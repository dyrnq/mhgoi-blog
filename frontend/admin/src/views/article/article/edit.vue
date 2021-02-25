<template>
  <div class="app-container">
    <el-form ref="articleForm" :model="articleForm" status-icon hide-required-asterisk label-width="auto" :rules="rules">
      <el-row>
        <el-col :span="8">
      <el-form-item label="标题" prop="title">
        <el-input v-model="articleForm.title" placeholder="标题" maxlength="50" show-word-limit></el-input>
      </el-form-item>

        </el-col>
      
        <el-col :span="3">
              <el-button class="publish" type="success" @click="publish">发布</el-button>
            </el-col>

      </el-row>

    <el-row>
        <el-col :span="8">
          <el-form-item label="slug" prop="slug">
            <el-input v-model="articleForm.slug" placeholder="slug" maxlength="200" show-word-limit></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="分类" prop="categoryId">
            <el-select v-model="articleForm.categoryId" filterable placeholder="请选择文章分类">
              <el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="标签" prop="tagNames">
            <!-- <el-select v-model="articleForm.tagIds" :multiple-limit="3" clearable filterable multiple placeholder="请选择文章标签">
              <el-option v-for="item in tags" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select> -->

            <el-input-tag v-model="articleForm.tagNames"></el-input-tag>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="置顶" prop="top">
            <el-switch v-model="articleForm.top" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-col>
    
      </el-row>

      <el-row>
        <el-form-item label="文章图片" prop="imgUrl">
          <el-upload :headers="myHeaders" :on-remove="onRemoveTxt" :limit="1" :file-list="fileList" :data="formData" :action="uploadURL" list-type="picture-card" :on-preview="handlePictureCardPreview" :on-success="handleSuccess" :before-upload="beforeUpload">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible" append-to-body>
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
      </el-row>

    <el-row>
          <el-col :span="10">
          <el-form-item label="概述" prop="summary">
            <el-input type="textarea" :rows="4" placeholder="请输入文章概述" maxlength="200" show-word-limit v-model="articleForm.summary">
            </el-input>
          </el-form-item>
            </el-col>
            
    </el-row>

      <mavon-editor v-model.trim="articleForm.contentMarkdown" ref="editor" :codeStyle="codeStyle" :boxShadow="false" :fontSize="fontSize" @imgAdd="imgAdd" />
    </el-form>
    <!--
    <el-button class="publish" type="success" @click="publish">发布</el-button>
    -->
  </div>
</template>

<script>
import {ElInputTag} from 'el-input-tag'
import { getCategory } from '@/api/category'
//import { getTag } from '@/api/tag'
import { insertArticle, getArticleById, updateArticle } from '@/api/article'
import fetch from '@/utils/fetch'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import { uploadImg, myURL, uploadURL } from '@/utils/upload'
import { getToken } from '@/utils/auth'

export default {
  
  components: {
    mavonEditor,
    ElInputTag
  },
  data() {
    return {
      myHeaders: {Authorization: getToken()},
      codeStyle: 'atom-one-dark',
      fontSize: '15px',
      articleForm: {
        id: '',
        categoryId: '',
        title: "",
        slug: "",
        summary: '',
        contentMarkdown: "",
        imageUrl: '',
        top: 0,
        tagNames: []
      },
      categories: [],
      tags: [],
      rules: {
        title: [{ required: true, message: '请输入文章标题', trigger: 'change' }],
        slug: [{ required: true, message: '请输入slug', trigger: 'change' }],
        categoryId: [{ required: true, message: '请选择文章分类', trigger: 'change' }],
        //summary: [{ required: true, message: '文章概述不能为空', trigger: 'change' }],
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
    if (id) this.getArticle(id)
    this.getCategory()
    //this.getTag()
    this.initFormData()
  },
  methods: {
    async initFormData() {
      // let token = await this.$store.dispatch('user/getUpToken')
      // this.formData.token = token
      this.formData.key = 'article/img/' +  Date.now()
    },
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
    handleSuccess(res, file) {
      this.articleForm.imageUrl = myURL + res.data.url;
    },
    onRemoveTxt(file){
      this.articleForm.imageUrl = '';
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    getCategory() {
      getCategory().then(res => {
        if (res.flag) {
          this.categories = res.data
          let id = this.$route.query.id
          if (!id){
            //给个默认类别
            if(this.categories.length>0){
              this.articleForm.categoryId=this.categories[0].id
            }
          }

        }
      })
    },
    // getTag() {
    //   getTag().then(res => {
    //     if (res.flag) this.tags = res.data
    //   })
    // },
    getArticle(id) {
      getArticleById(id).then(res => {
        if (res.flag) {
          if (res.data.imageUrl !== null && res.data.imageUrl !== undefined && res.data.imageUrl !== '') {
            this.fileList.push({ url: res.data.imageUrl })
          }
          // let list = []
          // res.data.tags.forEach(tag => list.push(tag.id))
          Object.keys(this.articleForm).forEach(key => this.articleForm[key] = res.data[key])
          // this.articleForm.tagIds = list
        }
      })
    },
    async imgAdd(pos, file) {
      let imgUrl = await uploadImg(file)
      this.$refs.editor.$img2Url(pos, myURL + imgUrl.data.url);
    },
    publish() {
      this.$refs.articleForm.validate((valid) => {
        if (valid) {
          if (this.articleForm.contentMarkdown == '') {
            this.$message.warning("文章内容不能为空")
            return
          }
          if (this.articleForm.id) {
            updateArticle(this.articleForm).then(res => {
              if (res.flag) this.$notify.success("更新成功")
            })
          } else {
            insertArticle(this.articleForm).then(res => {
              if (res.flag) {
                this.$notify.success("发布成功")
                this.articleForm.id = res.data.id;
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