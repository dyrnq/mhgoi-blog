<template>
  <el-dialog :title='`"${title}"相关评论`' width="60%" :close-on-click-modal="false" :visible.sync="visible" append-to-body>
    <div class="comments">
      <comment v-for="item in comments" :key="item.id" :comment="item" @deleteComment="handleDel">
        <template v-if="item.children && item.children.length">
          <comment v-for="reply in item.children" :key="reply.id" :comment="reply" @deleteComment="handleDel"></comment>
        </template>
      </comment>
    </div>
  </el-dialog>
</template>

<script>
import { getCommetnsByArticleId, deleteComment } from '@/api/comment'
import Comment from '@/components/Comment'

export default {
  components: {
    Comment
  },
  data() {
    return {
      visible: false,
      comments: [],
      title: '',
      articleId: ''
    }
  },
  methods: {
    init(article) {
      this.title = article.title
      this.articleId = article.id
      this.getComments()
    },
    getComments() {
      getCommetnsByArticleId(this.articleId).then(res => {
        if (res.flag) {
          this.comments = res.data
          this.visible = true
        }
      })
    },
    // 删除
    handleDel(commentId) {
      deleteComment(this.articleId, [commentId]).then(res => {
        if(res.flag) {
          this.$notify.success('删除成功')
          this.getComments()
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>>
