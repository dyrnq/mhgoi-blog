<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" :model="filters" @submit.native.prevent>

        <el-form-item label="标题">
          <el-input v-model="filters.title" clearable></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" icon="el-icon-search" v-on:click="getPage">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style="margin-bottom:20px" type="primary" icon="el-icon-edit" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </div>

    <my-table :column="column" :button="button" :data="tableData" @batchremove="batchRemove" />
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="pageSize" @pagination="handlePageChange" />
    <!--<comment ref="comment"></comment>-->
  </div>
</template>

<script>
import util from '@/utils/table.js'
import { queryPage, deletePage, updatePage } from '@/api/page'
import MyTable from '@/components/Table'
import Pagination from '@/components/Pagination'

export default {
  components: {
    MyTable,
    Pagination,
  },
  data() {
    return {
      column: [

        {
          label: "标题",
          prop: "title"
        },
        {
          label: "发表日期",
          prop: "createTime",
          width: "150",
          formatter: this.formatTime
        },
      
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
      pageSize: 20,
      categories: [],
      filters: {
        username: '',
        title: '',
        categoryId: ''
      }
    }
  },
  created() {
    this.getPage()
  },
  methods: {
    
    formatTime: function (value) {
      if (value){
        return util.formatDate.format(new Date(value), 'yyyy-MM-dd hh:mm')
      }else{
        return '';
      }
    },
    getPage() {
      queryPage(this.page, this.pageSize, this.filters).then(res => {
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
      this.$router.push({ path: '/edit/page', query: { id: row.id } })
    },
    // 显示新增界面
    handleAdd() {
      this.$router.push({ path: '/edit/page' })
    },
    doDel(data) {
      this.$confirm('确认删除?', '提示', {
        type: 'warning'
      }).then(() => {
        deletePage(data).then(res => {
          if (res.flag) {
            this.$message.success('删除成功')
            this.getPage()
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
      this.getPage()
    },
  }
}
</script>