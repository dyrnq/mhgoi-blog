<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="header">
        <svg class="icon-svg" aria-hidden="true">
          <use xlink:href='#el-ali-gonggao'></use>
        </svg>
        <span>公告栏</span>
      </div>
      <div v-for="(item, index) in notices" :key="index" class="content">
        <i class="el-icon-delete" style="color:#F56C6C;cursor:pointer;margin-right:10px" @click="deleteNotice(item)"></i>
        <i class="el-ali-xiugai" style="color:#409EFF;cursor:pointer;margin-right:20px" @click="editNotice(item)"></i>
        <p :class="{ 'important-color':item.important}">
          {{index+1}}.{{item.content}}
        </p>
      </div>
      <el-button type="primary" @click="addNotice">新增</el-button>
    </el-card>

    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" append-to-body @close="resetForm">
      <el-form :model="form" :rules="rules" ref="form" label-width="80px" :hide-required-asterisk="true">
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-slider v-model="form.sort" show-input :min="1">
          </el-slider>
        </el-form-item>
        <el-form-item label="重要" prop="important">
          <el-switch v-model="form.important" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getNotice, updateNotice, insertNotice, deleteNotice } from '@/api/notice'
import { insertMenu } from '@/api/menu';

export default {
  data() {
    return {
      notices: [],
      title: '',
      visible: false,
      form: {
        id: '',
        content: '',
        important: 0,
        sort: 1
      },
      rules: {
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
      }
    };
  },
  created() {
    this.getNotice()
  },
  methods: {
    getNotice() {
      getNotice().then(res => {
        if (res.flag) this.notices = res.data
      })
    },
    addNotice() {
      this.title = '新增公告'
      Object.keys(this.form).forEach(key => this.form[key] = null)
      this.visible = true
    },
    editNotice(item) {
      this.title = '修改公告'
      Object.keys(this.form).forEach(key => this.form[key] = item[key])
      this.visible = true
    },
    deleteNotice(item) {
      this.$confirm('确认删除', '提示', {
        type: 'warning'
      }).then(() => {
        deleteNotice([item.id]).then(res => {
          if (res.flag) {
            this.$notify.success('删除成功')
            this.getNotice()
          }
        })
      }).catch(() => { })
    },
    resetForm() {
      this.visible = false
      this.$refs.form.resetFields()
      Object.keys(this.form).forEach(key => {
        this.form[key] = null
      })
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) updateNotice(this.form).then(res => this.showMessage(res))
          else insertNotice(this.form).then(res => this.showMessage(res))
        } else {
          return false
        }
      });
    },
    showMessage(res) {
      if (res.flag) {
        this.$notify.success(res.msg)
        this.visible = false
        this.getNotice()
        this.resetForm()
      }
    },
  }
}
</script>

<style lang="scss" scoped>
.header {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.important-color {
  color: #e6a23c;
}
.content {
  display: flex;
  flex-direction: row;
  align-items: center;
}
</style>