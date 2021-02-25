import { login, logout, getInfo } from '@/api/user'
import { getUpToken } from '@/api/file'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: '',
    avatar: '',
    menus: [],
    roles: [],
    upToken: ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_MENUS: (state, menus) => {
    state.menus = menus
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_UP_TOKEN: (state, upToken) => {
    state.upToken = upToken
  }
}
const actions = {
  // user login
  login({ commit }, { username, password }) {
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password }).then(res => {
        if (res.flag) {
          let { token } = res.data
          commit('SET_TOKEN', token)
          setToken(token)
          resolve()
        } else {
          reject(res.msg)
        }
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(res => {
        if (!res.data) {
          return reject('Verification failed, please Login again.')
        }
        const { username, avatar, menus, roles } = res.data
        // menus must be a non-empty array
        if (!menus || menus.length <= 0) {
          reject('权限不足')
        }
        commit('SET_ROLES', roles)
        commit('SET_NAME', username)
        commit('SET_AVATAR', avatar)
        commit('SET_MENUS', menus)
        resolve(res.data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      removeToken() // must remove  token  first
      resetRouter()
      commit('RESET_STATE')
      resolve()
      // logout(state.token).then(() => {
      //   removeToken() // must remove  token  first
      //   resetRouter()
      //   commit('RESET_STATE')
      //   resolve()
      // }).catch(error => {
      //   reject(error)
      // })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  },

  getUpToken({ commit, state }) {
    return new Promise(resolve => {
      if (state.upToken) resolve(state.upToken)
      else {
        getUpToken().then(res => {
          if (res.flag) {
            commit('SET_UP_TOKEN', res.data)
            resolve(res.data)
          }
          resolve()
        })
      }
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

