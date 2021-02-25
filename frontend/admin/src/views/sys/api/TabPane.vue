<template>
  <div>
    <div class="filter-container">
      <el-form :inline="true" :model="filters" @submit.native.prevent>
        <el-form-item>
          <el-button type="primary" icon="el-icon-edit" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    <my-table :column="column" :button="button" :data="apis" @batchremove="batchRemove" />
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="pageSize" @pagination="handlePageChange" />
    <edit @loadData="loadData" ref="edit" />
  </div>
</template>

<script>
import { queryPage, deleteApi } from '@/api/backendApi'
import MyTable from '@/components/Table'
import Pagination from '@/components/Pagination'
import Edit from "./edit"

export default {
  components: {
    MyTable,
    Edit,
    Pagination
  },
  props: {
    apiModule: {
      type: Object,
      default: {}
    }
  },
  data() {
    return {
      column: [
        {
          label: "API名称",
          prop: "apiName",
          width: "150"
        },
        {
          label: "API请求地址",
          prop: "apiUrl",
          width: "200",
          align: "left"
        },
        {
          label: "API请求方式",
          prop: "apiMethod",
          width: "200",
          render: (h, params) => {
            let methods = params.row.apiMethod.split(',')
            return h('span', methods.map((item, index) => {
              return h('el-tag', {
                domProps: { innerHTML: item },
                props: { type: 'primary' },
                attrs: { style: 'margin:2px' },
              })
            }))
          }
        },
        {
          label: "是否开放",
          prop: "open",
          width: "100",
          render: (h, params) => {
            let isOpen = params.row.open===1;
            return h('el-tag', {
              domProps: { innerHTML: isOpen ? '是' : '否' },
              props: { type: isOpen ? 'success' : 'danger' }
            })
          }
        },
        {
          label: "描述",
          prop: "description",
          width: "200",
          formatter: this.formatEmpty
        }
      ],
      button: [
        {
          label: "编辑",
          type: "primary",
          click: this.handleEdit
        },
        {
          label: "删除",
          type: "danger",
          click: this.handleDel
        },
      ],
      filters: {
        moduleName: this.apiModule.key
      },
      total: 0,
      page: 1,
      pageSize: 20,
      apis: []
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    loadData() {
      queryPage(this.page, this.pageSize, this.filters).then(res => {
        this.total = res.data.counts
        this.apis = res.data.rows
      })
    },
    handlePageChange({ page, limit }) {
      this.page = page
      this.pageSize = limit
      this.loadData()
    },
    // 显示编辑界面
    handleEdit(row) {
      this.$refs.edit.init(this.apiModule, row.id)
    },
    // 显示新增界面
    handleAdd() {
      this.$refs.edit.init(this.apiModule)
    },
    // 删除
    handleDel(row) {
      this.doDel([row.id])
    },
    // 批量删除
    batchRemove(sels) {
      let list = []
      sels.forEach(item => list.push(item.id))
      this.doDel(list)
    },
    doDel(data) {
      this.$confirm('确认删除?', '提示', {
        type: 'warning'
      }).then(() => {
        deleteApi(data).then(res => {
          if (res.flag) {
            this.$message.success('删除成功')
            this.loadData()
          }
        })
      }).catch(() => { })
    },
    formatEmpty(value) {
      return value ? value : '-'
    }
  }
}
</script>