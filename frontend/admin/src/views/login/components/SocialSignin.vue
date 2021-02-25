<template>
  <div class="social-signup-container">
    <div class="sign-btn" @click="githubHandleClick">
      <svg class="icon-svg" aria-hidden="true">
        <use xlink:href="#el-ali-github"></use>
      </svg>
      <span>GitHub</span>
    </div>
    <div class="sign-btn" @click="giteeHandleClick">
      <svg class="icon-svg" aria-hidden="true">
        <use xlink:href="#el-ali-logo-gitee-g-red"></use>
      </svg>
      <span>Gitee</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SocialSignin',
  methods: {
    giteeHandleClick() {
      localStorage.setItem('privider', 'gitee')
      let client_id = 'e18f2b57e73810dd56d5aeb7f941847c8f90c74cde9608be6a83932a39b43946'
      let redirect_uri = 'http://admin.lesslesson.com/auth-redirect'
      let scope = 'user_info'
      let url = "https://gitee.com/oauth/authorize?client_id="
        + client_id + "&redirect_uri=" + redirect_uri + '&response_type=code&scope=' + scope
      window.location.href = url
    },
    githubHandleClick() {
      localStorage.setItem('privider', 'github')
      let client_id = '088d574da03e0c65c6ae'
      let redirect_uri = 'http://admin.lesslesson.com/auth-redirect'
      let state = Math.random().toString(36).substr(5)
      let url = "https://github.com/login/oauth/authorize?client_id="
        + client_id + "&redirect_uri=" + redirect_uri + "&state=" + state
      window.location.href = url
    },
  }
}
</script>

<style lang="scss" scoped>
.social-signup-container {
  display: flex;
  flex-direction: row;
  .sign-btn {
    margin-right: 30px;
    display: flex;
    flex-direction: row;
    align-items: center;
    cursor: pointer;
    .icon-svg {
      margin-right: 5px;
    }
  }
}
</style>
