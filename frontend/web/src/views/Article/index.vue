<template>
  <div>
    <banner :src="article.imageUrl"></banner>
    <!--<el-button type="primary" icon="el-icon-back" circle class="go-back animate__animated animate__bounceInLeft" @click="goBack"></el-button>-->
    <el-row :gutter="40" class="article-container">
      <el-col class="hidden-xs-only hidden-sm-only" :md="3" :lg="4" :xl="8">
      </el-col>
      <el-col :xs="24" :sm="24" :md="18" :lg="16" :xl="16">
        <main class="site-main">
          <article class="hentry">
            <!-- 文章头部 -->
            <header class="entry-header animate__animated animate__bounceInRight">
              <!-- 标题输出 -->
              <div class="header-container">
                <div class="feature">
                  <img :src="article.avatar" />
                </div>
                <div class="breadcrumbs">
                  <h1 class="entry-title">{{ article.title }}</h1>
                  <div id="crumbs">最后更新时间：{{ article.modifiedTime | parseTime }}</div>
                </div>
              </div>
              <!-- <hr> -->
            </header>
            <!-- 正文输出 -->
            <div id="entry-container">
              <!-- v-highlight -->
              <div id="write" class="markdown-body" v-html="compiledMarkdown"></div>
            </div>
            <!-- 文章底部 -->
            <section-title>
              <footer class="post-footer">
                <!-- 阅读次数 -->
                <div class="post-like">
                  <i class="iconfont iconeyes"></i>
                  <span class="count">{{ article.viewsCount }}</span>
                </div>
                <!-- 赞助按钮 -->
                <div class="donate" @click="showDonate=!showDonate">
                  <span>赏</span><!--
                  <ul class="donate_inner" :class="{'show':showDonate}">
                    <li class="wedonate">
                      <p>微信</p>
                    </li>
                    <li class="alidonate">
                      <p>支付宝</p>
                    </li>
                  </ul>-->
                </div>
                <!-- 文章标签 -->
                <div class="post-tags" v-for="item in article.tagNames" :key="item">
                  <i class="iconfont iconcategory"></i>
                  <router-link :to="`/tags/${item}`">{{item}}</router-link>
                </div>
              </footer>
            </section-title>
            <!--声明-->
            <div class="open-message">
              <p>声明：{{websiteInfo.name}} 版权所有|本网站采用<a href="https://creativecommons.org/licenses/by-nc-sa/3.0/" target="_blank">BY-NC-SA</a>协议进行授权</p>
              <p>转载：转载请注明原文链接 - <a :href="`${websiteInfo.domain}/post/${article.slug}`">{{ article.title }}</a></p>
            </div>
            <!--评论-->
            <div class="comments">
                <ArtalkCards></ArtalkCards>
            </div>

          </article>
        </main>
      </el-col>
      <el-col class="hidden-xs-only hidden-sm-only" :md="3" :lg="4" :xl="8">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import ArtalkCards from '@/components/ArtalkCards'
import { getArticleById, getArticleBySlug, visitArticle } from '@/api/article'
// import { getComments, insertComment } from '@/api/comment'
import Banner from '@/components/banner'
import sectionTitle from '@/components/section-title'
// import comment from '@/components/comment'
import menuTree from '@/components/menu-tree'
// import commentMessageEditor from 'comment-message-editor'
import { mapState } from 'vuex'
//import { checkLogin } from '@/utils/auth'
import { showFullScreenLoading, tryHideFullScreenLoading } from '@/utils/loading'
import $ from 'jquery'
import marked from 'marked'
import hljs from 'highlight.js'
import 'highlight.js/styles/atom-one-dark.css'

export default {
  computed: {
    ...mapState(['isLogin', 'username', 'avatar', 'websiteInfo', 'socials']),
    compiledMarkdown () {
      return marked(this.article.contentMarkdown).replace(/<pre>/g, "<pre class='hljs'>")
    }
  },
  metaInfo () {
    return {
      title: this.article.title
    }
  },
  data () {
    return {
      showDonate: false,
      comments: [],
      menus: [],
      article: {
        contentMarkdown: ''
      },
      greetings: ''
    }
  },
  components: {
    ArtalkCards,
    Banner,
    sectionTitle,
    // comment,
    menuTree
    // commentMessageEditor
  },
  created () {
    marked.setOptions({
      renderer: new marked.Renderer(),
      highlight: function (code) {
        return hljs.highlightAuto(code).value
      },
      pedantic: false,
      gfm: true,
      tables: true,
      breaks: false,
      sanitize: false,
      smartLists: true,
      smartypants: false,
      xhtml: false
    })
    showFullScreenLoading()
    this.getArticle()
  },
  updated () {
    tryHideFullScreenLoading()
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    getArticle () {
      if (this.$route.params.id) {
        getArticleById(this.$route.params.id).then(res => {
          if (res.flag) {
            if (res.data) {
              // this.getComment()
              this.article = res.data
              this.$nextTick(() => {
                visitArticle(this.article.id)
                this.replaceDom()
              })
            } else {
              this.$router.replace('/404')
            }
          }
        })
      }
      if (this.$route.params.slug) {
        getArticleBySlug(this.$route.params.slug).then(res => {
          if (res.flag) {
            if (res.data) {
              // this.getComment()
              this.article = res.data
              this.$nextTick(() => {
                visitArticle(this.article.id)
                this.replaceDom()
              })
            } else {
              this.$router.replace('/404')
            }
          }
        })
      }
    },
    // getComment() {
    //   getComments(this.$route.params.id).then(res => {
    //     if (res.flag) this.comments = res.data
    //   })
    // },
    // publishComment(content) {
    //   if (!checkLogin()) return
    //   if (!this.checkVal(content)) {
    //     this.$message.warning("请输入评论内容")
    //     return
    //   }
    //   insertComment({ articleId: this.article.id, pid: null, toUserId: this.article.authorId, content: content })
    //     .then(res => {
    //       if (res.flag) {
    //         $('#editor .content').get(0).innerText = ''
    //         this.getComment()
    //         this.article.commentsCount = this.article.commentsCount + 1
    //       }
    //     })
    // },
    checkVal (str) {
      const reg = /&nbsp;|<div>|<\/div>|<br>/g
      const s = str.replace(reg, '')
      return s.trim() != ''
    },
    replaceDom () {
      const arr = []
      for (let i = 6; i > 0; i--) {
        const e = $(`.markdown-body h${i}`)
        for (let j = 0; j < e.length; j++) {
          // 把原来的h里的文本添加一个span,不然markdown的css解析不了
          const span = document.createElement('span')
          span.innerText = e[j].innerText
          e[j].removeChild(e[j].childNodes[0])
          e[j].appendChild(span)
        }
      }
    }
  }
}
</script>
<style scoped lang="less">
@import "../../assets/css/style.less";
@import "../../assets/css/orangeheart.css";
// @import url();

.el-button--primary {
  color: #fff;
  background-color: #aca7a7;
  border-color: #aca7a7;
  opacity: 0.4;
}

.el-col {
  min-height: 1px;
}
.go-back {
  margin: 0 0 10px 20px;
  position: fixed;
  left: 10px;
  bottom: 70px;
  z-index: 10;
}

.article-container {
  padding: 30px 20px 100px 20px;
  height: 100%;
}
.header-container {
  display: flex;
  flex-direction: row;
  margin-bottom: 20px;
}
.feature {
  margin-right: 10px;
}
.feature img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 50%;
  padding: 2px;
  border: 1px solid #dadada;
  position: relative;
  transition: all 0.2s linear;
  overflow: hidden;
  &:hover {
    transform: scale(1.1, 1.1);
    filter: contrast(130%);
  }
}
.comment-count {
  margin-bottom: 10px;
}

article.hentry {
  .entry-header {
    .entry-title {
      font-size: 23px;
      font-weight: 600;
      color: #737373;
      margin: 0.67em 0;

      &:before {
        // content: "#";
        margin-right: 6px;
        color: #d82e16;
        font-size: 20px;
        font-weight: 600;
      }
    }

    hr {
      height: 1px;
      border: 0;
      background: #efefef;
      margin: 15px 0;
    }

    .breadcrumbs {
      font-size: 14px;
      color: #c0c4cc;
      text-decoration: none;
      margin-bottom: 30px;
    }
  }

  footer.post-footer {
    width: 100%;
    padding: 20px 10px;
    margin-top: 30px;
    height: 65px;
    position: relative;
    i {
      font-size: 18px;
      margin-right: 5px;
    }
    .post-like {
      float: right;
      margin: 7px 0 0 20px;
    }

    .post-share {
      float: right;
      list-style: none;
      margin-right: 20px;
    }

    .donate {
      float: left;
      line-height: 36px;
      border-radius: 100%;
      -webkit-border-radius: 100%;
      -moz-border-radius: 100%;
      border: 1px solid #2b2b2b;
      &:hover {
        border: 1px solid goldenrod;
        span {
          color: goldenrod;
        }
      }
      span {
        color: #2b2b2b;
        padding: 10px;
        position: relative;
        cursor: pointer;
      }

      .donate_inner {
        display: none;
        margin: 0;
        list-style: none;
        position: absolute;
        left: 80px;
        top: -40px;
        background: #fff;
        padding: 10px;
        border: 1px solid #ddd;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
        border-radius: 3px;
        &.show {
          display: block;
        }
        li {
          float: left;
        }

        img {
          width: 100px;
        }
        p {
          text-align: center;
          font-size: 15px;
          color: #d2d2d2;
          line-height: 1rem;
        }
      }

      .donate_inner:after,
      .donate_inner:before {
        content: "";
        position: absolute;
        left: 0;
        bottom: 45%;
        margin-left: -8px;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
        border-right: 8px solid #fff;
      }

      .donate_inner:before {
        left: -1px;
        border-right: 8px solid #ddd;
      }
    }

    .post-tags {
      margin: 7px 0 0 20px;
      float: left;
      text-transform: uppercase;
      a:hover {
        color: #ff6d6d;
      }
    }
  }
  .open-message {
    margin: 50px 0;
    position: relative;
    background: #3f3f3f;
    padding: 10px 30px;
    border-radius: 3px;
    font-size: 14px;
    color: #fff;
    &:after {
      content: "";
      border-left: 10px solid transparent;
      border-right: 10px solid transparent;
      border-bottom: 10px solid #2b2b2b;
      position: absolute;
      top: -8px;
      left: 48%;
    }
    p {
      margin: 10px 0;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    a {
      color: #a0dad0;
      padding: 0 5px;
    }
  }
}
</style>
