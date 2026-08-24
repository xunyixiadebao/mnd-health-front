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

app.mount("#app");
