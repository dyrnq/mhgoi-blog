<template>
  <div class="category-wrap">
    <ul class="category-list">
      <li v-for="(item,index) in categories" @click="cateClick(index)" :key="item.name" class="category animate__animated">
        <router-link :to="item.id ? `/category/${item.slug}` : '/'" exact active-class="active" class="category-item">
          <div class="category-left">
            <svg class="icon-svg" aria-hidden="true">
              <use :xlink:href='`#${item.icon}`'></use>
            </svg>
            <span>{{ item.name }}</span>
          </div>
          <div>
            <span class="counts">{{ item.counts }}</span>
          </div>
        </router-link>
      </li>
    </ul>
  </div>
</template>
<script>
import $ from 'jquery'

export default {
  props: {
    categories: {
      type: Array,
      default () {
        return []
      }
    }
  },
  methods: {
    cateClick (index) {
      this.$emit('resetPage')
      this.getTop()
      const effects = 'animate__shakeX'
      const effectsEnd = 'animationend oAnimationEnd mozAnimationEnd webkitAnimationEnd'
      $('.category').eq(index).addClass(effects).one(effectsEnd, function () {
        $(this).removeClass(effects)
      })
    },
    getTop () {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      if (scrollTop <= 750) {
        return
      }
      const timer = setInterval(() => {
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
        const speed = Math.ceil(scrollTop / 5)
        document.documentElement.scrollTop = scrollTop - speed
        if (scrollTop < 750) {
          clearInterval(timer)
        }
      }, 20)
    }
  }
}
</script>
<style scoped>
.category-item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
.category-left {
  display: flex;
  flex-direction: row;
  align-items: center;
}
.counts {
  margin-right: 20px;
  background-color: #f5f5f5;
  color: #4a4a4a;
  padding: 0.75em;
  font-size: 0.75rem;
  border-radius: 4px;
}

.category-list {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.category-list li a {
  padding-left: 20px;
  font-size: 15px;
  height: 54px;
  position: relative;
  display: flex;
  align-items: center;
  color: #4f6174;
  font-weight: 500;
  margin-bottom: 3px;
  border-radius: 5px 0 0 5px;
  transition: all 0.5s;
}

.category-list li a.active,
.category-list li a:hover {
  box-shadow: 0 0px 10px 0px rgba(0, 0, 0, 0.2);
}

.category-list li a.active svg,
.category-list li a.active span,
.category-list li a:hover svg,
.category-list li a:hover span {
  transform: translateX(-2px);
}

.category-list li a.active svg,
.category-list li a:hover svg {
  filter: grayscale(0);
}

.category-list li a svg {
  width: 60px;
  filter: grayscale(1);
  transition: all 0.3s;
}

@media (max-width: 840px) {
  .category-wrap {
    display: none;
  }
}
</style>
