'use strict'
var webpack = require('webpack')

// If your port is set to 80,
// use administrator privileges to execute the command line.
// For example, Mac: sudo npm run
// You can change the port by the following methods:
const port = process.env.port || process.env.npm_config_port || 8888 // dev port

module.exports = {
  // 基本路径
  publicPath: '/',
  outputDir: 'dist', // 当运行 vue-cli-service build 时生成的生产环境构建文件的目录。
  lintOnSave: false, // eslint-loader 是否在保存的时候检查
  devServer: {
    open: false, // 配置自动启动浏览器
    port: port,
    // 关闭域名检查
    //disableHostCheck: true,
    allowedHosts: "all",
    // 错误或警告的全屏覆盖层
    client: {
      overlay: {
        // 警告
        warnings: false,
        // 错误
        errors: true
      }
    },
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8001',
        changeOrigin: true
        // pathRewrite: {
        //     '^/api': ''
        // }
      },
      '/pic': {
        target: 'http://127.0.0.1:8006',
        changeOrigin: true,
        onProxyReq: function (request) {
          request.setHeader('Cache-Control', 'no-cache')
        }
      },
      '/ArtalkServer': {
        target: 'http://127.0.0.1:23366',
        changeOrigin: true,
        pathRewrite: {
          '^/ArtalkServer': ''
        }
      }
    }
  },
  chainWebpack: config => {
    config.resolve.alias
      .set('assets', 'src/assets')

    config.plugin('html')
      .tap(args => {
        args[0].title = '唔该博客'
        return args
      })
  },
  css: {
    loaderOptions: {
      less: {
        lessOptions: {
          globalVars: {
            primary: '#ee5e5e'
          }
        }
      }
    }
  }

}
