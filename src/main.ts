import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
// 引入 Element Plus
import ElementPlus from "element-plus";
// 引入 Element Plus 的基础样式
import "element-plus/dist/index.css";
// 引入中文语言包（用于日历、分页等组件）
import zhCn from "element-plus/es/locale/lang/zh-cn";

import "virtual:svg-icons-register";

const app = createApp(App);
// 使用路由
app.use(router);
const minioUrl = "http://82.156.211.164:9000/mnd-health";
app.provide("minioUrl", minioUrl);
// 使用 Element Plus，并配置中文语言
app.use(ElementPlus, { locale: zhCn });
// 配置WebSocket
import VueNativeSockModule from "vue-native-websocket-vue3";
const VueNativeSock = (VueNativeSockModule as any).default;

app.use(VueNativeSock, "ws://localhost:8080/api/websocket/push/message", {
  //收发消息自动按照json格式解析和序列化
  format: "json",
  // 如果WebSocket连接长时间不收发请求，会被服务端切断连接，下面的设置可以自动重连
  reconnection: true,
});

app.mount("#app");
