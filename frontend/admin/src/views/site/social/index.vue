<template>
  <div class="app-container">
    <el-button style="margin-bottom:20px" type="primary" icon="el-icon-edit" @click="handleAdd">新增</el-button>
    <my-table :column="column" :button="button" :data="socials" @batchremove="batchRemove" />
    <edit @loadData="getSocials" ref="edit" />
  </div>
</template>

<script>
import { getSocial, deleteSocial } from '@/api/social'
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
          label: "标题",
          prop: "title",
          width: "150"
        },
        {
          label: "图标",
          prop: "icon",
          width: "150",
          render: (h, params) => {
            return h('i', {
              class: [params.row.icon],
              attrs: {
                style: 'color:' + params.row.color
              }
            })
          }
        },
        {
          label: "颜色",
          prop: "color",
          width: "150",
          render: (h, params) => {
            let color = params.row.color
            return h('div', {
              domProps: { innerHTML: color },
              attrs: {
                style: 'padding:5px;color:#fff;margin:0 auto;background-color:' + color
              }
            })
          }
        },
        {
          label: "链接",
          prop: "href",
          href: "150",
          align: 'left',
          render: (h, params) => {
            let href = params.row.href
            return h('el-link', {
              domProps: { innerHTML: href },
              attrs: {
                href: href,
                target: '_blank'
              }
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
          label: '删除',
          type: 'danger',
          click: this.handleDel
        }
      ],
      socials: []
    }
  },
  created() {
    this.getSocials()
  },
  methods: {
    getSocials() {
      getSocial().then(res => {
        if (res.flag) this.socials = res.data
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
        deleteSocial(data).then(res => {
          if (res.flag) {
            this.$message.success('删除成功')
            this.getSocials()
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