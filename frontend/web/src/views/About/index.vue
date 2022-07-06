<template>
  <div class="about">
    <banner></banner>
    <div class="site-content">
      <div class="content-warp">

        <div id="write" class="markdown-body" v-html="compiledMarkdown"></div>
        <div id="Guestbook">
            <ArtalkCards></ArtalkCards>
        </div>

      </div>
    </div>
  </div>
</template>
<script>
import ArtalkCards from '@/components/ArtalkCards'
import sectionTitle from '@/components/section-title'
import Banner from '@/components/banner'
import { getTime, getTimeInterval } from '@/utils'
import { getPageBySlug } from '@/api/page'
import { showFullScreenLoading, tryHideFullScreenLoading } from '@/utils/loading'
import $ from 'jquery'
import { marked } from 'marked';
import hljs from 'highlight.js'
import 'highlight.js/styles/atom-one-dark.css'

export default {
  name: 'About',
  computed: {
    compiledMarkdown () {
      return marked(this.page.contentMarkdown).replace(/<pre>/g, "<pre class='hljs'>")
    }
  },
  metaInfo () {
    return {
      title: '关于'
    }
  },
  data () {
    return {
      list: [],
      page: {
        contentMarkdown: ''
      }
    }
  },
  components: {
    sectionTitle,
    Banner,
    ArtalkCards
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
    this.getPage()
  },
  updated () {
    tryHideFullScreenLoading()
  },
  methods: {
    goBack () {
      this.$router.go(-1)
    },
    getPage () {
      getPageBySlug('about').then(res => {
        if (res.flag) {
          if (res.data) {
            this.page = res.data
            this.$nextTick(() => {
              this.replaceDom()
            })
          } else {
            this.$router.replace('/404')
          }
        }
      })
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

.about {
  padding-top: 40px;
}

.content-warp {
  margin-top: 80px;

  .about-info {
    margin: 30px 0;

    span {
      color: red;
      margin-right: 10px;
    }

    .info-card {
      min-height: 100px;
      padding: 20px;
      border-radius: 3px;
      margin: 30px 0 50px 0;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      p {
        line-height: 1.7rem;
      }
    }
  }
  .contactForm {
    width: 100%;
    padding: 20px;
    .form-item {
      align-items: center;
      display: flex;
      &:not(:last-child) {
        margin-bottom: 20px;
      }
      label {
        width: 80px;
      }
      .v {
        min-height: 40px;
        line-height: 20px;
        border-radius: 3px;
        padding: 2px 10px;
        outline: none;
        border: 1px solid #8fd0cc;
        width: 100%;
        resize: vertical;
      }
      button {
        width: 100px;
        height: 40px;
        border-radius: 3px;
        outline: 0;
        border-style: none;
        cursor: pointer;
        background-color: #409eff;
        color: white;
        &:hover {
          opacity: 0.8;
        }
      }
    }
  }
}

/*******/
@media (max-width: 800px) {
  .content-warp {
    margin-top: 0;
  }
}
</style>
