import Vue from 'vue'
import Vuex from 'vuex'
import axios from '../axios'
import request from '@/utils/request'
import { getToken, setToken, removeToken, getUserId } from '@/utils/auth'
import { getTimeInterval } from '@/utils/index'
import { login, getUserInfo } from '@/api/user'

Vue.use(Vuex)
// 略:后台获取系统运行时间
const runAt = '1596109327000'
let timer = null

export default new Vuex.Store({
  state: {
    loading: false,
    userId: '',
    username: '',
    avatar: '',
    isLogin: false,
    token: getToken(),
    loading: false,
    runTimeInterval: '',
    socials: '',
    websiteInfo: '',
    upToken: '',
    searchWords: ''
  },
  getters: {
    loading: state => state.loading,
    runTimeInterval: state => state.runTimeInterval,
    notice: state => state.websiteInfo ? state.websiteInfo.notice : ''
  },
  mutations: {
    SET_LOADING: (state, v) => {
      state.loading = v
    },
    SET_USER_NAME (state, username) {
      state.username = username
    },
    setLoginState (state, loginState) {
      state.isLogin = loginState
    },
    SET_TOKEN (state, token) {
      state.token = token
    },
    SET_LOADING: (state, v) => {
      state.loading = v
    },
    SET_SOCIALS: (state, v) => {
      state.socials = v
    },
    SET_SITE_INFO: (state, v) => {
      state.websiteInfo = v
    },
    GET_RUNTIME_INTERVAL: (state) => {
      if (!timer || !state.runTimeInterval) {
        clearInterval(timer)
        timer = setInterval(() => {
          state.runTimeInterval = getTimeInterval(runAt)
        }, 1000)
      }
    },
    SET_UP_TOKEN: (state, v) => {
      state.upToken = v
    },
    SET_AVATAR: (state, v) => {
      state.avatar = v
    },
    setSearchWords: (state, v) => {
      state.searchWords = v
    },
    SET_USER_ID: (state, v) => {
      state.userId = v
    }
  },
  actions: {
    setLoading: ({ commit }, v) => {
      commit('SET_LOADING', v)
    },
    login ({ commit }, loginForm) {
      return new Promise((resolve, reject) => {
        login(loginForm).then(res => {
          if (res.flag) {
            const data = res.data
            commit('SET_TOKEN', data.token)
            setToken(data.token)
          }
          resolve(res)
        })
          .catch(error => {
            reject(error)
          })
      })
    },
    logout ({ commit }) {
      return new Promise((resolve, reject) => {
        removeToken() // must remove  token  first
        commit('setLoginState', false)
        commit('SET_USER_NAME', '')
        commit('SET_AVATAR', '')
        commit('SET_TOKEN', '')
        commit('SET_USER_ID', '')
        resolve()
      })
    },
    getUserInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getUserInfo(getToken()).then(res => {
          if (!res.data) {
            return reject('Verification failed, please Login again.')
          }
          const { username, avatar, menus, roles } = res.data
          commit('SET_USER_NAME', username)
          commit('SET_AVATAR', avatar)
          commit('setLoginState', true)
          commit('SET_USER_ID', getUserId())
          resolve(res.data)
        }).catch(error => {
          reject(error)
        })
      })
    },
    initComputeTime: ({ commit }) => {
      commit('GET_RUNTIME_INTERVAL')
    },
    getSiteInfo: ({ commit, state }) => {
      return getData(commit, state, 'websiteInfo', 'SET_SITE_INFO', '/siteInfo')
    },
    getSocials: ({ commit, state }) => {
      return getData(commit, state, 'socials', 'SET_SOCIALS', '/social')
    },
    getUpToken ({ commit, state }) {
      return getData(commit, state, 'upToken', 'SET_UP_TOKEN', '/file/token')
    },
    refreshToken ({ commit }) {
      return new Promise(resolve => {
        const token = getToken()
        const config = {
          headers: { Freshman: token, 'Content-Type': 'application/json' }
        }
        request.post('/api/user/refresh', {}, config).then(res => {
          if (res.flag) {
            const token = res.data
            commit('SET_TOKEN', token)
            setToken(token)
            resolve(token)
          }
        })
      })
    }
  },
  modules: {
  }
})

function getData (commit, state, name, setName, path) {
  return new Promise(resolve => {
    if (state[name]) {
      resolve(state[name])
    } else {
      axios.get(path).then(res => {
        if (res.flag) {
          commit(setName, res.data)
          resolve(res.data)
        }
      })
    }
  })
}
