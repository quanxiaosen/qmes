<template>
  <div>
    <!-- 搜索框 -->
    <el-input
        v-model="searchQuery"
        placeholder="搜索菜单"
        prefix-icon="el-icon-search"
        clearable
        style="margin-bottom: 10px; width: 100%;"
    ></el-input>

    <!-- 菜单 -->
    <el-menu
        default-active="/"
        class="el-menu-vertical-demo"
        :router="true"
    >
      <!-- 首页菜单项 -->
      <el-menu-item v-if="filterMenuItem('首页')" index="/">
        <i class="el-icon-house"></i>
        <span>首页</span>
      </el-menu-item>

      <!-- 用户管理子菜单 -->
      <el-submenu v-if="filterSubMenu('用户管理')" :index="2" :opened="openedSubMenus.includes('用户管理')">
        <template slot="title">
          <i class="el-icon-s-custom"></i>
          <span>用户管理</span>
        </template>
        <el-menu-item v-if="filterMenuItem('用户管理')" index="/user-management">用户管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('角色管理')" index="/role-management">角色管理</el-menu-item>
      </el-submenu>

      <!-- 基础信息子菜单 -->
      <el-submenu v-if="filterSubMenu('基础信息')" :index="3" :opened="openedSubMenus.includes('基础信息')">
        <template slot="title">
          <i class="el-icon-s-grid"></i>
          <span>基础信息</span>
        </template>
        <el-menu-item v-if="filterMenuItem('车间管理')" index="3-1">车间管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('部门管理')" index="3-2">部门管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('仓库管理')" index="3-3">仓库管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('客户管理')" index="3-4">客户管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('供应商管理')" index="3-5">供应商管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('班组管理')" index="3-6">班组管理</el-menu-item>
      </el-submenu>

      <!-- 物料管理子菜单 -->
      <el-submenu v-if="filterSubMenu('物料管理')" :index="4" :opened="openedSubMenus.includes('物料管理')">
        <template slot="title">
          <i class="el-icon-s-goods"></i>
          <span>物料管理</span>
        </template>
        <el-menu-item v-if="filterMenuItem('物料管理')" index="4-1">物料管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('丝网安全库存')" index="4-2">丝网安全库存</el-menu-item>
        <el-menu-item v-if="filterMenuItem('物料寿命管理')" index="4-3">物料寿命管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('物料管控信息')" index="4-4">物料管控信息</el-menu-item>
      </el-submenu>

      <!-- 工单管理子菜单 -->
      <el-submenu v-if="filterSubMenu('工单管理')" :index="5" :opened="openedSubMenus.includes('工单管理')">
        <template slot="title">
          <i class="el-icon-s-order"></i>
          <span>工单管理</span>
        </template>
        <el-menu-item v-if="filterMenuItem('工单管理')" index="5-1">工单管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('工单开工')" index="5-2">工单开工</el-menu-item>
        <el-menu-item v-if="filterMenuItem('工单打印')" index="5-3">工单打印</el-menu-item>
        <el-menu-item v-if="filterMenuItem('工单跟踪')" index="5-4">工单跟踪</el-menu-item>
      </el-submenu>

      <!-- 品质管理子菜单 -->
      <el-submenu v-if="filterSubMenu('品质管理')" :index="6" :opened="openedSubMenus.includes('品质管理')">
        <template slot="title">
          <i class="el-icon-s-finance"></i>
          <span>品质管理</span>
        </template>
        <el-menu-item v-if="filterMenuItem('异常反馈单')" index="6-1">异常反馈单</el-menu-item>
        <el-menu-item v-if="filterMenuItem('检验项目')" index="6-2">检验项目</el-menu-item>
        <el-menu-item v-if="filterMenuItem('缺陷类型')" index="6-3">缺陷类型</el-menu-item>
        <el-menu-item v-if="filterMenuItem('检验标准')" index="6-4">检验标准</el-menu-item>
      </el-submenu>

      <!-- 系统管理子菜单 -->
      <el-submenu v-if="filterSubMenu('系统管理')" :index="7" :opened="openedSubMenus.includes('系统管理')">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>系统管理</span>
        </template>
        <el-menu-item v-if="filterMenuItem('数据库管理')" index="7-1">数据库管理</el-menu-item>
        <el-menu-item v-if="filterMenuItem('菜单配置')" index="7-2">菜单配置</el-menu-item>
        <el-menu-item v-if="filterMenuItem('...')" index="7-3">...</el-menu-item>
        <el-menu-item v-if="filterMenuItem('...')" index="7-4">...</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'Sidebar',
  data() {
    return {
      searchQuery: "",  // 搜索框的绑定数据
      openedSubMenus: []  // 用于控制展开的子菜单
    };
  },
  methods: {
    // 过滤菜单项
    filterMenuItem(itemName) {
      return itemName.toLowerCase().includes(this.searchQuery.toLowerCase());
    },
    // 过滤子菜单
    filterSubMenu(menuName) {
      const items = this.getSubMenuItems(menuName);
      const hasMatch = items.some(item => this.filterMenuItem(item)); // 如果有任意子项符合搜索条件，显示子菜单
      if (hasMatch && !this.openedSubMenus.includes(menuName)) {
        // 如果有匹配项且子菜单未展开，则自动展开
        this.openedSubMenus.push(menuName);
      } else if (!hasMatch && this.openedSubMenus.includes(menuName)) {
        // 如果没有匹配项且子菜单已展开，则折叠
        this.openedSubMenus = this.openedSubMenus.filter(menu => menu !== menuName);
      }
      return hasMatch;
    },
    // 获取子菜单项名称
    getSubMenuItems(menuName) {
      const menuMap = {
        '用户管理': ['用户管理', '角色管理'],
        '基础信息': ['车间管理', '部门管理', '仓库管理', '客户管理', '供应商管理', '班组管理'],
        '物料管理': ['物料管理', '丝网安全库存', '物料寿命管理', '物料管控信息'],
        '工单管理': ['工单管理', '工单开工', '工单打印', '工单跟踪'],
        '品质管理': ['异常反馈单', '检验项目', '缺陷类型', '检验标准'],
        '系统管理': ['数据库管理', '菜单配置', '...', '...'],
      };
      return menuMap[menuName] || [];
    }
  }
}
</script>

<style scoped>
.el-menu-vertical-demo {
  width: 200px;
  height: 600px;
  border-right: 1px solid #ebeef5;
}

.el-menu {
  margin-top: 10px;
}
</style>
