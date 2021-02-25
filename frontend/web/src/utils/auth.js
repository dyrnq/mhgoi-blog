import jwt_decode from 'jwt-decode'
import store from '../store'
import router from '../router'

const TokenKey = 'Authorization'

// 获取token
export function getToken () {
  return localStorage.getItem(TokenKey)
}

export async function validateToken () {
  const token = getToken()
  if (token == null) return null
  const decode = parseToken(token)
  if (decode) {
    if (isTokenExpired(token)) return await store.dispatch('refreshToken')
    return token
  } else {
    return null
  }
}

// 解析token(错误token返回null)
export function parseToken (token) {
  try {
    return jwt_decode(token)
  } catch (e) {
    return null
  }
}

// 判断token是否过期
export function isTokenExpired (token) {
  const decode = parseToken(token)
  const nowTime = Math.ceil(new Date().getTime() / 1000)
  if (decode && decode.exp > nowTime) return false
  return true
}

export function setToken (token) {
  return localStorage.setItem(TokenKey, token)
}

export function removeToken () {
  return localStorage.removeItem(TokenKey)
}

export function getRoles (token) {
  const decode = parseToken(token)
  return decode.roles
}

export function getUserId () {
  const token = getToken()
  const decode = parseToken(token)
  return decode.jti
}

// 检查是否登录，未登录跳转登录页，并记录当前路由
export function checkLogin () {
  // if (store.state.isLogin) {
  //   return true
  // }
  // router.push({
  //   path: '/login',
  //   query: {
  //     redirect: router.currentRoute.fullPath
  //   }
  // })
  return false
}

// 复制内容时加上版权信息
export function copyright () {
  // document.addEventListener('copy', function (e) {
  //     setClipboardText(e);
  // });
}

function setClipboardText (event) {
  // event.preventDefault();
  // var node = document.createElement('div');
  // node.appendChild(window.getSelection().getRangeAt(0).cloneContents());
  // var htmlData = '<div>'
  //     + node.innerHTML
  //     + '<br/><br/>著作权归作者所有。<br/>'
  //     + '商业转载请联系作者获得授权，非商业转载请注明出处。<br/>'
  //     + '作者：唔该博客<br/>'
  //     + '来源：https://mhgoi.net/<br/><br/>'
  //     + '</div>';
  // var textData = window.getSelection().getRangeAt(0)
  //     + '\n\n著作权归作者所有。\n'
  //     + '商业转载请联系作者获得授权，非商业转载请注明出处。\n'
  //     + '作者：唔该博客\n'
  //     + '来源：https://mhgoi.net/\n\n';
  // if (event.clipboardData) {
  //     event.clipboardData.setData("text/html", htmlData);
  //     event.clipboardData.setData("text/plain", textData);
  // }
  // else if (window.clipboardData) {
  //     return window.clipboardData.setData("text", textData);
  // }
}
