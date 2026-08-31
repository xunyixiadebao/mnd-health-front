<template>
  <div class="customer-container">
    <el-menu 
      :default-active="active" 
      class="menu" 
      @select="selectHandle"
    >
      <el-menu-item index="FrontMine">
        <el-icon>
          <User />
        </el-icon>
        <span>账户信息</span>
      </el-menu-item>
      <el-menu-item index="FrontOrderList">
        <el-icon>
          <Tickets />
        </el-icon>
        <span>我的订单</span>
      </el-menu-item>
      <el-menu-item index="FrontAppointmentList">
        <el-icon>
          <Files />
        </el-icon>
        <span>体检预约</span>
      </el-menu-item>
      <el-menu-item index="FrontCustomerIm">
        <el-icon>
          <Collection />
        </el-icon>
        <span>客服咨询</span>
      </el-menu-item>
    </el-menu>
    <div class="content">
      <RouterView />
    </div>
  </div>
</template>

<script lang="ts" setup>
import router from "@/router";
import { ref } from "vue";
import { useRoute } from 'vue-router';

// 获取当前路由
const route = useRoute();
// 使用当前路由名称作为激活菜单项，如果不存在则使用默认值
const active = ref<string>(route.name as string || 'FrontMine');

// 菜单选择处理函数
function selectHandle(index: string) {
  router.push({ name: index });
}
</script>

<style lang="less" scoped>
@import url("customer.less");
</style>