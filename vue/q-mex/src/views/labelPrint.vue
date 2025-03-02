<template>
  <div>
    <button @click="printLabel">打印标签</button>
  </div>
</template>

<script>
export default {
  mounted() {
    if (!window.BrowserPrint) {
      console.error("Browser Print 未加载，请检查 localhost:9101 是否运行！");
    }
  },
  methods: {
    printLabel() {
      if (!window.BrowserPrint) {
        console.error("Browser Print 未运行！");
        return;
      }

      BrowserPrint.getDefaultDevice('printer', (device) => {
        if (!device) {
          console.error("未找到打印机！");
          return;
        }

        const zplData = `^XA
        ^FO50,50^ADN,36,20^FDHello, Zebra!^FS
        ^FO50,100^B3N,N,100,Y,N^FD123456789^FS
        ^XZ`;

        device.send(zplData, (response) => {
          console.log("打印成功", response);
        }, (error) => {
          console.error("打印失败", error);
        });
      }, (error) => {
        console.error("获取打印机失败", error);
      });
    }
  }
};
</script>
