<template>




  <div class="app-container">


   <el-tabs :tab-position="tabPosition" @tab-click="tabClick" v-model="tabActiveName">
    <el-tab-pane label="站点信息" name="default">


          <el-card class="box-card">
                <div slot="header" class="clearfix">
                  <span>站点信息</span>
                  <el-button style="float: right; padding: 3px 0" type="text" @click="handleClick">{{ editable?'保存':'编辑' }}</el-button>
                </div>
                <div>
                  <div style="width:50%;margin:0 auto">
                    <div class="avatar">
                      <el-avatar :size="size" :src="siteInfo.avatar" v-if="!editable"></el-avatar>
                    </div>
                    <template v-if="editable">
                      <p><span>头像：</span> <el-input v-model="siteInfo.avatar"></el-input></p>
                      <p><span>站名：</span> <el-input v-model="siteInfo.name"></el-input></p>
                      <p><span>域名：</span> <el-input v-model="siteInfo.domain"></el-input></p>
                      <p><span>标语：</span> <el-input v-model="siteInfo.slogan"></el-input></p>
                      <p><span>横幅：</span> <el-input v-model="siteInfo.banner"></el-input></p>
                    </template>
                    <template v-else>
                      <p>站名：{{ siteInfo.name }}</p>
                      <p>域名：
                        <el-link :href="siteInfo.domain" target="_blank" :underline="false">{{ siteInfo.domain }}</el-link>
                      </p>
                      <p>标语：
                        <el-tag>{{ siteInfo.slogan }}</el-tag>
                      </p>
                      <p>横幅：{{ siteInfo.banner }}
                      </p>
                    </template>
                    <p class="description">
                      <span>简述：</span>
                      <el-input type="textarea" :rows="4" :readonly="!editable" v-model="siteInfo.description"></el-input>
                    </p>
                    <p class="notice">
                      <span>通知：</span>
                      <el-input type="textarea" :rows="4" :readonly="!editable" v-model="siteInfo.notice"></el-input>
                    </p>
                    <p class="notice">
                      <span>底部：</span>
                      <el-input type="textarea" :rows="2" :readonly="!editable" v-model="siteInfo.footer"></el-input>
                    </p>
                    <p class="notice">
                      <span>备案：</span>
                      <el-input type="textarea" :rows="2" :readonly="!editable" v-model="siteInfo.beian"></el-input>
                    </p>
                    <p class="notice">
                      <span>扩展：</span>
                      <el-input type="textarea" :rows="2" :readonly="!editable" v-model="siteInfo.extend"></el-input>
                    </p>
                  </div>
                </div>
              </el-card>


    </el-tab-pane>

    <el-tab-pane label="系统信息" name="runtime">
          <json-viewer 
          :value="runtimeInfo"
          :expanded=true
          :expand-depth=5
          copyable
          sort
          ></json-viewer>      
    </el-tab-pane>


    <el-tab-pane label="JVM信息" name="thread">
      <div v-html="thread"></div>
    </el-tab-pane>

  </el-tabs>


  </div>
</template>

<script>
//import { defineComponent, reactive, watch } from 'vue';
import { getSiteInfo, updateSiteInfo, getRuntime, getThread } from '@/api/site-info'
import JsonViewer from 'vue-json-viewer'


export default {
  components: {
    JsonViewer
  },
  data() {
    return {
      tabPosition: 'left',
      tabActiveName: 'default',
      editable: false,
      size: 100,
      siteInfo: {
        name: '',
        domain: '',
        avatar: '',
        slogan: '',
        notice: '',
        description: '',
        footer: '',
        beian: '',
        extend: '',
      },
      runtimeInfo: '',
      thread: ''
    }
  },
  created() {
    this.getSiteInfo()
    //this.getRuntime()
  },
  methods: {

  tabClick(tab, event) {
        console.log(tab, event);
        if(tab.name == 'default'){
        	this.getSiteInfo()
        }else if(tab.name == 'runtime'){
        	this.getRuntime();
        }else if(tab.name == 'thread'){
        	this.getThread();
        }
      },
    getThread() {
      getThread().then(res => {
        if (res.flag) this.thread = res.data.jinfo
      })
    }, 
    getRuntime() {
      getRuntime().then(res => {
        if (res.flag) this.runtimeInfo = res.data
      })
    },
    getSiteInfo() {
      getSiteInfo().then(res => {
        if (res.flag) this.siteInfo = res.data
      })
    },
    handleClick() {
      this.editable = !this.editable
      if(!this.editable) {
        updateSiteInfo(this.siteInfo).then(res => {
          if (res.flag) this.$notify.success('修改成功');
          this.getSiteInfo()
        }) 
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.el-textarea,
.el-input {
  width: 90%;
}
.el-input {
  margin: 5px 0;
}
.description,
.notice {
  display: flex;
}
.avatar {
  display: flex;
  justify-content: center;
}
</style>>
