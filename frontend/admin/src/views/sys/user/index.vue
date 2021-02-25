<template>
  <div class="app-container">
    <!--工具条-->
    <div class="filter-container">
      <el-form :inline="true" :model="filters" @submit.native.prevent>
        <el-form-item label="用户名">
          <el-input v-model="filters.username" clearable></el-input>
        </el-form-item>       
        <el-form-item label="手机号">
          <el-input v-model="filters.mobile" clearable></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="filters.email" clearable></el-input>
        </el-form-item>
        <el-form-item label="用户状态">
          <el-select clearable v-model="filters.state">
            <el-option v-for="item in stateOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" v-on:click="getUsers">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-edit" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--列表-->
    <my-table :column="column" :button="button" :data="users" @batchremove="batchRemove" />
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="pageSize" @pagination="handlePageChange" />
    <!--编辑界面-->
    <edit @loadData="getUsers" ref="edit" />
    <grant-role ref="grantRole"></grant-role>
  </div>
</template>

<script>
import util from '@/utils/table.js'
import { queryPage, deleteUser } from '@/api/user'
import MyTable from '@/components/Table'
import Pagination from '@/components/Pagination'
import { parseTime } from '@/utils'
import Edit from './edit'
import GrantRole from './grantRole'

export default {
  components: {
    MyTable,
    Pagination,
    Edit,
    GrantRole
  },
  data() {
    return {
      column: [
        {
          label: "用户名",
          prop: "username",
          width: "150"
        },
        {
          label: "全名",
          prop: "fullname",
          width: "150"
        },
        {
          label: "头像",
          prop: "avatar",
          width: "70",
          render: (h, params) => {
            return h('el-avatar', {
              props: {
                src: params.row.avatar
              }
            })
          }
        },
        {
          label: "手机号",
          prop: "mobile",
          width: "180",
          formatter: this.formatEmpty
        },
        {
          label: "邮箱",
          prop: "email",
          width: "180",
          formatter: this.formatEmpty
        },
        {
          label: "性别",
          prop: "sex",
          width: "100",
          formatter: this.formatSex
        },
        {
          label: "创建时间",
          prop: "createTime",
          width: "180",
          formatter: this.formatTime
        },
        {
          label: "用户状态",
          prop: "state",
          width: '100px',
          render: (h, params) => {
            let state = params.row.state
            return h('el-tag', {
              domProps: { innerHTML: state == '1' ? '有效' : '无效' },
              props: { type: state == '1' ? 'success' : 'danger' }
            })
          }
        }
      ],
      button: [
        {
          label: '编辑',
          type: 'primary',
          click: this.handleEdit
        },
        {
          label: '分配角色',
          type: 'primary',
          click: this.grantRole
        },
        {
          label: '删除',
          type: 'danger',
          click: this.handleDel
        }
      ],
      filters: {
        username: '',
        mobile: '',
        email: ''
      },
      stateOptions: [{ value: '1', label: '有效' }, { value: '0', label: '无效' }],
      users: [],
      total: 0,
      page: 1,
      pageSize: 20
    }
  },
  methods: {
    formatSex: function (value) {
      return value === '1' ? '男' : value === '0' ? '女' : '未知'
    },
    formatEmpty: function (value) {
      return value ? value : '-'
    },
    formatTime: function (value) {
      return util.formatDate.format(new Date(value), 'yyyy-MM-dd hh:mm')
    },
    // 获取用户列表
    getUsers() {
      queryPage(this.page, this.pageSize, this.filters).then(res => {
        this.total = res.data.counts
        this.users = res.data.rows
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
        deleteUser(data).then(res => {
          if (res.flag) {
            this.$message.success('删除成功')
            this.getUsers()
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
      this.getUsers()
    },
    grantRole(row) {
      this.$refs.grantRole.init(row.id)
    },
  },
  mounted() {
    this.getUsers()
  }
}
</script>

<style scoped>
</style>