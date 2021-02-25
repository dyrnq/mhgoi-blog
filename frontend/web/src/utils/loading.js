import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import store from '../store'

let needFullScreenLoadingRequestCount = 0
let needLoadingRequestCount = 0

export function showFullScreenLoading () {
  if (needFullScreenLoadingRequestCount === 0) {
    store.dispatch('setLoading', true)
  }
  needFullScreenLoadingRequestCount++
}

export function tryHideFullScreenLoading () {
  if (needFullScreenLoadingRequestCount <= 0) return
  needFullScreenLoadingRequestCount--
  if (needFullScreenLoadingRequestCount === 0) {
    store.dispatch('setLoading', false)
  }
}

export function showLoading () {
  if (needLoadingRequestCount === 0) {
    NProgress.start()
  }
  needLoadingRequestCount++
}

export function tryHideLoading () {
  if (needLoadingRequestCount <= 0) return
  needLoadingRequestCount--
  if (needLoadingRequestCount === 0) {
    NProgress.done()
  }
}
