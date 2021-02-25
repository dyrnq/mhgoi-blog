<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" :model="filters" @submit.native.prevent>
        <el-form-item>
          <el-button type="primary" icon="el-icon-edit" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--列表-->
    <my-table :column="column" :button="button" :data="roles" @batchremove="batchRemove" />
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="pageSize" @pagination="handlePageChange" />

    <edit ref="edit" v-on:loadData="loadData"></edit>
    <GrantApi ref="GrantApi"></GrantApi>
    <GrantMenu ref="GrantMenu"></GrantMenu>
  </div>
</template>

<script>
import { queryPage, deleteRole } from '@/api/role'
import MyTable from '@/components/Table'
import Pagination from '@/components/Pagination'
import Edit from "./edit"
import GrantMenu from "./grantMenu"
import GrantApi from "./grantApi"

export default {
  components: {
    MyTable,
    Pagination,
    Edit,
    GrantMenu,
    GrantApi
  },
  data() {
    return {
      column: [
        {
          label: "角色ID",
          prop: "id",
          width: "250"
        },
        {
          label: "角色名",
          prop: "roleName",
          width: "250"
        },
        {
          label: "角色Code",
          prop: "roleCode",
          width: "200",
          render: (h, params) => h('el-tag', params.row.roleCode)
        },
        {
          label: "描述",
          prop: "description",
          formatter: this.formatEmpty
        }
      ],
      button: [
        {
          label: "修改",
          type: "primary",
          click: this.handleEdit
        },
        {
          label: "分配菜单",
          type: "primary",
          click: this.grantMenu
        },
        {
          label: "分配API",
          type: "primary",
          click: this.GrantApi
        },
        {
          label: "删除",
          type: "danger",
          click: this.handleDel
        },
      ],
      filters: {},
      total: 0,
      page: 1,
      pageSize: 20,
      roles: []
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    loadData() {
      queryPage(this.page, this.pageSize, this.filters).then(res => {
        if (res.flag) {
          this.total = res.data.counts
          this.roles = res.data.rows
        }
      })
    },
    handlePageChange({ page, limit }) {
      this.page = page
      this.pageSize = limit
      this.loadData()
    },
    // 显示编辑界面
    handleEdit(row) {
      this.$refs.edit.init(row.id)
    },
    // 显示新增界面
    handleAdd() {
      this.$refs.edit.init()
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
        deleteRole(data).then(res => {
          if (res.flag) {
            this.$message.success('删除成功')
            this.loadData()
          }
        })
      }).catch(() => { })
    },
    grantMenu(row) {
      this.$refs.GrantMenu.init(row.id)
    },
    GrantApi(row) {
      this.$refs.GrantApi.init(row.id)
    },
    delete(row) {
      this.$confirm('确定删除此角色?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete("/role", {
          data: [row.id]
        })
          .then(res => {
            if (res.flag) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.loadData()
            }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
  },
  formatEmpty(row, column, cellValue) {
    return value ? value : '-'
  }
}
</script>

<style scoped>
</style>