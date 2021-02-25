<template>
  <el-card class="box-card">
    <div slot="header" class="header">
      <span>标签</span>
    </div>
    <router-link v-for="tag in tags" :key="tag.id" :to="`/tags/${tag.name}`">
      <el-tag class="tags" :effect="tag.id == active?'dark':'plain'">{{ tag.name }}({{tag.counts}})</el-tag>
    </router-link>
  </el-card>
</template>

<script>
import { getTags } from '@/api/tag'

export default {
  props: {
    active: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      tags: []
    }
  },
  created () {
    this.getTags()
  },
  methods: {
    getTags () {
      getTags({ grouped: true }).then(res => {
        if (res.flag) this.tags = res.data
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
  justify-content: space-between;
}
.tags {
  margin: 2px;
}
</style>>
