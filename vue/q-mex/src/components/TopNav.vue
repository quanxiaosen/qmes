<template>
  <el-header>
    <div class="logo">q-mex管理系统</div>
    <el-dropdown trigger="click">
      <span class="el-dropdown-link">
        用户
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>修改密码</el-dropdown-item>
        <el-dropdown-item>退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-header>
</template>

<script>
export default {
  name: 'TopNav'
}
</script>

<style scoped>
.el-header {
  background-color: #409EFF;
  color: white;
  padding: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-size: 18px;
  margin-left: 20px;
  font-weight: bold;
}
</style>
