<template>
  <el-dialog class="edit-dialog" :title="title" :close-on-click-modal="false" :visible.sync="visible" append-to-body @close="resetForm">
    <el-form :model="form" :rules="rules" ref="form" label-width="100px">
      <el-form-item label="API模块" prop="moduleId">
        <el-input v-model="form.moduleName"></el-input>
      </el-form-item>
      <el-form-item label="API名称" prop="apiName">
        <el-input v-model="form.apiName"></el-input>
      </el-form-item>
      <el-form-item label="请求地址" prop="apiUrl">
        <el-input v-model="form.apiUrl"></el-input>
      </el-form-item>
      <el-form-item label="请求方式" prop="apiMethod">
        <el-select size="medium" v-model="apiMethods" multiple placeholder="请选择">
          <el-option v-for="item in options" :key="item.value" :label="item.value" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否开放" prop="open">
        <el-switch v-model="form.open" :active-value="1" :inactive-value="0" active-color="#13ce66" inactive-color="#ff4949">
        </el-switch>
      </el-form-item>
      <el-form-item label="排序:" prop="apiSort">
        <el-slider v-model="form.apiSort" show-input :min="1">
        </el-slider>
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
import { getApiById, updateApi, insertApi } from '@/api/backendApi'


export default {
  data() {
    return {
      visible: false,
      title: "",
      apiMethods: [],
      form: {
        id: "",
        moduleId: "",
        apiName: "",
        apiUrl: "",
        apiMethod: "",
        open: 0,
        apiSort: 1,
        description: "",
        moduleName: ""
      },
      rules: {
        apiUrl: [{ required: true, message: '请输入API地址', trigger: 'blur' }],
        apiMethod: [{ required: true, message: '请选择请求方式', trigger: 'blur' }]
      },
      options: [{ value: 'GET' }, { value: 'POST' }, { value: 'PUT' }, { value: 'DELETE' }],
      moduleName: {},
    }
  },
  methods: {
    init(apiModule, apiId) {
      this.form.moduleId = apiModule.key
      this.form.moduleName = apiModule.label
      this.form.id = apiId
      if (apiId) {
        this.title = "修改API"
        getApiById(apiId).then(res => {
          Object.keys(this.form).forEach(key => this.form[key] = res.data[key])
          this.apiMethods = this.form.apiMethod.split(",")
          this.visible = true
        })
      } else {
        this.visible = true
        this.title = "添加API"
      }
    },
    showMessage(res) {
      if (res.flag) {
        this.$message({
          message: res.msg,
          type: 'success'
        });
        this.visible = false
        this.$emit("loadData")
        this.resetForm()
      }
    },
    save() {
      this.form.apiMethod = this.apiMethods.join(',')
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) updateApi(this.form).then(res => this.showMessage(res))
          else insertApi(this.form).then(res => this.showMessage(res))
        } else {
          return false
        }
      });
    },
    resetForm() {
      this.visible = false
      this.$refs.form.resetFields()
      Object.keys(this.form).forEach(key => {
        this.form[key] = null
      })
      this.form.apiSort = 1
      this.apiMethods = []
    }
  }
}
</script>

<style scoped>
.edit-dialog {
  padding-bottom: 20px;
}
</style>