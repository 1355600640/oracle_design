<template>
  <div id="libraryCard">
    <div class="libraryCard-select">
      <div class="select-name">
        <div>搜索:</div>
        <a-input
          @press-enter="getStudents"
          :style="{ width: '320px', background: 'white' }"
          placeholder="请输入要输入的学号"
          v-model:model-value="pageDate.studentId"
          allow-clear
        />
      </div>
      <a-button @click="getStudents()">搜索</a-button>
      <a-button @click="removeStudents(selectds, true)">删除选中</a-button>
      <a-button
        @click="()=>{
        updateItem = {} as any
        visible = true
      }"
        >新增</a-button
      >
    </div>
    <div class="libraryCard-table">
      <a-table
        row-key="bookCard"
        :pagination="{
          total: pageDate.total,
          pageSize: pageDate.pageSize,
          current: pageDate.current,
          showTotal: true,
          showPageSize: true,
          showJumper: true,
        }"
        :columns="columns"
        v-model:selected-keys="selectds"
        :data="students"
        :row-selection="{
          type: 'checkbox',
          showCheckedAll: true,
          onlyCurrent: true,
        }"
        :scroll="{
          x: '100%',
          y: '100%',
        }"
        :pageSession="pageDate"
        @page-size-change="pageSizeChange"
        @page-change="pageChange"
      >
        <template #columns>
          <a-table-column
            v-for="item in columns"
            :key="item.dataIndex"
            :title="item.title"
            :data-index="item.dataIndex"
          >
            <template #cell="{ record }">
              <div :title="record[item.dataIndex]" class="table-item">
                {{ record[item.dataIndex] }}
              </div>
            </template>
          </a-table-column>
          <a-table-column :width="200" title="操作">
            <template #cell="{ record }">
              <a-button
                @click="removeStudents([record.bookCard], false)"
                style="background-color: red; color: white; margin-right: 10px"
                >删除</a-button
              >
              <a-button @click="openUpdate(record)" type="primary"
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
        {{ updateItem.studentId ? '修改' : '新增' }}借阅证
      </template>
      <div>
        <a-form :model="updateItem">
          <a-form-item
            v-if="updateItem.bookCard"
            field="username"
            label="借阅证"
          >
            <a-input v-model="(updateItem.bookCard as any)" disabled />
          </a-form-item>
          <a-form-item
            field="username"
            tooltip="Please enter username"
            label="姓名"
          >
            <a-input v-model="updateItem.username" placeholder="请输入姓名" />
          </a-form-item>
          <a-form-item
            field="studentId"
            tooltip="Please enter studentId"
            label="学号"
          >
            <a-input
              v-model="(updateItem.studentId as any)"
              placeholder="请输入学号"
            />
          </a-form-item>
          <a-form-item
            field="studentId"
            tooltip="Please enter studentId"
            label="班级"
          >
            <a-input
              v-model="(updateItem.classname as any)"
              placeholder="请输入班级"
            />
          </a-form-item>
          <a-form-item label="性别">
            <a-select
              v-model:model-value="updateItem.sex"
              placeholder="请选择性别"
            >
              <a-option value="男">男</a-option>
              <a-option value="女">女</a-option>
            </a-select>
          </a-form-item>
        </a-form>
      </div>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { ref, Ref, onMounted } from 'vue'
import {
  removeStudent,
  insterOrUpdateStudent,
  selectToLikeName,
  studentInfo,
} from '../api/Borrow'
import { Message } from '@arco-design/web-vue'
let pageDate = ref({
  studentId: '',
  current: 1,
  pageSize: 10,
  total: 0,
})
let loading = ref(false)
let students = ref([] as studentInfo[])
// 页码切换
const pageChange = (page: number) => {
  pageDate.value.current = page
  getStudents()
}

// 每页数量切换
const pageSizeChange = (pageSize: number) => {
  pageDate.value.pageSize = pageSize
  getStudents()
}
// 获取学生列表
const getStudents = () => {
  loading.value = true
  selectToLikeName(pageDate.value).then(
    (r) => {
      if (r.data.status == 200) {
        loading.value = false
        students.value = r.data.data.list
      }
    },
    (error) => {
      loading.value = false
    }
  )
}
let selectds = ref([])
const removeStudents = (ids: number[], removeType: boolean) => {
  loading.value = true
  ids = removeType ? selectds.value : ids
  removeStudent(ids).then(
    (r) => {
      if (r.data.status == 200) {
        Message.success(r.data.message)
        getStudents()
        if (removeType) {
          selectds.value = selectds.value.filter((r) => {
            return r != ids[0]
          })
        } else selectds.value = []
      }
    },
    () => {
      loading.value = false
    }
  )
}
let updateItem = ref({} as studentInfo)
let visible = ref(false)
const updateOrInster = async () => {
  loading.value = true
  updateItem.value.studentId = parseInt(updateItem.value.studentId + '')
  await insterOrUpdateStudent(updateItem.value).then(
    (r) => {
      if (r.data.status === 200) {
        // visible.value = false
        getStudents()
        Message.success(r.data.message)
      }
      loading.value = false
    },
    () => {
      loading.value = false
    }
  )
}
const columns = [
  {
    title: '学号',
    dataIndex: 'studentId',
  },
  {
    title: '姓名',
    dataIndex: 'username',
  },
  {
    title: '班级',
    dataIndex: 'classname',
  },
  {
    title: '借阅证',
    dataIndex: 'bookCard',
  },
  {
    title: '性别',
    dataIndex: 'sex',
  },
]
const openUpdate = (item: studentInfo) => {
  updateItem.value = item
  visible.value = true
}
const handleOk = async () => {
  let message = ''
  if (!updateItem.value.username) {
    message = '请输入姓名'
  } else if (!updateItem.value.studentId) {
    message = '请输入学号'
  } else if (!updateItem.value.classname) {
    message = '请输入班级'
  } else if (!updateItem.value.sex) {
    message = '请选择性别'
  }
  if (message) {
    Message.warning(message)
    return Promise.reject()
  }
  loading.value = true
  await updateOrInster()
  return Promise.resolve()
}
const handleCancel = () => {
  updateItem.value = {} as any
  visible.value = false
}
onMounted(() => {
  getStudents()
})
</script>
<style lang="scss" scoped>
#libraryCard {
  display: flex;
  flex-direction: column;
  padding: 30px 25px;
  .libraryCard-select {
    display: flex;
    gap: 20px;
    .select-name {
      display: flex;
      > div {
        background-color: white;
        line-height: 30px;
        width: 100px;
        text-align: center;
        font-size: 13px;
        border-right: 1px solid #e5e5e5;
      }
    }
    > button {
      background: white;
      padding: 0 25px;
    }
  }
  .libraryCard-table {
    flex: 1;
    overflow: hidden;
    margin-top: 30px;
  }
}
</style>
