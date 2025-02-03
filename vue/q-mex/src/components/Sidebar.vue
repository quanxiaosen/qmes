<template>
  <el-menu
    default-active="/user-management"
    class="el-menu-vertical-demo"
    :router="true"
  >
    <el-menu-item index="/">
      <i class="el-icon-house"></i>
      <span>首页</span>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-s-custom"></i>
        <span>用户管理</span>
      </template>
      <el-menu-item index="/user-management" >用户管理</el-menu-item>
      <el-menu-item index="/role-management">角色管理</el-menu-item>
    </el-submenu>
    <el-submenu index = "3">
      <template slot = "title">
        <i class="el-icon-s-grid"></i>
        <span>基础信息</span>
      </template>
      <el-menu-item index="3-1">车间管理</el-menu-item>
      <el-menu-item index="3-2">部门管理</el-menu-item>
      <el-menu-item index="3-3">仓库管理</el-menu-item>
      <el-menu-item index="3-4">客户管理</el-menu-item>
      <el-menu-item index="3-5">供应商管理</el-menu-item>
      <el-menu-item index="3-6">班组管理</el-menu-item>
    </el-submenu>

    <el-submenu index = "4">
      <template slot = "title">
        <i class="el-icon-s-goods"></i>
        <span>物料管理</span>
      </template>
      <el-menu-item index="4-1">物料管理</el-menu-item>
      <el-menu-item index="4-2">丝网安全库存</el-menu-item>
      <el-menu-item index="4-3">物料寿命管理</el-menu-item>
      <el-menu-item index="4-4">物料管控信息</el-menu-item>
    </el-submenu>

    <el-submenu index = "5">
      <template slot = "title">
        <i class="el-icon-s-order"></i>
        <span>工单管理</span>
      </template>
      <el-menu-item index="5-1">工单管理</el-menu-item>
      <el-menu-item index="5-2">工单开工</el-menu-item>
      <el-menu-item index="5-3">工单打印</el-menu-item>
      <el-menu-item index="5-4">工单跟踪</el-menu-item>
    </el-submenu>

    <el-submenu index = "6">
      <template slot = "title">
        <i class="el-icon-s-finance"></i>
        <span>品质管理</span>
      </template>
      <el-menu-item index="6-1">异常反馈单</el-menu-item>
      <el-menu-item index="6-2">检验项目</el-menu-item>
      <el-menu-item index="6-3">缺陷类型</el-menu-item>
      <el-menu-item index="6-4">检验标准</el-menu-item>
    </el-submenu>

    <el-submenu index = "7">
      <template slot = "title">
        <i class="el-icon-setting"></i>
        <span>系统管理</span>
      </template>
      <el-menu-item index="7-1">数据库管理</el-menu-item>
      <el-menu-item index="7-2">菜单配置</el-menu-item>
      <el-menu-item index="7-3">...</el-menu-item>
      <el-menu-item index="7-4">...</el-menu-item>
    </el-submenu>


  </el-menu>
</template>

<script>
export default {
  name: 'Sidebar'
}
</script>

<style scoped>
.el-menu-vertical-demo {
  width: 2000px;
  height: 600px;
  border-right: 1px solid #ebeef5;
}
</style>
