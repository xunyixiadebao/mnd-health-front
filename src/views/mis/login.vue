<template>
  <!-- 整个页面，设置背景图片 -->
  <div class="page">
    <!-- 登录面板 -->
    <div class="panel">
      <!-- 左边一个小div -->
      <div class="left">
        <!-- 上面一个图片 -->
        <img src="../../assets/login/logo.png" class="logo" />
        <!-- 下面一个图片 -->
        <img src="../../assets/login/big.png" class="big" />
      </div>
      <!-- 右边一个小div -->
      <div class="right">
        <!-- 显示标题字和版本号的div -->
        <div class="title-container">
          <h2>美年大健康体检系统</h2>
          <span>V1.0</span>
        </div>
        <!-- 登录表单控件div -->
        <div>
          <div class="row">
            <el-input
              v-model="loginInfo.username"
              placeholder="用户名"
              :prefix-icon="User"
              size="large"
              clearable
            ></el-input>
          </div>
          <div class="row">
            <el-input
              type="password"
              v-model="loginInfo.password"
              placeholder="密码"
              :prefix-icon="Lock"
              size="large"
              clearable
            ></el-input>
          </div>
          <div class="row">
            <el-button type="primary" class="btn" size="large" @click="login">
              登录系统
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from "vue"
import { User, Lock } from '@element-plus/icons-vue'




// 登录数据
const loginInfo = reactive({
  username: "",
  password: "",
});

import { isUsername, isPassword } from "@/utils/validate";
import { ElMessage } from "element-plus";
import axios from "axios";
import router from "@/router";

async function login() {
  const username = loginInfo.username;
  const password = loginInfo.password;
  if (!isUsername(username)) {
    // 验证用户名是否有效
    ElMessage({
      type: "error",
      message: "用户名格式错误",
      duration: 1200,
    });
  } else if (!isPassword(password)) {
    // 验证密码是否有效
    ElMessage({
      type: "error",
      message: "密码格式错误",
      duration: 1200,
    });
  } else {
    try {
      // 发送ajax post请求登录
      const response = await axios.post("/api/mis/user/login", {
        username,
        password,
      });
      const result = response.data;
      if (result.code === 200) {
        // 登录成功
        const token = result.data.token;
        const permissions = JSON.stringify(result.data.permissions);
        localStorage.setItem("token", token);
        localStorage.setItem("permissions", permissions);
        router.push({ name: "MisHome" });
      } else {
        // 登录失败
        ElMessage({
          type: "error",
          message: "登录失败",
          duration: 1200,
        });
      }
    } catch {
      ElMessage.error("网络异常，请稍后再试");
    }
  }
}

</script>

<style lang="less" scoped>
@import url('login.less');
</style>