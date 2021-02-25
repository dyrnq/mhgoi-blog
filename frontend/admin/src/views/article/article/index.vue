<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" :model="filters" @submit.native.prevent>
        <el-form-item label="用户名">
          <el-input v-model="filters.username" clearable></el-input>
        </el-form-item>
        <el-form-item label="标题">
          <el-input v-model="filters.title" clearable></el-input>
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="filters.categoryId" clearable placeholder="请选择">
            <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" v-on:click="getArticle">查询</el-button>
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
import { queryPage, deleteArticle, updateArticle } from '@/api/article'
import { getCategory } from '@/api/category'
import MyTable from '@/components/Table'
import Pagination from '@/components/Pagination'
//import Comment from '@/views/article/comment'

export default {
  components: {
    MyTable,
    Pagination,
    //Comment
  },
  data() {
    return {
      column: [
        
        {
          label: "用户名",
          prop: "username",
          width: "150",
        },
        // {
        //   label: "头像",
        //   prop: "avatar",
        //   width: "70",
        //   render: (h, params) => {
        //     return h('el-avatar', {
        //       props: {
        //         src: params.row.avatar
        //       }
        //     })
        //   }
        // },
        {
          label: "标题",
          prop: "title"
        },
        {
          label: "分类",
          prop: "categoryName",
          width: "100",
        },
        {
          label: "浏览量",
          prop: "viewsCount",
          width: "80",
          render: (h, params) => {
            return h('span', {
              domProps: { innerHTML: params.row.viewsCount },
              attrs: { style: 'color:#409EFF' }
            })
          }
        },
        // {
        //   label: "评论数",
        //   prop: "commentsCount",
        //   width: "80",
        //   render: (h, params) => {
        //     return h('span', {
        //       domProps: { innerHTML: params.row.commentsCount },
        //       attrs: { style: 'color:#E6A23C' }
        //     })
        //   }
        // },
        {
          label: "发表日期",
          prop: "createTime",
          width: "150",
          formatter: this.formatTime
        },
        {
          label: "置顶",
          prop: "top",
          width: "60",
          render: (h, params) => {
            let isTop = params.row.top === 1
            return h('el-tag', {
              domProps: { innerHTML: isTop ? '是' : '否' },
              props: { type: isTop ? 'success' : 'danger' }
            })
          }
        },
      ],
      button: [
        {
          label: '编辑',
          type: 'primary',
          click: this.handleEdit
        },
        {
          type: 'primary',
          click: this.palceTop,
          labelChange: (row) => {
            if (row.top === 1 ) return '取消置顶'
            else return '置顶'
          }
        },
        // {
        //   label: '查看评论',
        //   type: 'info',
        //   click: this.viewComment,
        // },
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
    this.getArticle()
    this.getCategory()
  },
  methods: {
    // viewComment(row) {
    //   this.$refs.comment.init(row)
    // },
    getCategory() {
      getCategory().then(res => {
        if(res.flag) this.categories = res.data
      })
    },
    palceTop(row) {
      let isTop = row.top === 1
      let label = isTop ? '确定取消置顶?' : '确定置顶?'
      let successLabel = isTop ? '已取消置顶' : '置顶成功'
      this.$confirm(label, '提示', {
        type: 'warning'
      }).then(() => {
        updateArticle({ id: row.id, top: isTop ? 0 : 1 }).then(res => {
          if (res.flag) {
            this.$notify.success(successLabel)
            this.getArticle()
          }
        })
      }).catch(() => { })
    },
    formatTime: function (value) {
      return util.formatDate.format(new Date(value), 'yyyy-MM-dd hh:mm')
    },
    getArticle() {
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
      this.$router.push({ path: '/edit/article', query: { id: row.id } })
    },
    // 显示新增界面
    handleAdd() {
      this.$router.push({ path: '/edit/article' })
    },
    doDel(data) {
      this.$confirm('确认删除?', '提示', {
        type: 'warning'
      }).then(() => {
        deleteArticle(data).then(res => {
          if (res.flag) {
            this.$message.success('删除成功')
            this.getArticle()
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
      this.getArticle()
    },
  }
}
</script>