<template>
  <el-header>
    <div class="logo">q-mes管理系统</div>
    <el-dropdown trigger="click">
      <span class="el-dropdown-link">
        {{ userName }}  <!-- 显示用户的名字 -->
        <i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="changePassword">修改密码</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-header>
</template>

<script>
import { Message } from 'element-ui';  // 导入 Message 组件，显示退出提示
export default {
  name: 'TopNav',
  data() {
    return {
      userName: ''  // 用于存储用户名
    };
  },
  methods: {
    // 退出登录的逻辑
    logout() {
      // 清除登录相关的数据（例如 token 或者用户信息）
      localStorage.removeItem("isLoggedIn");
      localStorage.removeItem("username")
      // 提示用户已退出
      Message.success('退出登录成功！');

      // 跳转到登录页面
      this.$router.push('/login');  // 假设登录页路径是 '/login'
    },

    // 修改密码的逻辑
    changePassword() {
      this.$message.info('修改密码功能待开发');
    }
  },
  mounted() {
    // 获取保存在 localStorage 中的用户名
    const savedUserName = localStorage.getItem('username');
    if (savedUserName) {
      this.userName = savedUserName;
    } else {
      this.userName = '未知用户';
    }
  }
}
</script>

<style scoped>
.el-header {
  background-color: #409EFF;
  color: white;
  padding: 20px;
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
