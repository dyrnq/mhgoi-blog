<template>
  <div class="artalk-cards">
    <details class="admonition admonition-warning">
      <summary><div class="admonition-icon"><svg xmlns="http://www.w3.org/2000/svg" width="12" height="16" viewBox="0 0 12 16"><path fill-rule="evenodd" d="M5.05.31c.81 2.17.41 3.38-.52 4.31C3.55 5.67 1.98 6.45.9 7.98c-1.45 2.05-1.7 6.53 3.53 7.7-2.2-1.16-2.67-4.52-.3-6.61-.61 2.03.53 3.33 1.94 2.86 1.39-.47 2.3.53 2.27 1.67-.02.78-.31 1.44-1.13 1.81 3.42-.59 4.78-3.42 4.78-5.56 0-2.84-2.53-3.22-1.25-5.61-1.52.13-2.03 1.13-1.89 2.75.09 1.08-1.02 1.8-1.86 1.33-.67-.41-.66-1.19-.06-1.78C8.18 5.31 8.68 2.45 5.05.32L5.03.3l.02.01z"></path></svg></div>
        Comment on this blog
      </summary>
      <p>
        评论发表后 UA 信息、IP 地址会被记录，分别用于评论区展示和垃圾评论拦截。<br />
        无特殊原因评论不会被删除，你应该懂得如何发表适当的言论并对其负责。
      </p>
    </details>
    <div class="artalk-list"><div id="ArtalkComments"></div></div>
  </div>
</template>

<script>
import 'artalk/dist/Artalk.css'

export default {
  mounted () {
    const Artalk = require('artalk')
    var artalk = new Artalk({
      el: '#ArtalkComments',
      placeholder: '说点什么 (づ￣ 3￣)づ',
      defaultAvatar: 'mp',
      // maxNest: 2,
      pageKey: window.location.href,
      serverUrl: '/ArtalkServer',
      readMore: {
        pageSize: 15,
        autoLoad: true
      }
    })
  }
}
</script>
<style scoped lang="less">
.artalk-cards {
  --radius: 10px;
  --at-bg-main: #fff;
  --cb-admonition-icon-color: #ff4500;
  --cb-admonition-color: #ff4500;
  //--title-font-family: "Roboto Slab","Noto Serif SC",serif,-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,pingfang sc,source han sans sc,noto sans cjk sc,sarasa gothic sc,microsoft yahei,sans-serif,Apple Color Emoji,Segoe UI Emoji;

  background: var(--at-bg-main);
  border-radius: var(--radius);
  max-width: var(--content-width);
  margin: 20px auto 100px;
  box-shadow: 1px 1px 5px 0 rgba(0, 0, 0, 0.02),
    1px 1px 15px 0 rgba(0, 0, 0, 0.03);
  details {
    margin: 0 auto;
    text-align: center;
    border-top-left-radius: var(--radius);
    border-top-right-radius: var(--radius);
    font-weight: 600;
    outline: none;
    padding: 20px;
    summary {
      list-style: none;
      margin: 4px auto !important;
      color: var(--cb-admonition-icon-color) !important;
      margin: 2.75rem 0 1rem;
      //font-family: var(--title-font-family);
      line-height: 1.5;
      outline: none;
    }
    summary::-webkit-details-marker {
      display: none;
    }
    p {
      color: #2a2e2e;
      margin-bottom: 0;
      letter-spacing: 2px;
      font-weight: 300;
    }

  }
    .admonition .admonition-icon {
        display: inline-block;
        vertical-align: middle;
        margin-right: .5em;
        margin-top: .3em;
    }
    .admonition .admonition-icon svg {
        display: inline-block;
        width: 22px;
        height: 22px;
        stroke-width: 0;
        fill: var(--cb-admonition-color);
        stroke: var(--cb-admonition-color);
    }
    .admonition-warning {
        background: rgba(224,0,0,0.06666666666666667);
    }

  .artalk-list {
      margin: 20px 20px 20px;
  }
  @media screen and (max-width: 767.5px) {
    details > p {
      text-align: left;
    }
    #ArtalkComments {
      padding: 20px 0 0 0;
    }
  }
}
</style>
