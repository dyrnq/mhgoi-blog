<template>
  <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" append-to-body @close="resetForm">
    <el-form :model="form" :rules="rules" ref="form" label-width="100px">
      <el-form-item label="角色名" prop="roleName">
        <el-input v-model="form.roleName"></el-input>
      </el-form-item>
      <el-form-item label="角色Code" prop="roleCode">
        <el-input v-model="form.roleCode"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input type="textarea" :rows="4" v-model="form.description"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="resetForm" size="small">取 消</el-button>
      <el-button type="primary" size="small" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getRoleById, updateRole, insertRole } from '@/api/role'

export default {
  data() {
    return {
      visible: false,
      title: "",
      form: {
        id: "",
        roleCode: "",
        roleName: "",
        description: ""
      },
      rules: {
        roleName: [{ required: true, message: '请输入角色名', trigger: 'blur' }],
        roleCode: [{ required: true, message: '请输入角色Code', trigger: 'blur' }]
      }
    }
  },
  methods: {
    init(roleId) {
      this.form.id = roleId
      if (roleId) {
        getRoleById(roleId).then(res => {
          Object.keys(this.form).forEach(key => this.form[key] = res.data[key])
          this.title = "修改角色"
          this.visible = true
        })
      } else {
        this.title = "添加角色"
        this.visible = true
      }
    },
    showMessage(res) {
      if (res.flag) {
        this.$message.success(res.msg);
        this.visible = false
        this.$emit("loadData")
        this.resetForm()
      } else if(res.code == 20008){
        this.$message.warning('角色Code重复')
      }
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) updateRole(this.form).then(res => this.showMessage(res))
          else insertRole(this.form).then(res => this.showMessage(res))
        } else {
          return false
        }
      });
    },
    resetForm() {
      this.visible = false
      this.$refs.form.resetFields()
      Object.keys(this.form).forEach(key => this.form[key] = null)
    }
  }
}
</script>

<style scoped>
</style>