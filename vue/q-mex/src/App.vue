<template>
  <div id="app">
    <el-container >
      <!-- 侧边栏 -->
      <el-aside width="200px" v-if="!isLoginPage">
        <sidebar />
      </el-aside>

      <!-- 主体部分 -->
      <el-container>
        <!-- 顶部导航栏 -->
        <el-header v-if="!isLoginPage">
          <top-nav />
        </el-header>

        <!-- 主体内容区域 -->
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Sidebar from './components/Sidebar.vue'
import TopNav from './components/TopNav.vue'

export default {
  name: 'App',
  components: {
    Sidebar,
    TopNav
  },
  computed: {
    // 判断当前路由是否是登录页面
    isLoginPage() {
      return this.$route.path === '/login';
    }
  }
}
</script>

<style>
#app {
  height: 100%;
}

.el-container {
  height: 100%;
}

.el-main {
  padding: 20px;
}
</style>