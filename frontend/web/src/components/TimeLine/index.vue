<template>
  <div class="container">
    <section class="timeline">
      <ul>
        <li v-for="(item,index) in archives" :key="index">
          <div>
            <time>{{ item.createTime | parseTime }}</time>
            <div class="discovery">
              <h1>{{ item.title }}</h1>
              <p>
                {{ item.title }}
              </p>
            </div>
            <div class="scientist">
              <h1>{{ item.title }}</h1>
              <span>{{ item.title }}</span>
            </div>
          </div>
        </li>
      </ul>
    </section>
  </div>
</template>

<script>
export default {
  data () {
    return {
      items: [],
      archives: []
    }
  },
  methods: {
    getArchives () {
      this.axios.get('/article/archive/1/5').then(res => {
        if (res.flag) {
          this.archives = res.data.rows
          this.$nextTick(() => {
            this.init()
          })
        }
      })
    },
    init () {
      this.items = document.querySelectorAll('.timeline li')
      window.addEventListener('load', this.callbackFunc)
      window.addEventListener('scroll', this.callbackFunc)
    },
    isElementInViewport (el) {
      var rect = el.getBoundingClientRect()
      return (
        rect.top >= 0 &&
        rect.left >= 0 &&
        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
        rect.right <= (window.innerWidth || document.documentElement.clientWidth)
      )
    },
    callbackFunc () {
      for (var i = 0; i < this.items.length; i++) {
        if (this.isElementInViewport(this.items[i])) {
          if (!this.items[i].classList.contains('in-view')) {
            this.items[i].classList.add('in-view')
          }
        } else if (this.items[i].classList.contains('in-view')) {
          this.items[i].classList.remove('in-view')
        }
      }
    }
  }
}
</script>

<style lang="less">
*,
*::before,
*::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.container {
  font: normal 16px/1.5 "Titillium Web", sans-serif;
  background: linear-gradient(54deg, #9164ff, #8bfff4);
  color: #3c3f64;
  overflow-x: hidden;
  padding-bottom: 50px;
}

// STYLING
.timeline {
  ul {
    li {
      list-style-type: none;
      position: relative;
      width: 6px;
      margin: 0 auto;
      padding-top: 50px;
      background: #fff;
      &::after {
        content: "";
        position: absolute;
        left: 50%;
        bottom: 0;
        transform: translateX(-50%) rotate(45deg);
        width: 20px;
        height: 20px;
        z-index: 2;
        background: #eee;
      }
      div {
        position: relative;
        bottom: 0;
        width: 400px;
        padding: 20px;
        background: #fff;
        box-shadow: 4px 13px 30px 1px rgba(252, 56, 56, 0.2);
        border-radius: 5px;
        display: flex;
        align-items: center;
        time {
          position: absolute;
          background: #f5af19;
          width: 80px;
          height: 30px;
          top: -15px;
          border-radius: 5px;
          display: flex;
          justify-content: center;
          align-items: center;
          letter-spacing: 2px;
        }
        div {
          height: 100px;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          p {
            text-align: center;
          }
        }
        .discovery {
          margin-right: 10px;
        }
      }
      &:nth-of-type(odd) {
        > div {
          left: 45px;
        }
      }
      &:nth-of-type(even) {
        > div {
          left: -439px;
        }
      }
    }
  }
}

// ANIMATION
.timeline {
  ul {
    li {
      div {
        visibility: hidden;
        opacity: 0;
        transition: all 0.5s ease-in-out;
      }
      &:nth-of-type(odd) {
        div {
          transform: translate3d(100px, -10px, 0) rotate(10deg);
        }
      }
      &:nth-of-type(even) {
        div {
          transform: translate3d(-100px, -10px, 0) rotate(10deg);
        }
      }
      &.in-view {
        div {
          transform: none;
          visibility: visible;
          opacity: 1;
        }
      }
    }
  }
}

@media screen and (max-width: 900px) {
  .timeline {
    ul {
      li {
        div {
          width: 250px;
          flex-direction: column;
          div {
            width: 80%;
            margin: 10px;
          }
        }
        &:nth-of-type(even) {
          > div {
            left: -289px;
          }
        }
      }
    }
  }
}

@media screen and (max-width: 600px) {
  body {
    background: #8bfff4;
  }
  .timeline {
    ul {
      li {
        margin-left: 20px;
        div {
          width: calc(100vw - 91px);
        }
        &:nth-of-type(even) {
          > div {
            left: 45px;
          }
        }
      }
    }
  }
}
</style>
