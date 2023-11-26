<template>
  <div id="category">
    <div class="category-select">
      <div>操作:</div>
      <a-button
        style="background-color: white"
        type="outline"
        @click="removeCategorys"
        >删除选中</a-button
      >
      <a-button
        @click="()=>{
        insterCategory = {} as any
        visible = true
      }"
        style="background-color: white"
        type="outline"
        >新增</a-button
      >
    </div>
    <div class="category-table">
      <a-table
        :loading="loading"
        :row-selection="(rowSelection as any)"
        :columns="columns"
        :data="bookCategorys"
        row-key="id"
        @selected-keys="selectedIds"
        :pagination="false"
        @select="selectItem"
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
                    insterCategory = { ...record }
                    visible = true
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
      v-model:visible="visible"
      :onBeforeOk="handleOk"
      @cancel="handleCancel"
    >
      <template #title> 新增图书分类 </template>
      <div class="insert-or-update">
        <div>
          <span>分类</span>
          <a-input
            :style="{ width: '320px' }"
            placeholder="请输入分类"
            v-model:model-value="insterCategory.name"
            allow-clear
          />
        </div>
        <div>
          <span>排序</span>
          <a-input
            :style="{ width: '320px' }"
            placeholder="请输入排序"
            v-model:model-value="(insterCategory.sort as any)"
            allow-clear
          />
        </div>
      </div>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { ref, Ref, onMounted } from 'vue'
import { categoryList, bookCategory } from '../api/BookCategory'
import { stringToDate } from '../utils/commonUtils'
import { Message } from '@arco-design/web-vue'
let bookCategorys: Ref<bookCategory[]> = ref([] as bookCategory[])
let columns = ref([
  {
    title: '编号',
    dataIndex: 'id',
  },
  {
    title: '分类名',
    dataIndex: 'name',
  },
  {
    title: '排序',
    dataIndex: 'sort',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
])
const rowSelection = ref({
  type: 'checkbox',
  showCheckedAll: true,
  onlyCurrent: false,
})
let getBookCategory = () => {
  loading.value = true
  categoryList().then((r) => {
    loading.value = false
    if (r.data.status == 200) {
      bookCategorys.value = r.data.data.list.map((r) => {
        r.createTime = stringToDate(r.createTime as string, 'all')
        return r
      })
    }
  })
}
let selectedIds: Ref<number[]> = ref([] as number[])
const selectItem = (rowKeys, rowKey, record) => {
  selectedIds.value = rowKeys
}
let insterCategory: Ref<bookCategory> = ref({} as bookCategory)
let visible = ref(false)
let loading = ref(false)
import { inster, update, remove } from '../api/BookCategory'
const handleOk = async () => {
  if (!insterCategory.value.name) {
    Message.warning('请输入分类名')
    return Promise.reject('')
  } else if (!insterCategory.value.sort) {
    Message.warning('请输入排序值')
    return Promise.reject('')
  } else if (isNaN(parseInt(insterCategory.value.sort + ''))) {
    Message.warning('排序值必须为数字')
    return Promise.reject('')
  } else {
    let api
    if (insterCategory.value.id) {
      api = update(insterCategory.value)
      insterCategory.value.createTime = new Date(
        insterCategory.value.createTime
      ).toJSON()
    } else api = inster(insterCategory.value)
    loading.value = true
    api.then((r) => {
      if (r.data.status === 200) {
        getBookCategory()
        Message.success(r.data.message)
      }
    })
  }
}
const handleCancel = () => {
  insterCategory.value = {} as bookCategory
  visible.value = false
}

const removeCategorys = () => {
  if (selectedIds.value.length === 0) {
    Message.warning('请选中你要删除的选项')
    return
  }
  remove({ ids: selectedIds.value }).then((r) => {
    if (r.data.status === 200) {
      selectedIds.value = []
      getBookCategory()
      Message.success(r.data.message)
    }
  })
}
onMounted(() => {
  getBookCategory()
})
</script>
<style lang="scss" scoped>
#category {
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  padding: 10px;
  .category-select {
    display: flex;
    gap: 20px;
    align-items: center;
  }
  .category-table {
    margin-top: 20px;
    overflow: hidden;
    width: 90%;
    flex: 1;
  }
}
.insert-or-update {
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
  > div {
    display: flex;
    gap: 20px;
    align-items: center;
  }
}
</style>
