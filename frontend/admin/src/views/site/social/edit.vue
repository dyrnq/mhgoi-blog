<template>
  <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" append-to-body @close="resetForm">
    <el-form :model="form" :rules="rules" ref="form" label-width="80px" :hide-required-asterisk="true">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <el-col :span="14">
          <el-input v-model="form.icon"></el-input>
        </el-col>
        <el-col :span="8" :offset="1">
          <i :class="form.icon" :style="{fontSize:'30px', color: form.color}"></i>
        </el-col>
      </el-form-item>
      <el-form-item label="颜色" prop="color">
        <el-color-picker v-model="form.color"></el-color-picker>
      </el-form-item>
      <el-form-item label="链接" prop="href">
        <el-input v-model="form.href"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="resetForm">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getSocialById, updateSocial, insertSocial } from '@/api/social'

export default {
  data() {
    return {
      visible: false,
      title: "",
      form: {
        id: null,
        title: "",
        icon: "",
        color: "",
        href: ""
      },
      rules: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        icon: [{ required: true, message: '请输入图标', trigger: 'blur' }],
        color: [{ required: true, message: '请选择颜色', trigger: 'blur' }],
        href: [{ required: true, message: '请输入链接', trigger: 'blur' }],
      }
    }
  },
  methods: {
    init(socialId) {
      this.form.id = socialId
      if (socialId) {
        this.title = "修改社交信息"
        getSocialById(socialId).then(res => {
          if (res.flag) {
            Object.keys(this.form).forEach(key => this.form[key] = res.data[key])
            this.visible = true
          }
        })
      } else {
        this.visible = true
        this.title = "添加社交信息"
      }
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) updateSocial(this.form).then(res => this.showMessage(res))
          else insertSocial(this.form).then(res => this.showMessage(res))
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