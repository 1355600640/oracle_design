<template>
  <div id="book">
    <div class="book-select">
      <div class="select-mode">
        <div>搜索条件:</div>
        <a-select
          :model-value="pageSession.sortType"
          :style="{ width: '120px', background: 'white' }"
          placeholder="Please select ..."
          @change="(r) => (pageSession.sortType = r as number)"
        >
          <a-option :value="0">全部</a-option>
          <a-option :value="1">分类</a-option>
          <a-option :value="2">书本名</a-option>
        </a-select>
      </div>

      <a-input
        :style="{ width: '320px', background: 'white' }"
        v-model:model-value="pageSession.name"
        placeholder="请输入你想搜索的内容"
        allow-clear
        @change="(r) => (pageSession.name = r)"
      />
      <a-button @click="getBooks" style="background-color: white" type="outline"
        >搜索</a-button
      >
      <a-button
        @click="updateBookVisible = true"
        style="background-color: white"
        type="outline"
      >
        +添加书籍
      </a-button>
      <a-button
        v-if="Object.keys(rowSelection).length > 0"
        @click="removeBooks(selectedIds)"
        style="background-color: white"
        type="outline"
        >删除选中</a-button
      >
      <!-- <a-button
        @click="openRemove"
        style="background-color: white"
        type="outline"
        >{{
          !(Object.keys(rowSelection).length > 0) ? '开启删除' : '关闭删除'
        }}</a-button
      > -->
    </div>
    <div class="book-table">
      <a-table
        :loading="loading"
        :scroll="{
          x: '100%',
          y: '100%',
        }"
        :table-layout-fixed="true"
        :columns="(columns as any)"
        :data="books"
        row-key="id"
        :row-selection="(rowSelection as any)"
        :selected-keys="selectedIds"
        @sorter-change="sortChaneg"
        :pagination="{
          total: pageSession.total,
          pageSize: pageSession.num,
          current: pageSession.page,
          showTotal: true,
          showPageSize: true,
          showJumper: true,
        }"
        @select="selectItem"
        @page-size-change="pageSizeChange"
        @page-change="pageChange"
      >
        <template #columns>
          <a-table-column
            v-for="item in columns"
            :key="item.dataIndex"
            :title="item.title"
            :width="item.width"
            :sortable="(item.sortable as any)"
            :data-index="item.dataIndex !== 'bookDesc' ? item.dataIndex : ''"
          >
            <template #cell="{ record }">
              <div
                :title="record[item.dataIndex]"
                :class="
                  item.dataIndex !== 'bookDesc' ? 'book-item' : 'book-desc'
                "
              >
                {{ record[item.dataIndex] }}
              </div>
            </template>
          </a-table-column>
          <a-table-column :width="120" title="操作">
            <template #cell="{ record }">
              <a-button
                @click="
                  () => {
                    console.log(record)

                    updateBook = { ...record }
                    updateBookVisible = true
                  }
                "
                style="background-color: white"
                type="outline"
              >
                修改
              </a-button>
            </template>
          </a-table-column>
        </template>
      </a-table>
    </div>
    <a-modal
      v-model:visible="updateBookVisible"
      :onBeforeOk="insterOrUpdateBooks"
      @cancel="handleCancel"
      unmountOnClose
    >
      <template #title>{{ updateBook.id ? '修改' : '新增' }}书籍</template>
      <div class="updateBook">
        <a-form :model="updateBook">
          <a-form-item
            field="bookname"
            tooltip="请输入图书名称"
            label="图书名称"
          >
            <a-input
              v-model="updateBook.bookname"
              placeholder="请输入图书名称..."
            />
          </a-form-item>
          <a-form-item field="author" tooltip="请输入作者名" label="作者名">
            <a-input
              v-model="updateBook.author"
              placeholder="请输入作者名..."
            />
          </a-form-item>
          <a-form-item field="categoryName" tooltip="请选择分类" label="分类">
            <a-select
              :model-value="updateBook.categoryId"
              placeholder="请选中分类 ..."
              @change="changeCategory"
            >
              <a-option :value="item.id" v-for="item in bookCategorys">{{
                item.name
              }}</a-option>
            </a-select>
          </a-form-item>
          <a-form-item field="bookDesc" tooltip="请输入简介" label="简介">
            <a-textarea
              v-model="updateBook.bookDesc"
              placeholder="请输入简介..."
              :show-word-limit="true"
              :auto-size="false"
              :max-length="300"
            />
          </a-form-item>
        </a-form>
      </div>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { Ref, ref, onMounted, reactive } from 'vue'
import * as bookApi from '../api/BookInfo'
import { Message } from '@arco-design/web-vue'
let books: Ref<bookApi.bookInfo[]> = ref([] as bookApi.bookInfo[])
const rowSelection = ref({
  type: 'checkbox',
  showCheckedAll: true,
  onlyCurrent: false,
})
let selectedIds: Ref<number[]> = ref([] as number[])
let loading = ref(false)
let pageSession = ref({
  page: 1,
  num: 10,
  name: '',
  total: 0,
  sort: 0,
  sortType: 0,
})
let columns = ref([
  {
    title: '图书编号',
    dataIndex: 'id',
    width: 60,
  },
  {
    title: '图书名称',
    dataIndex: 'bookname',
    width: 100,
  },
  {
    title: '作者',
    dataIndex: 'author',
    width: 80,
  },
  {
    title: '分类',
    dataIndex: 'categoryName',
    width: 60,
  },
  {
    title: '简介',
    dataIndex: 'bookDesc',
    width: 320,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    width: 100,
    sortable: {
      sortDirections: ['ascend', 'descend'],
    },
  },
])
import { stringToDate } from '../utils/commonUtils'
// 获取书本列表
const getBooks = (): void => {
  loading.value = true
  bookApi.bookList(pageSession.value).then((r) => {
    loading.value = false
    if (r.data.status == 200) {
      books.value = r.data.data.books.map((r) => {
        r.createTime = stringToDate(r.createTime as string, 'all')
        return r
      })
      pageSession.value.total = r.data.data.total
    }
  })
}

// 新增书本
const insterOrUpdateBooks = async () => {
  let bookInfo = updateBook.value
  let api, errorMessage
  if (!bookInfo.bookname) {
    errorMessage = '请输入图书名称'
  } else if (!bookInfo.author) {
    errorMessage = '请输入作者名'
  } else if (!bookInfo.categoryId) {
    errorMessage = '请选择分类'
  } else if (!bookInfo.bookDesc) {
    errorMessage = '请输入简介'
  }
  if (errorMessage) {
    Message.warning(errorMessage)
    return Promise.reject()
  } else {
    if (bookInfo.id) {
      bookInfo.createTime = new Date(bookInfo.createTime).toJSON()
      api = bookApi.update(bookInfo)
    } else api = bookApi.inster(bookInfo)
    loading.value = true
    api.then((r) => {
      if (r.data.status == 200) {
        updateBookVisible.value = false
        updateBook.value = {} as any
        getBooks()
        Message.success(r.data.data)
      }
    })
  }
}

// 删除书本信息
const removeBooks = (ids: number[]): void => {
  if (selectedIds.value.length == 0) {
    Message.warning('请选中你要删除的书籍')
    return
  }
  loading.value = true
  bookApi.remove({ ids }).then(
    (r) => {
      if (r.data.status == 200) {
        Message.success(r.data.data)
        selectedIds.value = []
        getBooks()
      }
    },
    (error) => {
      loading.value = false
      Message.error(error.data.message)
    }
  )
}

// 切换时间排序
const sortChaneg = () => {
  pageSession.value.sort = pageSession.value.sort == 0 ? 1 : 0
  getBooks()
}
// 选中书籍
const selectItem = (rowKeys, rowKey, record) => {
  selectedIds.value = rowKeys
}

// 页码切换
const pageChange = (page: number) => {
  pageSession.value.page = page
  getBooks()
}

// 每页数量切换
const pageSizeChange = (pageSize: number) => {
  pageSession.value.num = pageSize
  getBooks()
}

let updateBookVisible = ref(false)
let updateBook: Ref<bookApi.bookInfo> = ref({} as bookApi.bookInfo)
const handleCancel = () => {
  updateBookVisible.value = false
  updateBook.value = {} as bookApi.bookInfo
}
import { categoryList, bookCategory } from '../api/BookCategory'
let bookCategorys: Ref<bookCategory[]> = ref([] as bookCategory[])
let getBookCategory = () => {
  categoryList().then((r) => {
    if (r.data.status == 200) {
      bookCategorys.value = r.data.data.list
    }
  })
}
const changeCategory = (value) => {
  console.log(value)
  updateBook.value.categoryId = value
  bookCategorys.value.forEach((r) => {
    if (r.id == value) updateBook.value.categoryName = r.name
  })
}
onMounted(() => {
  getBooks()
  getBookCategory()
})
</script>
<style lang="scss" scoped>
#book {
  box-sizing: border-box;
  padding: 20px 30px;
  height: 100%;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  .book-select {
    display: flex;
    gap: 20px;
    .select-mode {
      display: flex;
      align-items: center;
      gap: 10px;
    }
  }
  .book-table {
    flex: 1;
    box-sizing: border-box;
    padding-top: 20px;
    overflow: hidden;
    .book-desc {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      white-space: normal;
    }
    .book-item {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      white-space: normal;
    }
    :deep .arco-table-pagination {
      background: white;
      padding: 20px 10px;
      margin: 0;
      .arco-select-view-single {
        background-color: white;
      }
      .arco-input-wrapper {
        border: 1px solid #b8b8b8;
      }
    }
  }

  .updateBook {
    overflow: hidden;
  }
}
</style>
