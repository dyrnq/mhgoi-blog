<template>
  <div class="app-container">
    <el-button style="margin-bottom:20px" type="primary" icon="el-icon-edit" @click="handleAdd">新增</el-button>
    <my-table :column="column" :button="button" :data="tableData" @batchremove="batchRemove" />
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="pageSize" @pagination="handlePageChange" />
    <edit @loadData="getTag" ref="edit" />
  </div>
</template>

<script>
import { queryPage, deleteTag } from '@/api/tag'
import MyTable from '@/components/Table'
import Pagination from '@/components/Pagination'
import Edit from './edit'

export default {
  components: {
    MyTable,
    Edit,
    Pagination
  },
  data() {
    return {
      column: [
        {
          label: "标签名",
          prop: "name",
          render: (h, params) => h('el-tag', params.row.name)
        },
        {
          label: "排序",
          prop: "sort",
          width: "150",
        }
      ],
      button: [
        {
          label: '编辑',
          type: 'primary',
          click: this.handleEdit
        },
        {
          label: '删除',
          type: 'danger',
          click: this.handleDel
        }
      ],
      tableData: [],
      total: 0,
      page: 1,
      pageSize: 20
    }
  },
  created() {
    this.getTag()
  },
  methods: {
    getTag() {
      queryPage(this.page, this.pageSize).then(res => {
        if (res.flag) {
          this.tableData = res.data.rows
          this.total = res.data.counts
        }
      })
    },
    // 删除
    handleDel(row) {
      this.doDel([row.id])
    },
    // 显示编辑界面
    handleEdit(row) {
      this.$refs.edit.init(row.id)
    },
    // 显示新增界面
    handleAdd() {
      this.$refs.edit.init()
    },
    doDel(data) {
      this.$confirm('确认删除?', '提示', {
        type: 'warning'
      }).then(() => {
        deleteTag(data).then(res => {
          if (res.flag) {
            this.$message.success('删除成功')
            this.getTag()
          }
        })
      }).catch(() => { })
    },
    // 批量删除
    batchRemove(sels) {
      let list = []
      sels.forEach(item => list.push(item.id))
      this.doDel(list)
    },
    handlePageChange({ page, limit }) {
      this.page = page
      this.pageSize = limit
      this.getTag()
    },
  }
}
</script>

<style>
</style>