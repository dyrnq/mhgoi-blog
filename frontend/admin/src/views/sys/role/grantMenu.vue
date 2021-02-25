<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" width="30%" append-to-body @close="resetForm">
      <el-tree ref="tree" :data="menus" check-strictly show-checkbox node-key="id" default-expand-all :default-checked-keys="ownedMenus" :props="defaultProps">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getMenus, getOwnedMenu, grantMenu } from '@/api/menu'

export default {
  data() {
    return {
      visible: false,
      title: "分配菜单",
      roleId: "",
      menus: [],
      ownedMenus: [],
      defaultProps: {
        children: 'children',
        label: 'menuName'
      }
    }
  },
  methods: {
    init(roleId) {
      this.roleId = roleId
      Promise.all([this.getMenus(), this.getOwnedMenus()]).then(res => {
        this.menus = res[0]
        this.ownedMenus = []
        res[1].forEach(e => {
          this.ownedMenus.push(e.id)
        })
        this.visible = true
      })
    },
    getMenus() {
      return new Promise(resolve => {
        getMenus().then(res => {
          if (res.flag) resolve(res.data)
        })
      })
    },
    getOwnedMenus() {
      return new Promise(resolve => {
        getOwnedMenu({ roleId: this.roleId }).then(res => {
          if (res.flag) resolve(res.data)
        })
      })
    },
    save() {
      grantMenu(this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
        if (res.flag) {
          this.$message.success('分配成功')
          this.resetForm()
        }
      })
    },
    resetForm() {
      this.visible = false
      this.roleId = null
      this.ownedMenus = []
    }
  }

}
</script>

<style scoped>
</style>