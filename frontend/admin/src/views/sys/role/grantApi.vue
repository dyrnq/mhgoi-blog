<template>
  <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" width="30%" append-to-body @close="resetForm">
    <el-tree ref="tree" :data="apiTree" node-key="id" show-checkbox :default-checked-keys="multipleSelection" default-expand-all :props="defaultProps"></el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="resetForm" size="small">取 消</el-button>
      <el-button type="primary" size="small" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import qs from "querystring"
import { captureRejectionSymbol } from 'events'
import { getOwnedApi, getModuledApi, grantApi } from '@/api/backendApi'

export default {
  data() {
    return {
      visible: false,
      title: "分配API",
      roleId: "",
      apiTree: [],
      defaultProps: {
        children: 'children',
        label: 'apiName'
      },
      multipleSelection: []
    }
  },
  methods: {
    init(roleId) {
      this.roleId = roleId
      Promise.all([this.getApi(), this.getOwnedApi()]).then(res => {
        this.apiTree = res[0]
        res[1].forEach(item => this.multipleSelection.push(item.id))
        this.visible = true
      })
    },
    getApi() {
      return new Promise(resolve => {
        getModuledApi().then(res => {
          if (res.flag) resolve(res.data)
        })
      })
    },
    getOwnedApi() {
      return new Promise(resolve => {
        getOwnedApi({ roleId: this.roleId }).then(res => {
          if (res.flag) resolve(res.data)
        })
      })
    },
    save() {
      let list = this.$refs.tree.getCheckedKeys().filter(item => item)
      grantApi(this.roleId, list).then(res => {
        if (res.flag) {
          this.$message.success('分配成功')
          this.resetForm()
        }
      })
    },
    resetForm() {
      this.visible = false
      this.roleId = null
      this.multipleSelection = []
    }
  }
}
</script>

<style scoped>
.clearfix {
  text-align: center;
}
.box-card {
  margin-bottom: 20px;
}
.el-card__body {
  padding: 0;
}
</style>