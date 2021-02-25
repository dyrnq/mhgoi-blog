<template>
  <div class="app-container">
    <el-button style="margin-bottom:20px" type="primary" icon="el-icon-edit" @click="handleAdd">新增</el-button>
    <my-table :column="column" :button="button" :data="cates" @batchremove="batchRemove" />
    <edit @loadData="getCategory" ref="edit" />
  </div>
</template>

<script>
import { getCategory, deleteCategory } from '@/api/category'
import MyTable from '@/components/Table'
import Edit from './edit'

export default {
  components: {
    MyTable,
    Edit
  },
  data() {
    return {
      column: [
        {
          label: "分类名",
          prop: "name",
        },
        {
          label: "slug",
          prop: "slug",
        },        
        {
          label: "图标",
          prop: "icon",
          width: "300",
          render: (h, params) => {
            let icon = params.row.icon
            return h('svg', {
              class: ['icon-svg'],
              attrs: {
                style: 'width:30px;',
              }
            }, [
              h('use', {
                attrs: {
                  'xlink:href': '#' + icon
                }
              }),
            ])
          }
        },
        {
          label: "排序",
          prop: "sort",
          width: "150"
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
      cates: []
    }
  },
  created() {
    this.getCategory()
  },
  methods: {
    getCategory() {
      getCategory().then(res => {
        if (res.flag) this.cates = res.data
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
        deleteCategory(data).then(res => {
          if (res.flag) {
            this.$message.success('删除成功')
            this.getCategory()
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
  }
}
</script>

<style>
</style>