<template>
  <div class="login-container">
    <el-card class="login-card">
      <div slot="header" class="header">
        <h2>登录</h2>
      </div>
      <el-form :model="form" ref="form" label-width="100px" class="login-form">
        <el-form-item label="员工ID" :rules="[{ required: true, message: '请输入员工ID', trigger: 'blur' }]">
          <el-input v-model="form.employeeId" placeholder="请输入员工ID"></el-input>
        </el-form-item>

        <el-form-item label="密码" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" class="login-button">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
import { Message } from "element-ui";  // 引入 element-ui 中的 Message 组件

export default {
  name: "Login",
  data() {
    return {
      form: {
        employeeId: "", // 员工ID
        password: "", // 密码
      },
      loading: false, // 按钮加载状态
    };
  },
  methods: {
    async handleLogin() {
      // 校验表单
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          this.loading = true;
          try {
            // 向接口查询用户数据
            const response = await axios.get("http://localhost:8090/login");

            // 查询结果中是否有匹配的用户
            const user = response.data.find(
                (user) => user.employeeId === this.form.employeeId && user.password === this.form.password
            );

            if (user) {
              // 用户名密码匹配，设置登录状态
              localStorage.setItem("isLoggedIn", true); // 设置登录状态
              localStorage.setItem("username",user.name)
              // 使用 Vue Router 跳转到目标页面
              this.$router.push('/');  // 直接使用路径跳转
              window.location.reload();
            } else {
              // 用户名密码不匹配，提示错误
              Message.error("账户或密码错误");
            }
          } catch (error) {
            Message.error("登录请求失败，请稍后重试");
          } finally {
            this.loading = false;
          }
        }
      });
    },
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f4f6f9;
}

.login-card {
  width: 400px;
  padding: 20px;
}

.login-button {
  width: 100%;
}

.header h2 {
  text-align: center;
  margin-bottom: 20px;
}
</style>
