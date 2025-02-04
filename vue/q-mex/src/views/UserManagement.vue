<template>
  <div class="user-management">
    <el-card class="box-card">
      <div slot="header" class="header">
        <h2>用户管理</h2>
        <el-input
            v-model="searchQuery"
            placeholder="请输入用户名搜索"
            clearable
            class="search-input"
            size="small"
            @keyup.enter="handleSearch"
        >
          <template #suffix>
            <el-button
                icon="el-icon-search"
                size="mini"
                @click="handleSearch"
            ></el-button>
          </template>
        </el-input>
      </div>

      <el-table
          :data="filteredUsers"
          border
          stripe
          style="width: 100%;"
      >
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="employeeid" label="员工ID" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column prop="birthdate" label="出生日期" />
        <el-table-column prop="dept" label="部门" />
        <el-table-column prop="hiredate" label="入职日期" />
        <el-table-column prop="employeestatus" label="员工状态" />
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button
                type="primary"
                size="mini"
                @click="editUser(scope.row)"
            >编辑</el-button>
            <el-button
                type="danger"
                size="mini"
                @click="deleteUser(scope.row.id)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination">
        <el-pagination
            background
            layout="prev, pager, next"
            :total="filteredUsers.length"
            :page-size="pageSize"
            @current-change="handlePageChange"
        />
      </div>

      <el-button
          type="primary"
          size="small"
          @click="openAddUserDialog"
      >新增用户</el-button>

      <el-dialog
          title="新增用户"
          :visible.sync="dialogVisible"
          width="40%"
          @close="resetForm"
      >
        <el-form :model="newUser" ref="form" label-width="100px">
          <el-form-item label="员工ID">
            <el-input v-model="newUser.employeeid" placeholder="请输入员工ID" />
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="newUser.name" placeholder="请输入姓名" />
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="newUser.gender" placeholder="请选择性别">
              <el-option label="男" value="男" />
              <el-option label="女" value="女" />
            </el-select>
          </el-form-item>
          <el-form-item label="出生日期">
            <el-date-picker
                v-model="newUser.birthdate"
                type="date"
                placeholder="请选择出生日期"
            />
          </el-form-item>
          <el-form-item label="部门">
            <el-input v-model="newUser.dept" placeholder="请输入部门" />
          </el-form-item>
          <el-form-item label="入职日期">
            <el-date-picker
                v-model="newUser.hiredate"
                type="date"
                placeholder="请选择入职日期"
            />
          </el-form-item>
          <el-form-item label="员工状态">
            <el-input v-model="newUser.employeestatus" placeholder="请输入员工状态" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addUser">确 定</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserManagement",
  data() {
    return {
      searchQuery: "", // 搜索关键词
      users: [], // 用户数据
      pageSize: 10, // 每页显示的条目数
      currentPage: 1, // 当前页码
      dialogVisible: false, // 控制新增用户对话框显示
      newUser: { // 新用户的数据模型
        employeeid: "",
        name: "",
        gender: "男",  // 默认性别
        birthdate: "", // 可以用空字符串或 null
        dept: "",
        hiredate: "",
        employeestatus: "Y",
        password:"1234",
      },
    };
  },
  computed: {
    filteredUsers() {
      const filtered = this.users.filter((user) =>
          user.name.includes(this.searchQuery)
      );
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return filtered.slice(start, end);
    },
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get("http://localhost:8090/users");
        this.users = response.data.map(user => ({
          id: user.id,
          employeeid: user.employeeId,
          name: user.name,
          birthdate: user.birthDate || "暂无",
          employeestatus: user.employeeStatus,
          hiredate: user.hireDate,
          gender: user.gender || "暂无",
          dept: user.department || "未知",
        }));
      } catch (error) {
        this.$message.error("加载用户数据失败");
      }
    },
    async deleteUser(id) {
      try {
        const response = await axios.delete(`http://localhost:8090/delete/${id}`);
        if (response.status === 200) {
          this.users = this.users.filter((user) => user.id !== id);
          this.$message.success("用户删除成功");
        } else {
          this.$message.error("删除失败，请重试");
        }
      } catch (error) {
        this.$message.error("删除用户失败");
      }
    },
    async addUser() {
      try {
        const response = await axios.post("http://localhost:8090/add", {
          employeeId: this.newUser.employeeid,
          name: this.newUser.name,
          gender: this.newUser.gender,
          birthDate: this.newUser.birthdate,
          department: this.newUser.dept,
          hireDate: this.newUser.hiredate,
          employeeStatus: this.newUser.employeestatus,
          password:this.newUser.password,
        });
        if (response.status === 200) {
          this.$message.success("新增用户成功");
          this.dialogVisible = false;
          this.fetchUsers(); // 刷新用户列表
        } else {
          this.$message.error("新增用户失败，请重试");
        }
      } catch (error) {
        this.$message.error("新增用户失败");
      }
    },
    handleSearch() {
      this.currentPage = 1;
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    editUser(user) {
      this.$message.info(`编辑用户：${user.name}`);
    },
    openAddUserDialog() {
      this.dialogVisible = true;
    },
    resetForm() {
      this.newUser = {
        employeeid: "",
        name: "",
        gender: "男",
        birthdate: "",
        dept: "",
        hiredate: "",
        employeestatus: "Y",
        password: "1234"
      };
    },
  },
  mounted() {
    this.fetchUsers(); // 组件加载时调用接口
  },
};
</script>

<style scoped>
.user-management {
  padding: 20px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.search-input {
  max-width: 300px;
}
.pagination {
  margin-top: 20px;
  text-align: right;
}
</style>
