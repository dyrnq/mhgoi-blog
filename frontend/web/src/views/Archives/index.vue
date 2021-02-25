<template>
  <div class="container">
    <el-row :gutter="10">
      <el-col class="hidden-xs-only hidden-sm-only" :md="6" :lg="4" :xl="4" style="min-height:1px;">
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="16" :xl="16">
        <div class="container-fluid">
          <div class="row example-split">
            <div class="example-title">
              <h2>文章归档</h2>
            </div>
            <div>
              <ul class="timeline timeline-split">
                <template v-for="(item, index) in archives">
                  <li class="timeline-item period wow animate__animated animate__fadeInUp" :key="index">
                    <div class="timeline-info"></div>
                    <div class="timeline-marker"></div>
                    <div class="timeline-content">
                      <h2 class="timeline-title">{{ item.archiveTime }}</h2>
                    </div>
                  </li>
                  <li class="timeline-item wow animate__animated animate__fadeInUp" v-for="(item2, index2) in item.archiveArticles" :key="index2.id">
                    <div class="timeline-info">
                      <span>{{ item2.day }}日</span>
                    </div>
                    <div class="timeline-marker"></div>
                    <div class="timeline-content">
                      <router-link :to="`/post/${item2.slug}`">
                        <h3 class="timeline-title">{{ item2.title }}</h3>
                      </router-link>
                    </div>
                  </li>
                </template>
              </ul>
            </div>
          </div>
        </div>
      </el-col>
      <el-col class="hidden-xs-only hidden-sm-only" :md="6" :lg="4" :xl="4">
        <div class="grid-content bg-purple"></div>
      </el-col>
    </el-row>
  </div>

</template>

<script>
import { WOW } from 'wowjs'
import { getArchives } from '@/api/article'

export default {
  metaInfo () {
    return {
      title: '归档'
    }
  },
  data () {
    return {
      archives: [],
      websiteInfo: {},
      socials: {}
    }
  },
  watch: {
    archives () {
      this.$nextTick(() => { // 在dom渲染完后,再执行动画
        var wow = new WOW({
          live: false
        })
        wow.init()
      })
    }
  },
  created () {

  },

  mounted () {
    this.getWebSiteInfo()
    this.fetchArchives()
    this.getSocial()
  },

  methods: {
    fetchArchives () {
      getArchives().then(res => {
        if (res.flag) this.archives = res.data
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

<style lang="less" scoped>
.container {
  padding: 100px 10%;
}
h1,
h2,
h3,
h4,
h5,
h6 {
  color: #3d4351;
  margin-top: 0;
}
// a {
//   color: #ff6b6b;
//   &:hover {
//     color: scale-color(#ff6b6b, 32%);
//     text-decoration: none;
//   }
// }
.example-header {
  background: #3d4351;
  color: #fff;
  font-weight: 300;
  padding: 3em 1em;
  text-align: center;
  h1 {
    color: #fff;
    font-weight: 300;
    margin-bottom: 20px;
  }
  p {
    font-size: 12px;
    text-transform: uppercase;
    letter-spacing: 3px;
    font-weight: 700;
  }
}
.container-fluid {
  .row {
    padding: 0 0 4em 0;
    &:nth-child(even) {
      background: #f1f4f5;
    }
  }
}

.example-title {
  text-align: center;
  padding: 1em 0;
  font-size: 30px;
}

/*==================================
    TIMELINE
==================================*/

/*-- GENERAL STYLES
    ------------------------------*/
.timeline {
  line-height: 1.4em;
  list-style: none;
  margin: 0;
  padding: 0;
  width: 100%;
  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: inherit;
  }
}

/*----- TIMELINE ITEM -----*/

.timeline-item {
  padding-left: 40px;
  position: relative;
  &:last-child {
    padding-bottom: 0;
  }
}

/*----- TIMELINE INFO -----*/

.timeline-info {
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 3px;
  margin: 0 0 0.5em 0;
  text-transform: uppercase;
  white-space: nowrap;
}
/*----- TIMELINE MARKER -----*/

.timeline-marker {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  width: 15px;
  &:before {
    background: #ff6b6b;
    border: 3px solid transparent;
    border-radius: 100%;
    content: "";
    display: block;
    height: 10px;
    position: absolute;
    top: 4px;
    left: -1px;
    width: 10px;
    transition: background 0.3s ease-in-out, border 0.3s ease-in-out;
  }
  &:after {
    content: "";
    width: 3px;
    background: #ccd5db;
    display: block;
    position: absolute;
    top: 24px;
    bottom: 0;
    left: 6px;
  }
  .timeline-item:last-child &:after {
    content: none;
  }
}
.timeline-item:not(.period):hover .timeline-marker:before {
  background: transparent;
  border: 3px solid #ff6b6b;
}

/*----- TIMELINE CONTENT -----*/

.timeline-content {
  padding-bottom: 40px;
  p:last-child {
    margin-bottom: 0;
  }
}

/*----- TIMELINE PERIOD -----*/

.period {
  padding: 0;
  .timeline-info {
    display: none;
  }
  .timeline-marker {
    &:before {
      background: transparent;
      content: "";
      width: 15px;
      height: auto;
      border: none;
      border-radius: 0;
      top: 0;
      left: 0;
      bottom: 30px;
      position: absolute;
      border-top: 3px solid #ccd5db;
      border-bottom: 3px solid #ccd5db;
    }
    &:after {
      content: "";
      height: 32px;
      top: auto;
    }
  }
  .timeline-content {
    padding: 40px 0 70px;
  }
  .timeline-title {
    margin: 0;
    font-size: 30px;
  }
}
.period:nth-child(1) .timeline-marker {
  &:before {
    border-top: 0;
  }
}
/*----------------------------------------------
        MOD: TIMELINE SPLIT
    ----------------------------------------------*/

.timeline-split {
  @media (min-width: 768px) {
    .timeline {
      display: table;
    }
    .timeline-item {
      display: table-row;
      padding: 0;
    }
    .timeline-info,
    .timeline-marker,
    .timeline-content,
    .period .timeline-info {
      display: table-cell;
      vertical-align: top;
    }
    .timeline-marker {
      position: relative;
    }
    .timeline-content {
      padding-left: 30px;
    }
    .timeline-info {
      padding-right: 30px;
    }
    .period .timeline-title {
      position: relative;
      left: -45px;
    }
  }
}
</style>
