<template>
  <div class="app-container">
    <el-button type="primary" icon="el-icon-plus" @click="addFriend">新增友站</el-button>
    <div class="friend-list animate">
      <el-row :gutter="30">
        <el-col :span="6" v-for="item in friendList" :key="item.id">
          <div class="friend-item">
            <a target="_blank" :href="item.path" class="link">
              <div class="item-container">
                <img :src="item.avatar">
                <div class="site-info">
                  <div class="site-name">{{item.siteName}}</div>
                  <div class="site-detail">{{item.description}}</div>
                </div>
              </div>
            </a>
            <div class="operation">
              <i class="el-ali-xiugai" style="color:#409EFF;cursor:pointer;" @click="editFriend(item)"></i>
              <i class="el-icon-delete" style="color:#F56C6C;cursor:pointer;" @click="deleteFriend(item)"></i>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" width="40%">
      <el-form :rules="rules" label-width="80px" :model="friend" :hide-required-asterisk="true">
        <el-form-item label="站名" prop="siteName">
          <el-input v-model="friend.siteName"></el-input>
        </el-form-item>
        <el-form-item label="网站URL" prop="path">
          <el-input v-model="friend.path"></el-input>
        </el-form-item>
        <el-form-item label="头像URL" prop="avatar">
          <el-input v-model="friend.avatar"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="description">
          <el-input type="textarea" :rows="4" v-model="friend.description"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getSiteFriend, updateSiteFriend, insertSiteFriend, deleteSiteFriend } from '@/api/site-friend'

export default {
  data() {
    return {
      title: '',
      visible: false,
      friendList: [],
      friend: {},
      rules: {
        siteName: [
          { required: true, message: '请输入站名', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '请输入网站RUL', trigger: 'blur' }
        ],
        avatar: [
          { required: true, message: '请输入头像URL', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入简述', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.fetchFriend()
  },
  methods: {
    fetchFriend() {
      getSiteFriend().then(res => {
        this.friendList = res.data
      })
    },
    save() {
      if (this.friend.id) {
        updateSiteFriend(this.friend).then(res => {
          if (res.flag) this.$notify.success('修改成功');
          this.fetchFriend()
        })
      } else {
        insertSiteFriend(this.friend).then(res => {
          if (res.flag) this.$notify.success('新增成功');
          this.fetchFriend()
        })
      }
      this.visible = false
    },
    editFriend(item) {
      this.title = '修改友站信息'
      this.friend = item
      this.visible = true
    },
    addFriend() {
      this.title = '新增友站'
      this.friend = {}
      this.visible = true
    },
    deleteFriend(item) {
      this.$confirm(`确认删除"${item.siteName}"?`, '提示', {
        type: 'warning'
      }).then(() => {
        deleteSiteFriend([item.id]).then(res => {
          if (res.flag) {
            this.$notify.success('删除成功')
            this.fetchFriend()
          }
        })
      }).catch(() => { })
    }
  }
}
</script>

<style scoped>
.operation {
  width: 10%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

.friend-list {
  margin-top: 20px;
  width: 100%;
}
.friend-list .friend-item {
  margin-bottom: 20px;
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 100px;
  padding: 10px;
  border: 1px solid #ececec;
  border-radius: 3px;
}
.friend-list .friend-item:hover {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.friend-list .friend-item:hover img {
  transform: rotate(360deg);
}
.link {
  width: 90%;
}
.item-container {
  display: flex;
  flex-direction: row;
}
.friend-list .friend-item .site-info {
  overflow: hidden;
  width: 100%;
}
.friend-list .friend-item .site-name,
.friend-list .friend-item .site-detail {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.5rem;
  color: #1f2d3d;
}
.friend-list .friend-item .site-name {
  -webkit-line-clamp: 1;
  border-bottom: 1px dotted #ececec;
  font-weight: bold;
  font-size: 20px;
}
.friend-list .friend-item .site-detail {
  padding-top: 10px;
  font-size: 13px;
  line-height: 1.1rem;
}
.friend-list .friend-item img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 50%;
  padding: 2px;
  border: 1px solid #dadada;
  position: relative;
  transition: all 0.4s linear;
  margin-right: 10px;
}
</style>