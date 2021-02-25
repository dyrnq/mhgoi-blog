<template>
  <el-card class="box-card">
    <div slot="header" class="header">
      <svg class="icon-svg" aria-hidden="true">
        <use xlink:href='#el-ali-gonggao'></use>
      </svg>
      <span>公告栏</span>
    </div>
    <p v-for="(item, index) in notices" :key="index" :class="{ 'notice': true, 'important-color':item.important}">
      {{index+1}}.{{item.content}}
    </p>
  </el-card>
</template>

<script>
import { getNotice } from '@/api/notice'

export default {
  data () {
    return {
      notices: []
    }
  },
  created () {
    this.getNotice()
  },
  methods: {
    getNotice () {
      getNotice().then(res => {
        if (res.flag) this.notices = res.data
      })
    }
  }
}
</script>

<style lang="less" scoped>
.header {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.notice {
  margin-bottom: 0.7rem;
  font-size: 15px;
  line-height: 1.5rem;
}
.important-color {
  color: #e6a23c;
}
</style>>
