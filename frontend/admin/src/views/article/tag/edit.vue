<template>
  <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" append-to-body @close="resetForm">
    <el-form :model="form" :rules="rules" ref="form" label-width="80px" :hide-required-asterisk="true">
      <el-form-item label="标签" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="排序:" prop="sort">
        <el-slider v-model="form.sort" show-input :min="1">
        </el-slider>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="resetForm">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getTagById, updateTag, insertTag } from '@/api/tag'

export default {
  data() {
    return {
      visible: false,
      title: "",
      form: {
        id: null,
        name: "",
        sort: 1
      },
      rules: {
        name: [{ required: true, message: '请输入标签名', trigger: 'blur' }],
      }
    }
  },
  methods: {
    init(id) {
      this.form.id = id
      if (id) {
        this.title = "修改文章标签"
        getTagById(id).then(res => {
          if (res.flag) {
            Object.keys(this.form).forEach(key => this.form[key] = res.data[key])
            this.visible = true
          }
        })
      } else {
        this.visible = true
        this.title = "添加文章标签"
      }
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) updateTag(this.form).then(res => this.showMessage(res))
          else insertTag(this.form).then(res => this.showMessage(res))
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