import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueMeta from 'vue-meta'
import './assets/css/scrollbar.less'
import './assets/font/iconfont.css'
import './assets/css/iconfont.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css'
import 'animate.css'

import axios from './axios'
import VueAxios from 'vue-axios'
import { parseTime } from './utils'

Vue.use(VueMeta, {
  refreshOnceOnNavigation: true
})
Vue.use(ElementUI)
Vue.use(VueAxios, axios)

Vue.config.productionTip = false
Vue.filter('parseTime', (v) => parseTime(v))

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
