<template>
  <div id="layout-footer">
    <div class="footer-main">
      <div class="footer-item" v-if="socials.length">
        <div v-for="item in socials" :key="item.id"><a target="_blank" class="out-link" :href="item.href"><i :class="item.icon"></i>{{item.title}}</a></div>
      </div>
      <div class="footer-item">
        <div><a target="_blank" class="out-link" href="https://github.com/explore">Github Explore</a></div>
        <div><a target="_blank" class="out-link" href="https://segmentfault.com/weekly?utm_source=sf-footer">每周精选</a></div>

      </div>
      <div class="footer-item">
        <!--<div>本站已苟活 {{runTimeInterval}}</div>-->
        <div v-html="websiteInfo.beian"></div>
      </div>
    </div>
    <div class="copyright" v-html="websiteInfo.footer"></div>
  </div>
</template>

<script>
import sectionTitle from '@/components/section-title'
import { mapState } from 'vuex'
export default {
  name: 'layout-footer',
  // data() {
  //   return {
  //     websiteInfo: {},
  //     socials: []
  //   }
  // },
  components: {
    sectionTitle
  },
  computed: {
    ...mapState(['isLogin', 'username', 'avatar', 'websiteInfo', 'socials']),
    runTimeInterval () {
      return this.$store.state.runTimeInterval
    }
  },
  methods: {
    // getSocial() {
    //   this.$store.dispatch('getSocials').then(data => {
    //     this.socials = data
    //   })
    // },
    // getWebSiteInfo() {
    //   this.$store.dispatch('getSiteInfo').then(data => {
    //     this.websiteInfo = data
    //   })
    // }

  },
  created () {
    // this.getWebSiteInfo();
    // this.getSocial();
    this.$store.dispatch('initComputeTime')
  }
}
</script>

<style scoped lang="less">
@import '../../assets/css/style.less';

#layout-footer {
  background-color: #fff;
  padding: 2%;
  border-top: 1px solid #f7f7f7;
  font-size: 13px;
  color: #9c9c9c;
  a.out-link:hover {
    color: #ff6d6d;
  }
  .footer-main {
    max-width: 900px;
    margin: 0 auto 20px auto;
    display: flex;
    justify-content: space-around;
    align-items: flex-start;
    .footer-item {
      flex: 1;
      & > div:not(:last-child) {
        margin-bottom: 10px;
      }
      i {
        margin-right: 10px;
      }
    }
  }
  .copyright {
    text-align: center;
  }
}
/*****/
@media (max-width: 900px) {
  #layout-footer {
    .footer-main .footer-item:nth-child(3) {
      flex: 2;
    }
  }
}
@media (max-width: 600px) {
  #layout-footer {
    .footer-main {
      display: block;
      .footer-item {
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-wrap: wrap;
        & > div {
          margin-bottom: 10px;
        }
      }
    }
  }
}
/*****/
</style>
