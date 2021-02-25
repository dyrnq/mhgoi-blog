<template>
  <div>
    <!-- 表格 -->
    <el-table :data="data" border fit highlight-current-row @selection-change="selsChange" style="width: 100%">
      <el-table-column v-if="selectable" type="selection" width="55"></el-table-column>
      <!-- 索引列 -->
      <el-table-column type="index" label="#" width="50"></el-table-column>
      <!-- 遍历column数组中的对象进行渲染 -->
      <el-table-column v-for="(col,index) in column" :key="index" header-align="center" :align="col.align?col.align:'center'" :prop="col.prop" :label="col.label" :width="col.width">
        <template slot-scope="scope">
          <ex-slot v-if="col.render" :render="col.render" :row="scope.row" :index="scope.$index" :column="col" />
          <span v-else>{{ col.formatter?col.formatter(scope.row[col.prop]):scope.row[col.prop] }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="button" align="center" label="操作">
        <template slot-scope="scope">
          <!-- 遍历button数组并渲染 -->
          <template v-for="(btn,index) in button">
            <el-button style="margin:2px" v-if="btn.show?btn.show(scope.row):true" :key="index" :disabled="btn.disabled?btn.disabled(scope.row):null" size="mini" @click="btn.click(scope.row)" :type="btn.type">{{btn.labelChange?btn.labelChange(scope.row):btn.label}}</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>
    <el-button v-if="selectable" type="danger" size="small" @click="$emit('batchremove', sels)" :disabled="this.sels.length===0" style="margin-top:5px">批量删除</el-button>
  </div>
</template>

<script>
// 自定义内容的组件
var exSlot = {
  functional: true,
  props: {
    row: Object,
    render: Function,
    index: Number,
    column: {
      type: Object,
      default: null
    }
  },
  render: (h, data) => {
    const params = {
      row: data.props.row,
      index: data.props.index
    }
    if (data.props.column) params.column = data.props.column
    return data.props.render(h, params)
  }
}

export default {
  components: {
    exSlot
  },
  props: {
    // 列
    column: { required: true },
    // 操作按钮
    button: { default: null },
    //显示数据
    data: {
      type: Array,
      default: () => []
    },
    //是否可多选
    selectable: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      total: 0,
      page: 1,
      pageSize: 20,
      sels: [], // 列表选中列
    }
  },
  methods: {
    selsChange(sels) {
      this.sels = sels
    }
  }
}
</script>