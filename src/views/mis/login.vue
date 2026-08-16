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
            <el-button type="primary" class="btn" size="large">
              登录系统
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { getCurrentInstance, reactive } from "vue"
import { User, Lock } from '@element-plus/icons-vue'

const { proxy } = getCurrentInstance()!;

// 登录数据
const loginInfo = reactive({
  username: null,
  password: null,
});
</script>

<style lang="less" scoped>
@import url('login.less');
</style>