<template>
  <el-dialog title="开放API" :visible.sync="visible" append-to-body>
    <my-table :column="column" :data="apis" :selectable="false" />
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="pageSize" @pagination="handlePageChange" />
  </el-dialog>
</template>

<script>
import MyTable from '@/components/Table'
import Pagination from '@/components/Pagination'
import { queryPage } from '@/api/backendApi'

export default {
  components: {
    MyTable,
    Pagination
  },
  data() {
    return {
      column: [
        {
          label: "模块",
          prop: "moduleName",
          width: "150"
        },
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
        }
      ],
      visible: false,
      total: 0,
      page: 1,
      pageSize: 20,
      apis: []
    }
  },
  methods: {
    init() {
      this.getOpenApi().then(res => this.visible = true)
    },
    handlePageChange({ page, limit }) {
      this.page = page
      this.pageSize = limit
      this.getOpenApi()
    },
    getOpenApi() {
      return new Promise(resolve => {
        queryPage(this.page, this.pageSize, { open: 1 }).then(res => {
          if (res.flag) {
            this.apis = res.data.rows
            this.total = res.data.counts
          }
          resolve()
        })
      })
    }
  }
}
</script>

<style>
</style>