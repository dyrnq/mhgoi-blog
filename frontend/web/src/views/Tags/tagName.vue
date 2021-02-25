<template>
  <div class="container">
    <el-row :gutter="40">
      <!-- <el-col :xs="24" :sm="9" :md="7" :lg="6" :xl="6">
        <tags :active="active" class="tags"></tags>
      </el-col> -->
      <el-col :xs="24" :sm="15" :md="17" :lg="18" :xl="18">
        <el-row :gutter="20">
          <el-col :xs="12" :sm="12" :md="8" :lg="6" :xl="6" v-for="article in articles" :key="article.id">
            <router-link :to="`/post/${article.slug}`">
              <article class="card">
                <div class="card__info-hover">
                </div>
                <div class="card__img" :style="{'background-image':`url(${article.imageUrl!=''?article.imageUrl:logo})`}"></div>
                <a href="#" class="card_link">
                  <div class="card__img--hover" :style="{'background-image':`url(${article.imageUrl!=''?article.imageUrl:logo})`}"></div>
                </a>
                <div class="card__info">
                  <h3 class="card__title">{{article.title}}</h3>
                </div>
              </article>
            </router-link>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getArticles } from '@/api/article'
// import Tags from '@/components/Tags';

export default {
  name: 'Tag',
  components: {
    // Tags
  },
  metaInfo () {
    return {
      title: '标签-' + this.$route.params.id
    }
  },
  data () {
    return {
      articles: [],
      logo: require('../../assets/images/logo.png'),
      websiteInfo: {},
      socials: {}
    }
  },
  computed: {
    // active() {
    //   return this.$route.params.id
    // }
  },
  watch: {
    $route (to, from) {
      if (to.path.indexOf('tags') > -1) {
        this.getArticles()
      }
    }
  },
  mounted () {
    this.getArticles()
    this.getWebSiteInfo()
    this.getSocial()
  },
  methods: {
    getArticles () {
      // alert(JSON.stringify(this.$route.params));
      const tagName = this.$route.params.id
      getArticles({ tagName: tagName }).then(res => {
        if (res.flag) this.articles = res.data
      })
    },
    getWebSiteInfo () {
      this.$store.dispatch('getSiteInfo').then(data => {
        this.websiteInfo = data
      })
    },
    getSocial () {
      this.$store.dispatch('getSocials').then(data => {
        this.socials = data
      })
    }
  }
}
</script>

<style scoped>
@media screen and (max-width: 768px) {
  .container {
    padding: 20% 5%;
  }
}
@media screen and (min-width: 768px) and (max-width: 992px) {
  .container {
    padding: 15% 5%;
  }
}
@media screen and (min-width: 992px) and (max-width: 1200px) {
  .container {
    padding: 10% 5%;
  }
}
@media screen and (min-width: 1200px) {
  .container {
    padding: 10% 5%;
  }
}

.tags {
  margin-bottom: 10px;
}

@import url("https://fonts.loli.net/css?family=Roboto+Slab:100,300,400,700");
@import url("https://fonts.loli.net/css?family=Raleway:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i");

* {
  box-sizing: border-box;
}

body,
html {
  font-family: "Roboto Slab", serif;
  margin: 0;
  width: 100%;
  height: 100%;
  padding: 0;
}

body {
  background-color: #d2dbdd;
  display: flex;
  display: -webkit-flex;
  -webkit-justify-content: center;
  -webkit-align-items: center;
  justify-content: center;
  align-items: center;
}

.card__like {
  width: 18px;
}

.card__clock {
  width: 15px;
  vertical-align: middle;
  fill: #ad7d52;
}
.card__time {
  font-size: 12px;
  color: #ad7d52;
  vertical-align: middle;
  margin-left: 5px;
}

.card__clock-info {
  float: right;
}

.card__img {
  visibility: hidden;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  height: 235px;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
}

.card__info-hover {
  position: absolute;
  padding: 16px;
  width: 100%;
  opacity: 0;
  top: 0;
}

.card__img--hover {
  transition: 0.2s all ease-out;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  width: 100%;
  position: absolute;
  height: 235px;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
  top: 0;
}
.card {
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0, 1);
  background-color: #fff;
  position: relative;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  margin-bottom: 20px;
}
.card:hover {
  box-shadow: 0px 30px 18px -8px rgba(0, 0, 0, 0.1);
  transform: scale(1.1, 1.1);
}

.card__info {
  z-index: 2;
  background-color: #fff;
  border-bottom-left-radius: 12px;
  border-bottom-right-radius: 12px;
  padding: 16px 24px 24px 24px;
}

.card__category {
  font-family: "Raleway", sans-serif;
  text-transform: uppercase;
  font-size: 13px;
  letter-spacing: 2px;
  font-weight: 500;
  color: #868686;
}

.card__title {
  margin-top: 5px;
  margin-bottom: 10px;
  font-family: "Roboto Slab", serif;
}

.card__by {
  font-size: 12px;
  font-family: "Raleway", sans-serif;
  font-weight: 500;
}

.card__author {
  font-weight: 600;
  text-decoration: none;
  color: #ad7d52;
}

.card:hover .card__img--hover {
  height: 100%;
  opacity: 0.3;
}

.card:hover .card__info {
  background-color: transparent;
  position: relative;
}

.card:hover .card__info-hover {
  opacity: 1;
}
</style>
