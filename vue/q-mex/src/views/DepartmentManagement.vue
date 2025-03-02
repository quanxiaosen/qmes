<template>
  <div>
    <h2>部门管理</h2>

    <el-form :model="department" label-width="100px">
      <el-form-item label="部门名称">
        <el-input v-model="department.name" />
      </el-form-item>
      <el-form-item label="部门编码">
        <el-input v-model="department.code" />
      </el-form-item>
      <el-form-item label="上级部门ID">
        <el-input v-model="department.parentId" type="number" />
      </el-form-item>
      <el-form-item label="负责人ID">
        <el-input v-model="department.managerId" type="number" />
      </el-form-item>
      <el-form-item label="部门描述">
        <el-input v-model="department.description" type="textarea" />
      </el-form-item>
      <el-form-item label="状态">
        <el-switch v-model="department.status" />
      </el-form-item>
      <el-button type="primary" @click="addDepartment">添加部门</el-button>
    </el-form>

    <el-table :data="departments" border style="width: 100%">
      <el-table-column prop="id" label="部门ID" width="80" />
      <el-table-column prop="name" label="部门名称" width="150" />
      <el-table-column prop="code" label="部门编码" width="150" />
      <el-table-column prop="parentId" label="上级部门ID" width="120" />
      <el-table-column prop="managerId" label="负责人ID" width="120" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="status" label="状态" width="100">
        <template v-slot="scope">{{ scope.row.status ? '正常' : '停用' }}</template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      department: {
        name: "",
        code: "",
        parentId: null,
        managerId: null,
        description: "",
        status: true,
      },
      departments: [],
    };
  },
  methods: {
    async addDepartment() {
      console.log("提交的部门信息:", this.department);

      if (!this.department.name || !this.department.code) {
        this.$message.error("部门名称和编码不能为空！");
        return;
      }

      try {
        const response = await axios.post(
            "http://localhost:8090/department/add",
            this.department
        );
        this.departments.push(response.data);
        this.$message.success("部门添加成功");
        this.fetchDepartments();
      } catch (error) {
        this.$message.error("添加部门失败");
        console.error(error);
      }
    },
    async fetchDepartments() {
      try {
        const response = await axios.get("http://localhost:8090/department/list");
        this.departments = response.data;
      } catch (error) {
        console.error("获取部门列表失败", error);
      }
    },
  },
  mounted() {
    this.fetchDepartments();
  },
};
</script>

<style scoped>
h2 {
  margin-bottom: 20px;
}
</style>
