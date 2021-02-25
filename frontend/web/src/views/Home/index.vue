<template>
  <div class="home">
    <banner isHome="true" :src="websiteInfo.banner"></banner>
    <el-row :gutter="largeScreen?40:0" class="home-container">
      <el-col class="hidden-xs-only hidden-sm-only" :md="6" :lg="5" :xl="4">
        <div class="left-sticky">
          <category-list :categories="categories" v-on:resetPage="resetPage" />
          <!--<tags style="margin-top:10px"></tags>-->
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="14" :xl="16">
        <div class="site-content animate" id="main-container">
          <!--通知栏-->
          <div class="notify">
            <quote v-if="notice">{{notice}}</quote>
            <div class="search-result" v-if="hideSlogan">
              <span v-if="searchWords">搜索结果："{{searchWords}}" 相关文章</span>
            </div>
          </div>
          <!--文章列表-->
          <main class="site-main" :class="{'search':hideSlogan}">
            <!--<section-title v-if="!hideSlogan">推荐</section-title>-->
            <post :posts="postList"></post>
          </main>
          <!--加载更多-->
          <div class="more" v-if="hasNextPage">
            <div class="more-btn" @click="loadMore">More</div>
          </div>
          <div v-else class="load-end"> --------- 我也是有底线的啦 --------- </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :md="6" :lg="5" :xl="4">
        <div class="right-sticky">
          <notice></notice>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { queryPage } from '@/api/article'
import { getCategories } from '@/api/category'
import Banner from '@/components/banner'
import sectionTitle from '@/components/section-title'
import Quote from '@/components/quote'
import Post from '@/components/Post'
import Notice from '@/components/Notice'
import CategoryList from '@/components/CategoryList'
import Tags from '@/components/Tags'
import { mapState } from 'vuex'
import { showFullScreenLoading, tryHideFullScreenLoading } from '@/utils/loading'
import $ from 'jquery'

export default {
  name: 'Home',
  props: ['cate', 'words'],
  metaInfo () {
    return {
      title: this.websiteInfo.name
    }
  },
  data () {
    return {
      postList: [],
      currPage: 1,
      pageSize: 5,
      totalCount: 5,
      hasNextPage: true,
      categories: [],
      // queryCateId: '',
      queryCategory: '',
      queryMore: false,
      screenWidth: document.body.clientWidth
    }
  },
  beforeRouteEnter (to, from, next) {
    document.title = ''
    next()
  },
  beforeRouteUpdate (to, from, next) {
    // alert(JSON.stringify(to));
    // alert(to.params.slug)
    this.queryMore = false
    this.queryCategory = to.params.slug
    this.$store.commit('setSearchWords', '')
    this.fetchList({
      categorySlug: this.queryCategory
    })
    next()
  },
  computed: {
    ...mapState(['searchWords', 'websiteInfo']),
    hideSlogan () {
      return this.searchWords
    },
    notice () {
      return this.$store.getters.notice
    },
    largeScreen () {
      return this.screenWidth > 800
    }
  },
  watch: {
    searchWords (newWord, oldWord) {
      this.queryMore = false
      if (newWord && newWord != oldWord) {
        this.fetchList({
          categorySlug: this.queryCategory,
          searchWords: newWord
        })
      }
    },
    screenWidth (newValue) {
      // 为了避免频繁触发resize函数导致页面卡顿，使用定时器
      if (!this.timer) {
        // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
        this.screenWidth = newValue
        this.timer = true
        setTimeout(() => {
          this.timer = false
        }, 400)
      }
    }
  },
  created () {
    showFullScreenLoading()
  },
  mounted () {
    // this.queryCateId = this.$route.query.cate
    this.queryCategory = this.$route.params.slug
    this.fetchList({
      categorySlug: this.queryCategory,
      searchWords: this.searchWords
    })
    this.fetchCategory()
    window.onresize = () => { // 监听屏幕变化
      this.screenWidth = document.body.clientWidth
    }
    // alert(JSON.stringify(this.websiteInfo));
    // document.title = this.websiteInfo.name
  },
  updated () {
    tryHideFullScreenLoading()
  },
  methods: {
    fetchList (queryWrap) {
      queryPage(this.currPage, this.pageSize, queryWrap).then(res => {
        if (res.flag) {
          if (!this.queryMore) this.postList = []
          this.postList.push(...res.data.rows)
          this.totalCount = res.data.counts
          this.hasNextPage = (this.currPage * this.pageSize) < this.totalCount
          this.$nextTick(() => {
            this.generateLeftAreaHeight()
          })
        }
      })
    },
    fetchCategory () {
      getCategories({ grouped: true }).then(res => {
        if (res.flag) {
          let counts = 0
          res.data.forEach(item => counts += item.counts)
          res.data.unshift({ name: '全部分类', icon: 'el-ali-ziyuan', counts: counts })
          this.categories = res.data
        }
      })
    },
    generateLeftAreaHeight () {
      const height = $('#main-container').get(0).offsetHeight + 'px'
      $('#left-area').css('min-height', height)
      $('#right-area').css('min-height', height)
    },
    loadMore () {
      this.queryMore = true
      this.currPage += 1
      this.fetchList()
    },
    resetPage () {
      this.currPage = 1
    }
  },
  components: {
    Banner,
    sectionTitle,
    Post,
    Quote,
    CategoryList,
    Notice,
    Tags
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/style.less";

.left-sticky,
.right-sticky {
  top: 90px;
  position: sticky;
  z-index: 1;
}

.home-container {
  padding: 5%;
}

#left-area,
#right-area {
  position: relative;
}

.el-col {
  min-height: 1px;
}

.load-end {
  text-align: center;
  margin: 50px 0;
}

.site-content {
  .notify {
    border-radius: 3px;
    & > div {
      padding: 20px;
    }
  }

  .search-result {
    padding: 15px 20px;
    text-align: center;
    font-size: 20px;
    font-weight: 400;
    border: 1px dashed #ddd;
    color: #828282;
    margin-top: 20px;
  }
}

.site-main {
  padding-top: 30px;

  &.search {
    padding-top: 0;
  }
}

.more {
  margin: 50px 0;
  .more-btn {
    width: 100px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    color: #adadad;
    border: 1px solid #adadad;
    border-radius: 20px;
    margin: 0 auto;
    cursor: pointer;
    &:hover {
      color: #8fd0cc;
      border: 1px dashed #8fd0cc;
    }
  }
}

/******/
@media (max-width: 800px) {
  .site-main {
    padding-top: 40px;
  }

  .site-content {
    .notify {
      margin: 30px 0 0 0;
    }

    .search-result {
      margin-bottom: 20px;
      font-size: 16px;
    }
  }
}
/******/
</style>
