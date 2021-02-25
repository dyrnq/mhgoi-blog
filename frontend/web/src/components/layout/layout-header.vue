<template>
  <div id="layout-header" :class="{'fixed':fixed,'hidden':hidden}">
    <div class="site-logo">
      <router-link to="/">
        <img src="@/assets/site-logo.svg" alt="">
        <p class="site-name">{{ websiteInfo.name }}</p>
      </router-link>
    </div>
    <div class="site-menus">
      <div class="menu-item header-search">
        <header-search />
      </div>
      <div class="menu-item">
        <router-link to="/">首页</router-link>
      </div>
      <div class="menu-item">
        <router-link to="/friend">友链</router-link>
      </div>
      <div class="menu-item">
        <router-link to="/about">关于</router-link>
      </div>
      <div class="menu-item">
        <router-link to="/tags">标签</router-link>
      </div>
      <div class="menu-item">
        <router-link to="/archives">归档</router-link>
      </div>
      <!--
      <template v-if="!isLogin">
        <div class="menu-item">
          <router-link to="/login">登录</router-link>
        </div>
        <div class="menu-item">
          <router-link to="/register">注册</router-link>
        </div>
      </template>
      <template v-else>
        <div class="menu-item">
          <router-link to="/file">文件</router-link>
        </div>
        <div class="menu-item hasChild avatar">
          <el-avatar :size="avatarSize" :src="avatar"></el-avatar>
          <div class="childMenu">
            <router-link to="/user/info" class="sub-menu my-menu">个人中心</router-link>
            <div class="sub-menu my-menu" @click="logout">退出</div>
          </div>
        </div>
      </template>
      -->
    </div>
    <div class="H5-icons">
      <div class="menu-item" style="margin-right:20px">
        <header-search />
      </div>
      <div v-show="!fullWidth">
        <i class="el-icon-menu" @click="showMenu"></i>
      </div>
    </div>
    <el-drawer append-to-body title="我是标题" :visible.sync="drawer" :with-header="false">
      <div class="H5-menus">
        <router-link class="h5-my-menu" to="/">首页</router-link>
        <router-link class="h5-my-menu" to="/friend">友链</router-link>
        <router-link class="h5-my-menu" to="/about">关于</router-link>
        <router-link class="h5-my-menu" to="/tags">标签</router-link>
        <router-link class="h5-my-menu" to="/archives">归档</router-link>
        <!--
        <template v-if="!isLogin">
          <router-link class="h5-my-menu" to="/login">登录</router-link>
          <router-link class="h5-my-menu" to="/register">注册</router-link>
        </template>
        <template v-else>
          <router-link class="h5-my-menu" to="/file">文件</router-link>
          <div class="h5-my-menu" @click="logout">退出</div>
        </template>
        -->
      </div>
    </el-drawer>
  </div>
</template>

<script>
import HeaderSearch from '@/components/header-search'
import { mapState } from 'vuex'

export default {
  name: 'layout-header',
  components: { HeaderSearch },
  data () {
    return {
      lastScrollTop: 0,
      fixed: false,
      hidden: false,
      avatarSize: 'medium',
      fullWidth: false,
      drawer: false
    }
  },
  watch: {
    $route (to, from) {
      this.drawer = false
    }
  },
  computed: {
    ...mapState(['isLogin', 'username', 'avatar', 'websiteInfo', 'socials'])
  },
  mounted () {
    window.addEventListener('scroll', this.watchScroll)
  },
  beforeDestroy () {
    window.removeEventListener('scroll', this.watchScroll)
  },
  methods: {
    showMenu () {
      this.drawer = true
    },
    async logout () {
      await this.$store.dispatch('logout')
      if (this.$route.path != '/') {
        this.$router.push('/')
      }
    },
    watchScroll () {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      if (scrollTop === 0) {
        this.fixed = false
      } else if (scrollTop >= this.lastScrollTop) {
        this.fixed = false
        this.hidden = true
      } else {
        this.fixed = true
        this.hidden = false
      }
      this.lastScrollTop = scrollTop
    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/style.less";
.H5-icons {
  float: right;
  height: 100%;
  display: flex;
  align-items: center;
  flex-direction: row;
}
.h5-my-menu {
  padding: 10px 0;
  display: block;
  text-align: center;
}
.avatar {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.my-menu {
  cursor: pointer;
  color: #545454;
  &:hover {
    color: #ff6d6d;
  }
}

#layout-header {
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  position: fixed;
  top: 0;
  z-index: 9;
  width: 100%;
  height: 80px;
  padding: 0 80px;
  transition: 0.3s all ease;
  -webkit-transition: 0.3s all ease;
  -moz-transition: 0.3s all linear;
  -o-transition: 0.3s all ease;
  -ms-transition: 0.3s all ease;
  &.hidden {
    top: -100px;
  }
  &.fixed {
    background-color: #ffffff;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
}

.site-logo {
  text-align: center;
  float: left;

  img {
    width: 60px;
    height: 60px;
  }

  p.site-name {
    font-size: 15px;
    font-weight: bold;
    position: relative;
    top: -10px;
  }
}

.site-menus {
  height: 100%;
  float: right;
  display: flex;
  align-items: center;

  .menu-item {
    min-width: 60px;
    height: 50px;
    line-height: 50px;
    text-align: center;
    position: relative;
    a {
      padding: 12px 10px;
      color: #545454;
      font-weight: 500;
      font-size: 16px;
      &:hover {
        color: #ff6d6d;
      }
    }
    &:not(:last-child) {
      margin-right: 15px;
    }
    &.hasChild:hover .childMenu {
      opacity: 1;
      visibility: visible;
      transform: translateY(-5px);
    }
  }
  .childMenu {
    width: 130px;
    background-color: #fdfdfd;
    border-radius: 3px;
    border: 1px solid #ddd;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    position: absolute;
    top: 55px;
    z-index: 2;
    opacity: 0;
    visibility: hidden;
    transition: 0.7s all ease;
    -webkit-transition: 0.6s all ease;
    -moz-transition: 0.6s all linear;
    -o-transition: 0.6s all ease;
    -ms-transition: 0.6s all ease;
    &:before,
    &:after {
      content: "";
      position: absolute;
      width: 0;
      height: 0;
      border-left: 6px solid transparent;
      border-right: 6px solid transparent;
      border-bottom: 8px solid white;
      top: -8px;
      left: 20px;
    }
    &:before {
      top: -9px;
      border-bottom: 8px solid #ddd;
    }
    .sub-menu {
      height: 40px;
      line-height: 40px;
      position: relative;
      &:not(:last-child):after {
        /*position: absolute;*/
        content: "";
        width: 50%;
        height: 1px;
        color: #ff6d6d;
        bottom: 0;
        left: 25%;
        z-index: 99;
      }
    }
  }
}

@media (max-width: 960px) {
  #layout-header {
    padding: 0 20px;
  }
}

@media screen and (max-width: 768px) {
  #layout-header {
    padding: 0 10px;
  }
  .site-menus {
    display: none;
  }
}
@media screen and (min-width: 768px) {
  .H5-icons {
    display: none;
  }
}
</style>
