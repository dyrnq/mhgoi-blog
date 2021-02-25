<template>
  <div class="app-container">
    <el-row>
      <el-col :span="6" class="tree">
        <el-tree :data="menus" highlight-current :default-expand-all="expandAll" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
      </el-col>
      <el-col class="menuForm" :span="14" :offset="1">
        <h3 style="text-align:center" v-if="title">正在{{title}}</h3>
        <el-form ref="form" hide-required-asterisk :disabled="disabled" :model="form" :rules="rules" label-width="80px">
          <el-col :span="16">
            <el-form-item label="菜单名:" prop="menuName">
              <el-input v-model="form.menuName" placeholder="菜单名"></el-input>
            </el-form-item>
            <el-form-item label="菜单描述:" prop="description">
              <el-input v-model="form.description" placeholder="菜单描述"></el-input>
            </el-form-item>
            <el-form-item label="路由名:" prop="routeName">
              <el-input v-model="form.routeName" placeholder="路由名"></el-input>
            </el-form-item>
            <el-form-item label="路由path:" prop="menuUrl">
              <el-input v-model="form.menuUrl" placeholder="路由path"></el-input>
            </el-form-item>
            <el-form-item label="菜单图标:" prop="menuIcon">
              <el-col :span="18">
                <el-input v-model="form.menuIcon" placeholder="菜单图标"></el-input>
              </el-col>
              <el-col :span="4" :offset="2" class="icon">
                <i :class="form.menuIcon"></i>
              </el-col>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单排序:" prop="menuSort">
              <el-slider v-model="form.menuSort" show-input :min="1">
              </el-slider>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="操作:">
              <el-button type="primary" round @click="addSameLevel">添加同级菜单</el-button>
              <el-button type="primary" round @click="addSubLevel">添加下级菜单</el-button>
              <el-button type="danger" round @click="deleteMenu">删除</el-button>
              <el-button type="primary" round @click="save">保存</el-button>
            </el-form-item>
          </el-col>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getMenus, insertMenu, updateMenu, deleteMenu } from '@/api/menu'
//import { getDictionaryByGroup } from '@/api/dictionary'

export default {
  data() {
    return {
      expandAll: true,
      title: "",
      disabled: true,
      form: {
        id: null,
        pid: null,
        menuName: "",
        routeName: "",
        description: "",
        menuUrl: "",
        menuIcon: "",
        menuSort: 1
      },
      rules: {
        menuName: [
          { required: true, message: '请输入菜单名', trigger: 'blur' },
        ]
      },
      choosedMenu: {},
      menus: [],
      icons: [],
      defaultProps: {
        children: 'children',
        label: 'menuName'
      }
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    loadData() {
      Promise.all([this.getMenus()]).then(res => {
        this.menus = res[0]
        //this.icons = res[1]
      })
    },
    //获取菜单
    getMenus() {
      return new Promise(resolve => {
        getMenus().then(res => resolve(res.data))
      })
    },
    //获取图标
    // getIcons() {
    //   return new Promise(resolve => {
    //     getDictionaryByGroup({ groupName: "icon" }).then(res => resolve(res.data))
    //   })
    // },
    //添加同级
    addSameLevel() {
      this.title = "添加同级菜单"
      this.resetForm()
      this.form.pid = this.choosedMenu.pid
    },
    //添加下级
    addSubLevel() {
      this.title = "添加下级菜单"
      this.resetForm()
      this.form.pid = this.choosedMenu.id
    },
    //选择节点，把值赋给表单
    handleNodeClick(menu) {
      Object.keys(this.form).forEach(key => {
        this.form[key] = menu[key]
      })
      this.title = "修改菜单"
      this.disabled = false
      this.choosedMenu = menu
    },
    showResult(res) {
      if (res.flag) {
        this.$message({
          message: res.msg,
          type: "success"
        })
        this.loadData()
        this.resetForm()
      }
      this.disabled = true
    },
    //保存
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) updateMenu(this.form).then(res => this.showResult(res))
          else insertMenu(this.form).then(res => this.showResult(res))
        } else {
          return false;
        }
      });
    },
    //删除
    deleteMenu() {
      this.$confirm('确定删除此菜单和下属菜单?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteMenu([this.choosedMenu.id]).then(res => this.showResult(res))
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    resetForm() {
      this.$refs['form'].resetFields()
      this.form.id = null
      this.form.pid = null
    }
  }
}
</script>

<style scoped>
.tree {
  height: 550px;
  overflow: auto;
  border: 1px solid #ddd;
}
.menuForm {
  padding: 10px 20px;
  border: 1px solid #ddd;
}
.icon i{
  font-size: 30px;
}
</style>