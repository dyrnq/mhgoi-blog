<template>
  <div>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" width="50%" append-to-body @close="resetForm">
      <el-checkbox-group v-model="roleIds">
        <el-checkbox v-for="role in roles" :label="role.id" :key="role.id">{{role.roleName}}</el-checkbox>
      </el-checkbox-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm" size="small">取 消</el-button>
        <el-button type="primary" size="small" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAllRole, getOwnedRole, grantRole } from '@/api/role'

export default {
  data() {
    return {
      visible: false,
      title: "分配角色",
      userId: "",
      roles: [],
      roleIds: []
    }
  },
  methods: {
    init(userId) {
      this.userId = userId
      Promise.all([this.getAllRoles(), this.getOwnedRoles(userId)]).then(res => {
        this.roles = res[0]
        let list = []
        res[1].forEach(e => list.push(e.id));
        this.roleIds = list
        this.visible = true
      })
    },
    getAllRoles() {
      return new Promise(resolve => {
        getAllRole().then(res => {
          if (res.flag) resolve(res.data)
        })
      })
    },
    getOwnedRoles(userId) {
      return new Promise(resolve => {
        getOwnedRole({ userId: userId }).then(res => {
          if (res.flag) resolve(res.data)
        })
      })
    },
    save() {
      grantRole(this.userId, this.roleIds).then(res => {
        if (res.flag) {
            this.$message.success('分配成功')
            this.resetForm()
          }
      })
    },
    resetForm() {
      this.visible = false
      this.roleIds = []
    }
  }
}
</script>

<style scoped>
</style>