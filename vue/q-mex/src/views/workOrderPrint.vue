<template>
  <div>
    <el-table :data="workOrders" border>
      <el-table-column prop="id" label="工单编号" width="120"></el-table-column>
      <el-table-column prop="name" label="工单名称"></el-table-column>
      <el-table-column prop="batch" label="批号" width="180"></el-table-column>
      <el-table-column prop="date" label="创建时间" width="180"></el-table-column>
      <el-table-column label="操作" width="180">
        <template slot-scope="{ row }">
          <el-button type="primary" @click="printWorkOrder(row)">打印</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div id="printArea" style="display: none;">
      <h2 style="text-align: center">工单详情</h2>
      <table border="1" width="100%" style="border-collapse: collapse; text-align: left;">
        <tr>
          <th style="padding: 8px; border: 1px solid black;">工单编号</th>
          <td style="padding: 8px; border: 1px solid black;">{{ selectedOrder.id }}</td>
        </tr>
        <tr>
          <th style="padding: 8px; border: 1px solid black;">工单名称</th>
          <td style="padding: 8px; border: 1px solid black;">{{ selectedOrder.name }}</td>
        </tr>
        <tr>
          <th style="padding: 8px; border: 1px solid black;">批号</th>
          <td style="padding: 8px; border: 1px solid black;">{{ selectedOrder.batch }}</td>
        </tr>
        <tr>
          <th style="padding: 8px; border: 1px solid black;">创建时间</th>
          <td style="padding: 8px; border: 1px solid black;">{{ selectedOrder.date }}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      workOrders: [
        { id: 'WO-20240218-001', name: '生产工单A',batch:'CMC001A', date: '2025-02-18' },
        { id: 'WO-20240218-002', name: '维修工单B',batch:'CMC002A', date: '2025-02-17' },
      ],
      selectedOrder: {},
    };
  },
  methods: {
    printWorkOrder(order) {
      this.selectedOrder = order;

      this.$nextTick(() => {
        let printContent = document.getElementById('printArea').innerHTML;
        let originalContent = document.body.innerHTML;
        document.body.innerHTML = printContent;
        window.print();
        document.body.innerHTML = originalContent;
        window.location.reload(); // 刷新恢复界面
      });
    },
  },
};
</script>

<style scoped>
#printArea {
  padding: 20px;
  font-size: 16px;
}

th, td {
  padding: 8px;
  border: 1px solid black;
}
</style>
