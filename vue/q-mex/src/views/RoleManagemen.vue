<template>
  <div class="role-management">
    {{ item }}
    <el-card class="box-card">
      <div slot="header" class="header">
        <h2>角色管理</h2>
        <el-input
          v-model="searchQuery"
          placeholder="请输入角色名称搜索"
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
        <el-button
          type="primary"
          size="small"
          @click="openAddRoleDialog"
        >
          添加角色
        </el-button>
      </div>
      <el-table
        :data="filteredRoles"
        border
        stripe
        style="width: 100%;"
      >
        <el-table-column prop="id" label="ID" width="50" />
        <el-table-column prop="name" label="角色名称" />
        <el-table-column prop="description" label="描述" />
        <el-table-column
          label="操作"
          width="200"
        >
          <template #default="scope">
            <el-button
              type="primary"
              size="mini"
              @click="editRole(scope.row)"
              >编辑</el-button
            >
            <el-button
              type="danger"
              size="mini"
              @click="deleteRole(scope.row.id)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="filteredRoles.length"
          :page-size="pageSize"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>

    <!-- 添加/编辑角色对话框 -->
    <el-dialog
      title="角色信息"
      :visible.sync="roleDialogVisible"
      width="500px"
    >
      <el-form :model="roleForm" :rules="roleFormRules" ref="roleForm">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="roleForm.name" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="roleForm.description" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeRoleDialog">取消</el-button>
        <el-button type="primary" @click="saveRole">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>


<script>
import axios from 'axios';

export default {
  name: "RoleManagement",
  data() {
    return {
      searchQuery: "", // 搜索关键词
      roles: [], // 角色数据，从接口获取
      pageSize: 5, // 每页显示的条目数
      currentPage: 1, // 当前页码
      roleDialogVisible: false, // 添加/编辑对话框显示状态
      roleForm: {
        id: null,
        name: "",
        description: "",
      },
      roleFormRules: {
        name: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
        description: [
          { required: true, message: "请输入角色描述", trigger: "blur" },
        ],
      },
    };
  },
  computed: {
    filteredRoles() {
      // 搜索过滤 + 分页处理
      const filtered = this.roles.filter((role) =>
          role.name.includes(this.searchQuery)
      );
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return filtered.slice(start, end);
    },
  },
  mounted() {
    // 页面加载时请求数据
    this.fetchRoles();
  },
  methods: {
    // 获取角色数据
    fetchRoles() {
      axios
          .get("http://localhost:8090/users")
          .then((response) => {
            if (response.data) {
              // 假设返回的数据格式是一个数组，其中包含角色的id和name
              this.roles = response.data.map((item) => ({
                id: item.id,
                name: item.name,
                description: "暂无描述", // 默认没有描述，可以根据需求更新
              }));
            }
          })
          .catch((error) => {
            console.error("获取角色数据失败:", error);
            this.$message.error("获取角色数据失败");
          });
    },
    handleSearch() {
      this.currentPage = 1; // 重置到第一页
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    openAddRoleDialog() {
      this.roleForm = { id: null, name: "", description: "" }; // 重置表单
      this.roleDialogVisible = true;
    },
    editRole(role) {
      this.roleForm = { ...role }; // 编辑时填充表单数据
      this.roleDialogVisible = true;
    },
    saveRole() {
      this.$refs.roleForm.validate((valid) => {
        if (valid) {
          if (this.roleForm.id) {
            // 更新角色
            const index = this.roles.findIndex(
                (role) => role.id === this.roleForm.id
            );
            if (index !== -1) {
              this.roles[index] = { ...this.roleForm };
            }
          } else {
            // 添加角色
            this.roleForm.id = this.roles.length + 1;
            this.roles.push({ ...this.roleForm });
          }
          this.roleDialogVisible = false;
          this.$message.success("保存成功");
        }
      });
    },
    deleteRole(id) {
      this.roles = this.roles.filter((role) => role.id !== id);
      this.$message.success("删除成功");

    },
    closeRoleDialog() {
      this.roleDialogVisible = false;
    },
  },
};
</script>


<!--<script>-->
<!--import axios from 'axios';-->
<!--export default {-->
<!--  name: "RoleManagement",-->
<!--  data() {-->
<!--    return {-->
<!--      searchQuery: "", // 搜索关键词-->
<!--      roles: [-->
<!--        // 模拟角色数据-->
<!--        { id: 1, name: "管理员", description: "拥有所有权限" },-->
<!--        { id: 2, name: "用户", description: "普通用户权限" },-->
<!--        { id: 3, name: "审核员", description: "负责审核任务" },-->
<!--      ],-->
<!--      pageSize: 5, // 每页显示的条目数-->
<!--      currentPage: 1, // 当前页码-->
<!--      roleDialogVisible: false, // 添加/编辑对话框显示状态-->
<!--      roleForm: {-->
<!--        id: null,-->
<!--        name: "",-->
<!--        description: "",-->
<!--      },-->
<!--      roleFormRules: {-->
<!--        name: [{ required: true, message: "请输入角色名称", trigger: "blur" }],-->
<!--        description: [-->
<!--          { required: true, message: "请输入角色描述", trigger: "blur" },-->
<!--        ],-->
<!--      },-->
<!--    };-->
<!--  },-->
<!--  computed: {-->
<!--    filteredRoles() {-->
<!--      // 搜索过滤 + 分页处理-->
<!--      const filtered = this.roles.filter((role) =>-->
<!--        role.name.includes(this.searchQuery)-->
<!--      );-->
<!--      const start = (this.currentPage - 1) * this.pageSize;-->
<!--      const end = start + this.pageSize;-->
<!--      return filtered.slice(start, end);-->
<!--    },-->
<!--  },-->
<!--  methods: {-->
<!--    handleSearch() {-->
<!--      this.currentPage = 1; // 重置到第一页-->
<!--    },-->
<!--    handlePageChange(page) {-->
<!--      this.currentPage = page;-->
<!--    },-->
<!--    openAddRoleDialog() {-->
<!--      this.roleForm = { id: null, name: "", description: "" }; // 重置表单-->
<!--      this.roleDialogVisible = true;-->
<!--    },-->
<!--    editRole(role) {-->
<!--      this.roleForm = { ...role }; // 编辑时填充表单数据-->
<!--      this.roleDialogVisible = true;-->
<!--    },-->
<!--    saveRole() {-->
<!--      this.$refs.roleForm.validate((valid) => {-->
<!--        if (valid) {-->
<!--          if (this.roleForm.id) {-->
<!--            // 更新角色-->
<!--            const index = this.roles.findIndex(-->
<!--              (role) => role.id === this.roleForm.id-->
<!--            );-->
<!--            if (index !== -1) {-->
<!--              this.roles[index] = { ...this.roleForm };-->
<!--            }-->
<!--          } else {-->
<!--            // 添加角色-->
<!--            this.roleForm.id = this.roles.length + 1;-->
<!--            this.roles.push({ ...this.roleForm });-->
<!--          }-->
<!--          this.roleDialogVisible = false;-->
<!--          this.$message.success("保存成功");-->
<!--        }-->
<!--      });-->
<!--    },-->
<!--    deleteRole(id) {-->
<!--      this.roles = this.roles.filter((role) => role.id !== id);-->
<!--      this.$message.success("删除成功");-->
<!--    },-->
<!--    closeRoleDialog() {-->
<!--      this.roleDialogVisible = false;-->
<!--    },-->
<!--  },-->
<!--};-->
<!--</script>-->


<!--<script>-->
<!--import axios from 'axios';-->

<!--export default {-->
<!--  name: "RoleManagement",-->
<!--  data() {-->
<!--    return {-->
<!--      searchQuery: "", // 搜索关键词-->
<!--      roles: [], // 存储请求的角色数据-->
<!--      pageSize: 5, // 每页显示的条目数-->
<!--      currentPage: 1, // 当前页码-->
<!--    };-->
<!--  },-->
<!--  created() {-->
<!--    this.fetchRoles(); // 页面加载时请求角色数据-->
<!--  },-->
<!--  methods: {-->
<!--    // 请求角色数据-->
<!--    fetchRoles() {-->
<!--      axios-->
<!--          .get('http://localhost:8090/users') // 替换为实际的接口地址-->
<!--          .then((response) => {-->
<!--            this.roles = response.data; // 假设返回的数据是一个数组-->
<!--            this.$message.success("获取角色数据成功"); // 获取数据成功的提示-->
<!--            console.log("获取到的数据:", this.roles); // 打印获取到的数据-->
<!--          })-->
<!--          .catch((error) => {-->
<!--            console.error("获取角色数据失败:", error);-->
<!--            this.$message.error("获取角色数据失败");-->
<!--          });-->
<!--    },-->
<!--    handleSearch() {-->
<!--      // 搜索功能-->
<!--      this.currentPage = 1; // 重置到第一页-->
<!--    },-->
<!--    handlePageChange(page) {-->
<!--      this.currentPage = page;-->
<!--    },-->
<!--    editRole(role) {-->
<!--      this.$message.info(`编辑角色：${role.name}`);-->
<!--    },-->
<!--    deleteRole(id) {-->
<!--      // 删除角色-->
<!--      this.roles = this.roles.filter((role) => role.id !== id);-->
<!--      this.$message.success("删除成功");-->
<!--    },-->
<!--  },-->
<!--};-->
<!--</script>-->



<!--<script>-->
<!--import axios from 'axios';-->

<!--export default {-->
<!--  data() {-->
<!--    return {-->
<!--      roles: []  // 存放从后端获取的角色数据-->
<!--    };-->
<!--  },-->
<!--  created() {-->
<!--    this.fetchRoleData();-->
<!--  },-->
<!--  methods: {-->
<!--    fetchRoleData() {-->
<!--      axios.get('http://localhost:8090/users')  // 请求后端接口-->
<!--          .then(response => {-->
<!--            this.roles = response.data;  // 设置返回的数据到 roles-->
<!--          })-->
<!--          .catch(error => {-->
<!--            console.error('获取角色数据失败:', error.response ? error.response.data : error.message);-->
<!--          });-->
<!--    }-->
<!--  }-->
<!--};-->
<!--</script>-->

<style scoped>
.role-management {
  padding: 20px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.search-input {
  max-width: 300px;
  margin-right: 10px;
}
.pagination {
  margin-top: 20px;
  text-align: right;
}
</style>
