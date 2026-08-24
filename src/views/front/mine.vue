<template>
  <div class="summary-container">
    <el-card class="box-card" shadow="never">
      <div class="info">
        <div class="left">
          <el-avatar :size="45" shape="square" :src="data.photoUrl">
            <el-icon size="25">
              <UserFilled />
            </el-icon>
          </el-avatar>
        </div>
        <div class="right">
          <div class="base">
            <span>姓名：{{ data.customerName }}</span>
            <span>性别：{{ data.gender }}</span>
            <span>电话号码：{{ data.phone }}</span>
            <div class="operate" @click="updateHandle">
              <el-icon :size="18">
                <Edit />
              </el-icon>
              <div>修改资料</div>
            </div>
          </div>
          <p>注册时间：{{ data.registerTime }}</p>
        </div>
      </div>
      <el-divider />
      <el-row :gutter="16">
        <el-col :span="6">
          <div class="statistic-card">
            <el-statistic :value="data.amount" suffix="元">
              <template #title>
                <div class="title">累计消费金额</div>
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="statistic-card">
            <el-statistic :value="data.count" suffix="笔">
              <template #title>
                <div class="title">有效订单数量</div>
              </template>
            </el-statistic>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="statistic-card">
            <el-statistic :value="data.number" suffix="个">
              <template #title>
                <div class="title">体检套餐数量</div>
              </template>
            </el-statistic>
          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>
<script lang="ts" setup>
import { reactive, ref, type Ref, getCurrentInstance } from "vue";
import router from "../../router/index";
import { UserFilled } from "@element-plus/icons-vue";

const data = reactive({
  customerName: "东邪西毒",
  gender: "男",
  phone: "18888888888",
  photoUrl: "",
  registerTime: "2025-10-01",
  count: 10,
  number: 5,
  amount: 8888,
});

const updateHandle = () => {};
</script>
<style lang="less" scoped>
@import url("mine.less");
</style>