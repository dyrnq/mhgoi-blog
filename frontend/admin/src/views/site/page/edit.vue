<template>
  <div class="app-container">
    <el-form ref="pageForm" :model="pageForm" status-icon hide-required-asterisk label-width="auto" :rules="rules">
      <el-row>
        <el-col :span="8">
      <el-form-item label="标题" prop="title">
        <el-input v-model="pageForm.title" placeholder="标题" maxlength="50" show-word-limit></el-input>
      </el-form-item>

        </el-col>
      
        <el-col :span="3">
              <el-button class="publish" type="success" @click="publish">发布</el-button>
            </el-col>

      </el-row>

    <el-row>
        <el-col :span="8">
          <el-form-item label="slug" prop="slug">
            <el-input v-model="pageForm.slug" placeholder="slug" maxlength="200" show-word-limit></el-input>
          </el-form-item>
        </el-col>
      </el-row>

     

      <mavon-editor v-model.trim="pageForm.contentMarkdown" ref="editor" :codeStyle="codeStyle" :boxShadow="false" :fontSize="fontSize" @imgAdd="imgAdd" />
    </el-form>

  </div>
</template>

<script>

import { insertPage, getPageById, updatePage } from '@/api/page'
import fetch from '@/utils/fetch'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import { uploadImg, myURL, uploadURL } from '@/utils/upload'
import { getToken } from '@/utils/auth'

export default {
  
  components: {
    mavonEditor
  },
  data() {
    return {
      myHeaders: {Authorization: getToken()},
      codeStyle: 'atom-one-dark',
      fontSize: '15px',
      pageForm: {
        id: '',
        title: "",
        slug: "",
        contentMarkdown: ""
      },
 
      rules: {
        title: [{ required: true, message: '请输入文章标题', trigger: 'change' }],
        slug: [{ required: true, message: '请输入slug', trigger: 'change' }],
      },
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      uploadURL: uploadURL,
      formData: {
        token: '',
        key: ''
      },
    }
  },
  created() {
    let id = this.$route.query.id
    if (id) this.getPage(id)
    //this.initFormData()
  },
  methods: {
    // async initFormData() {
    //   let token = await this.$store.dispatch('user/getUpToken')
    //   this.formData.token = token
    //   this.formData.key = 'article/img/' +  Date.now()
    // },
    beforeUpload(file) {
      const isIMG =
        file.type === 'image/jpg' ||
        file.type === 'image/jpeg' ||
        file.type === 'image/png'
      const isLt = file.size / 1024 / 1024 <= 50
      if (!isIMG) {
        this.$message.error('上传头像图片只支持jpg、jpeg、png格式!')
      }
      if (!isLt) {
        this.$message.error('上传头像图片大小不能超过50M!')
      }
      return isLt && isIMG
    },
    // handleSuccess(res, file) {
    //   this.pageForm.imageUrl = myURL + res.data.url;
    // },
    onRemoveTxt(file){
      this.pageForm.imageUrl = '';
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    getPage(id) {
      getPageById(id).then(res => {
        if (res.flag) {
          Object.keys(this.pageForm).forEach(key => this.pageForm[key] = res.data[key])
        }
      })
    },
    async imgAdd(pos, file) {
      let imgUrl = await uploadImg(file)
      //alert(JSON.stringify(imgUrl))
      this.$refs.editor.$img2Url(pos,myURL + imgUrl.data.url);
    },
    publish() {
      this.$refs.pageForm.validate((valid) => {
        if (valid) {
          if (this.pageForm.contentMarkdown == '') {
            this.$message.warning("文章内容不能为空")
            return
          }
          if (this.pageForm.id) {
            updatePage(this.pageForm).then(res => {
              if (res.flag) this.$notify.success("更新成功")
            })
          } else {
            insertPage(this.pageForm).then(res => {
              if (res.flag) {
                this.$notify.success("发布成功")
                this.pageForm.id = res.data.id;
                }
            })
          }
        } else {
          return false;
        }
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.publish {
  width: 100%;
  margin-left:30px;
  //margin-top: 10px;
}
</style>