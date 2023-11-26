<template>
  <div id="borrow">
    <div class="top-select">
      <div class="select-type">
        <div>搜索类型:</div>
        <a-select
          v-model:model-value="selectType"
          :style="{ width: '160px', background: 'white' }"
          placeholder="Please select ..."
        >
          <a-option
            :value="item.key"
            v-for="item in selectTypes"
            :key="item.key"
            >{{ item.name }}</a-option
          >
        </a-select>
      </div>
      <a-input
        v-model:model-value="selectSession"
        :style="{ width: '320px', background: 'white' }"
        placeholder="请输入你要搜索的内容"
        @press-enter="selectTypeToBorrow"
        allow-clear
      />
      <a-button @click="selectTypeToBorrow">搜索</a-button>
      <a-button @click="removeItems(selectItems, true)">删除选中</a-button>
      <a-button @click="openInster">新增</a-button>
    </div>
    <div class="borrow-table">
      <a-table
        :pagination="{
          total: pageDate.total,
          pageSize: pageDate.pageSize,
          current: pageDate.current,
          showTotal: true,
          showPageSize: true,
          showJumper: true,
        }"
        :row-selection="{
          type: 'checkbox',
          showCheckedAll: true,
          onlyCurrent: true,
        }"
        row-key="id"
        v-model:selected-keys="selectItems"
        :pageSession="pageDate"
        :columns="(columns as any)"
        :data="borrows"
        @page-size-change="pageSizeChange"
        @page-change="pageChange"
        :loading="loading"
        :scroll="{
          x: '100%',
          y: '100%',
        }"
      >
        <template #columns>
          <a-table-column
            v-for="item in columns"
            :key="item.dataIndex"
            :title="item.title"
            :data-index="item.dataIndex"
            :width="(item.width as any)"
          >
            <template #cell="{ record }">
              <div :title="record[item.dataIndex]" class="table-item">
                {{ record[item.dataIndex] }}
              </div>
            </template>
          </a-table-column>
          <a-table-column
            v-for="item in columns_2"
            :key="item.dataIndex"
            :title="item.title"
          >
            <template #cell="{ record }">
              {{ record['student'][item.dataIndex] }}
            </template>
          </a-table-column>
          <a-table-column :width="200" title="操作">
            <template #cell="{ record }">
              <a-button
                @click="removeItems([record.id], false)"
                style="background-color: red; color: white; margin-right: 10px"
                >删除</a-button
              >
              <a-button type="primary" @click="openUpdate(record)"
                >修改</a-button
              >
            </template>
          </a-table-column>
        </template>
      </a-table>
    </div>
    <a-modal
      v-model:visible="visible"
      :onBeforeOk="handleOk"
      @cancel="handleCancel"
    >
      <template #title>
        {{ updateItem.bookId ? '修改' : '新增' }}借阅信息</template
      >
      <div>
        <a-form :model="updateItem" :style="{ width: '100%' }">
          <a-form-item field="bookId" label="图书名:">
            <a-select
              placeholder="请选择图书"
              v-model:input-value="bookSelectValue"
              v-model:model-value="updateItem.bookName"
              @search="selectNameOfBook"
              :loading="bookLoading"
              allow-search
              @change="bookSelectChange"
            >
              <a-option
                v-bind:value="item.id"
                v-for="item in selectBook"
                :key="item.id"
                >{{ item.bookname }}</a-option
              >
            </a-select>
          </a-form-item>
          <a-form-item field="bookId" label="图书编号:">
            <a-input-number
              v-model="(updateItem.bookId as any)"
              placeholder="图书编号"
              disabled
            />
          </a-form-item>
          <a-form-item field="bookId" label="分类:">
            <a-input
              v-model="(updateItem.categoryName as any)"
              placeholder="分类"
              disabled
            />
          </a-form-item>
          <a-form-item field="bookId" label="图书名:">
            <a-select
              placeholder="请选择学号"
              :loading="studentLoading"
              v-model:input-value="studentSelectValue"
              v-model:model-value="updateItem.student.studentId"
              @search="selectNameOfStudent"
              allow-search
              @change="studentSelectChange"
            >
              <a-option
                v-bind:value="item.studentId"
                v-for="item in students"
                :key="item.studentId"
                >{{ item.studentId }}</a-option
              >
            </a-select>
          </a-form-item>
          <a-form-item label="学生:">
            <a-input
              v-model="(updateItem.student.username as any)"
              placeholder="学生"
              disabled
            />
          </a-form-item>
        </a-form>
      </div>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { ref, Ref, onMounted } from 'vue'
import { getBorrows, borrowInfo, remove, insterOrUpdate } from '../api/Borrow'
import { Message } from '@arco-design/web-vue'
let pageDate = ref({
  current: 1,
  pageSize: 10,
  bookName: '',
  studentId: 0,
  categoryName: '',
  total: 0,
})
let selectTypes = ref([
  { name: '书名', key: 'bookName' },
  { name: '学号', key: 'studentId' },
  { name: '分类', key: 'categoryName' },
])
let selectType: Ref<String> = ref('bookName')
let borrows: Ref<borrowInfo[]> = ref([] as any)
let selectSession = ref('')
let selectItems = ref([])
const columns = ref([
  {
    title: '图书编号',
    dataIndex: 'bookId',
  },
  {
    title: '书名',
    dataIndex: 'bookName',
    width: 200,
  },
  {
    title: '分类名',
    dataIndex: 'categoryName',
  },
])
const columns_2 = ref([
  {
    title: '学号',
    dataIndex: 'studentId',
  },
  {
    title: '姓名',
    dataIndex: 'username',
  },
  {
    title: '图书证',
    dataIndex: 'bookCard',
  },
  {
    title: '班级',
    dataIndex: 'classname',
  },
  {
    title: '性别',
    dataIndex: 'sex',
  },
])
let loading = ref(false)
const selectBorrows = () => {
  loading.value = true
  getBorrows(pageDate.value as any).then((r) => {
    loading.value = false
    if (r.data.status == 200) {
      borrows.value = r.data.data.list
      pageDate.value.total = r.data.data.total
    }
  })
}
// 页码切换
const pageChange = (page: number) => {
  pageDate.value.current = page
  selectBorrows()
}

// 每页数量切换
const pageSizeChange = (pageSize: number) => {
  pageDate.value.pageSize = pageSize
  selectBorrows()
}

// 搜索选项
const selectTypeToBorrow = () => {
  if (
    selectType.value == 'studentId' &&
    (isNaN(parseInt(selectSession.value)) || selectSession.value == '')
  ) {
    Message.warning('学号请输入数字且不能为空')
  } else {
    if (selectType.value == 'studentId') {
      pageDate.value[selectType.value as any] = parseInt(
        selectSession.value + ''
      )
    } else pageDate.value[selectType.value as any] = selectSession.value
    selectBorrows()
  }
}

// 删除选项
const removeItems = (ids: number[], type: boolean) => {
  loading.value = true
  remove(ids).then(
    (r) => {
      if (r.data.status === 200) {
        loading.value = false
        if (type) selectItems.value = []
        else {
          selectItems.value = selectItems.value.filter((r) => {
            return r != ids[0]
          })
        }
        selectBorrows()
        Message.success(r.data.message)
      }
    },
    (error) => {
      loading.value = false
    }
  )
}
let visible = ref(false)
let updateItem = ref({ student: { studentId: 0 } } as borrowInfo)

import { studentInfo, selectToLikeName } from '../api/Borrow'
let students = ref([] as studentInfo[])
let studentSelectValue = ref('')
let timeOver = 500
let selectStudentTime
let studentLoading = ref(false)
// 根据输入模糊查询 学生
const selectNameOfStudent = () => {
  if (studentSelectValue.value == '') return
  clearTimeout(selectStudentTime)
  studentLoading.value = true
  selectStudentTime = setTimeout(() => {
    selectToLikeName({ studentId: studentSelectValue.value }).then((r) => {
      studentLoading.value = false
      students.value = {} as any
      if (r.data.status == 200) {
        students.value = r.data.data.list
      }
    })
  }, timeOver)
}
// 选择学生
const studentSelectChange = (val) => {
  students.value.forEach((item) => {
    if (item.studentId == val) {
      updateItem.value.student.username = item.username
      updateItem.value.student.bookCard = item.bookCard
    }
  })
}
import {
  selectToLikeName as selectBookToLikeName,
  bookInfo,
} from '../api/BookInfo'
let selectBookTime
let selectBook = ref([] as bookInfo[])
let bookSelectValue = ref('')
let bookLoading = ref(false)
// 更具输入 模糊查询图书
const selectNameOfBook = () => {
  if (bookSelectValue.value == '') return
  clearTimeout(selectBookTime)
  bookLoading.value = true
  selectBookTime = setTimeout(() => {
    selectBookToLikeName({ bookName: bookSelectValue.value }).then((r) => {
      bookLoading.value = false
      selectBook.value = {} as any
      if (r.data.status == 200) {
        selectBook.value = r.data.data
      }
    })
  }, timeOver)
}
// 选择书本
const bookSelectChange = (val) => {
  updateItem.value.bookId = parseInt(val)
  selectBook.value.forEach((item) => {
    if (item.id == val) {
      updateItem.value.bookName = item.bookname
      updateItem.value.categoryName = item.categoryName
    }
  })
}
const openInster = () => {
  visible.value = true
  updateItem.value = { student: {} } as any
  bookSelectValue.value = ''
  studentSelectValue.value = ''
}
const openUpdate = (item: borrowInfo) => {
  visible.value = true
  updateItem.value = JSON.parse(JSON.stringify(item))
  bookSelectValue.value = item.bookName
}
const handleOk = () => {
  loading.value = true
  let message = ''
  if (!updateItem.value.student.bookCard) {
    message = '请选择学生'
  } else if (!updateItem.value.bookId) {
    message = '请选择图书'
  }
  if (message) {
    Message.warning(message)
    loading.value = false
    return Promise.reject()
  }
  insterOrUpdate({
    id: updateItem.value.id,
    bookCard: updateItem.value.student.bookCard,
    bookInfoId: updateItem.value.bookId,
  }).then(
    (r) => {
      if (r.data.status === 200) {
        selectBorrows()
        Message.success(r.data.message)
      }
      loading.value = false
    },
    (error) => {
      loading.value = false
    }
  )
}
const handleCancel = () => {
  updateItem.value = {} as any
  visible.value = false
  students.value = [] as any
  selectBook.value = []
}
onMounted(() => {
  selectBorrows()
})
</script>
<style lang="scss" scoped>
#borrow {
  box-sizing: border-box;
  padding: 20px 25px;
  display: flex;
  flex-direction: column;
  .top-select {
    display: flex;
    align-items: center;
    gap: 20px;
    .select-type {
      display: flex;
      align-items: center;
      > div {
        width: 100px;
        text-align: center;
        padding: 6px 20px;
        font-size: 13px;
        background-color: white;
        border-right: 1px solid #e5e5e5;
      }
    }
    button {
      border: 1px solid #e5e5e5;
      background-color: white;
      padding: 0 20px;
      height: 35px;
    }
  }
  .borrow-table {
    margin-top: 20px;
    flex: 1;
    overflow: hidden;
    .table-item {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      white-space: normal;
    }
  }
}
</style>
