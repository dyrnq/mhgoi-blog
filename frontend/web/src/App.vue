<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"></router-view>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"></router-view>
  </div>
</template>

<script>

export default {
  name: 'app'
}
</script>

<style lang="less">
body {
  margin: 0 !important;
}
#app {
  font-size: 17px;
  color: #6f6f6f;
}
</style>
