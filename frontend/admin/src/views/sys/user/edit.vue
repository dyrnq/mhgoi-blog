<template>
  <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" append-to-body @close="resetForm">
    <el-form :model="form" :rules="rules" ref="form" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="全名" prop="fullname">
        <el-input v-model="form.fullname"></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <el-input v-model="form.avatar"></el-input>
      </el-form-item>
      <el-form-item v-if="form.id==null" label="密码" prop="password">
        <el-input v-model="form.password" show-password></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="form.mobile"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="form.sex" placeholder="请选择">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用户状态" prop="state">
        <el-switch v-model="form.state" active-value="1" inactive-value="0" active-color="#13ce66" inactive-color="#ff4949">
        </el-switch>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="resetForm">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getUserById, updateUser, insertUser } from '@/api/user'

export default {
  data() {
    return {
      visible: false,
      title: "",
      form: {
        id: null,
        username: "",
        fullname: "",
        avatar: "",
        mobile: "",
        email: "",
        sex: "",
        state: "",
        password: null
      },
      options: [
        { value: '1', label: '男' },
        { value: '0', label: '女' }
      ],
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { max: 10, message: '长度不能超过 10 个字符哦', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    init(userId) {
      this.form.id = userId
      if (userId) {
        this.title = "修改用户"
        getUserById(userId).then(res => {
          Object.keys(this.form).forEach(key => this.form[key] = res.data[key])
          this.form.password = null
          this.visible = true
        })
      } else {
        this.visible = true
        this.title = "添加用户"
      }
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) updateUser(this.form).then(res => this.showMessage(res))
          else insertUser(this.form).then(res => this.showMessage(res))
        } else {
          return false
        }
      });
    },
    showMessage(res) {
      if (res.flag) {
        this.$message.success(res.msg)
        this.visible = false
        this.$emit("loadData")
        this.resetForm()
      } else if (res.code == 20008) {
        this.$message.warning('用户名重复')
      }
    },
    resetForm() {
      this.visible = false
      this.$refs.form.resetFields()
      Object.keys(this.form).forEach(key => {
        this.form[key] = null
      })
    }
  }
}
</script>