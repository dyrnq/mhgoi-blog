<template>
  <div class="app-container">
    <el-button type="primary" @click="viewOpenApi">查看所有开放API</el-button>
    <open ref="open"></open>
    <el-tabs v-model="activeName" style="margin-top:15px;" type="border-card">
      <el-tab-pane v-for="item in tabMapOptions" :key="item.key" :label="item.label" :name="item.key">
        <keep-alive>
          <tab-pane v-if="activeName==item.key" :apiModule="item" />
        </keep-alive>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getModules } from '@/api/backendApi'
import TabPane from './TabPane'
import Open from './open'

export default {
  components: {
    TabPane,
    Open
  },
  watch: {
    activeName(val) {
      this.$router.push(`${this.$route.path}?tab=${val}`)
    }
  },
  created() {
    // init the default selected tab
    const tab = this.$route.query.tab
    if (tab) {
      this.activeName = tab
    }
  },
  data() {
    return {
      tabMapOptions: [],
      activeName: 'CN',
    }
  },
  mounted() {
    this.getModule()
  },
  methods: {
    getModule() {
      getModules().then(res => {
        if (res.flag) {
          var list = []
          res.data.forEach(e => {
            list.push({
              key: e,
              label: e
            })
          })
          this.tabMapOptions = list
        }
      })
    },
    viewOpenApi() {
      this.$refs.open.init()
    }
  }
}
</script>