<template>
  <el-form :inline="true" :model="dataForm" :rules="dataRule" ref="form">
    <el-form-item prop="keyword">
      <el-input
        v-model="dataForm.keyword"
        placeholder="套餐标题 / 订单编号"
        size="default"
        class="keyword"
        maxlength="32"
        clearable
      />
    </el-form-item>
    <el-form-item>
      <el-button
        size="default"
        type="primary"
        :icon="Search"
        @click="searchHandle()"
        >查询</el-button
      >
    </el-form-item>
    <el-form-item class="mold">
      <el-radio-group
        v-model="dataForm.statusLabel"
        size="default"
        @change="searchHandle()"
      >
        <el-radio-button label="全部"></el-radio-button>
        <el-radio-button label="未付款"></el-radio-button>
        <el-radio-button label="已付款"></el-radio-button>
      </el-radio-group>
    </el-form-item>
  </el-form>
  <div class="order-list" v-show="!empty">
    <div class="order" v-for="one in data.dataList">
      <div class="header">
        <div class="datetime">{{ one.createTime }}</div>
        <div class="uuid">
          订单号：
          <span>{{ one.outTradeNo }}</span>
        </div>
        <div class="detail" @click="searchDetailHandle(one.snapshotId)">
          查看商品详情
        </div>
      </div>
      <div class="content">
        <img :src="one.goodsImage" class="image" />
        <div class="info">
          <h4>{{ one.goodsTitle }}</h4>
          <p>{{ one.goodsDescription }}</p>
        </div>
        <div class="price">
          <span class="label">套餐单价</span>
          <span class="value">￥{{ one.goodsPrice }}</span>
        </div>
        <div class="number">
          <span class="label">购买数量</span>
          <span class="value">×{{ one.quantity }}</span>
        </div>
        <div class="amount">
          <span class="label">合计</span>
          <span class="value">￥{{ one.totalAmount }}</span>
        </div>
        <div class="status">
          <span class="label">状态</span>
          <span class="value">{{ one.orderStatus }}</span>
        </div>
        <div class="operate">
          <el-button
            v-if="one.orderStatus == '未付款'"
            type="primary"
            :disabled="one.disabled"
            @click="paymentHandle(one.outTradeNo)"
          >
            付款
          </el-button>
          <el-button
            v-if="one.orderStatus == '未付款'"
            type="danger"
            @click="closeOrderHandle(one.id)"
          >
            取消订单
          </el-button>
          <el-button
            v-if="one.orderStatus == '已付款'"
            type="primary"
            :disabled="one.appointCount == one.quantity"
            @click="appointHandle(one.id, one.quantity, one.appointCount)"
          >
            预约体检
          </el-button>
          <el-button v-if="one.orderStatus == '已结束'">获取发票</el-button>
          <el-button
            v-if="one.orderStatus == '已付款'"
            type="danger"
            :disabled="one.appointCount > 0"
            @click="refundHandle(one.id)"
          >
            退款
          </el-button>
        </div>
      </div>
    </div>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="data.pageIndex"
      :page-sizes="[10, 20, 50]"
      :page-size="data.pageSize"
      :total="data.totalCount"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
  </div>
  <div class="empty" v-show="empty">
    <el-empty :image-size="200" />
  </div>
</template>
<script lang="ts" setup>
import { reactive, ref, type Ref, getCurrentInstance, inject } from "vue";
import { Search } from "@element-plus/icons-vue";
import router from "../../router/index";

import { dayjs } from "element-plus";
import isBetween from "dayjs/plugin/isBetween";
dayjs.extend(isBetween);

let empty = ref(false);

const dataForm = reactive({
  keyword: null,
  statusLabel: "全部",
  status: null,
});

const dataRule = reactive({
  keyword: [
    {
      required: false,
      pattern: "^[a-zA-Z0-9\u4e00-\u9fa5]{1,32}$",
      message: "关键字内容不正确",
    },
  ],
});

const minioUrl = inject("minioUrl");
const data: any = reactive({
  dataList: [
    {
      createTime: "2025-10-01 10:10:10",
      outTradeNo: "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
      snapshotId: "888888",
      goodsImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      goodsTitle: "公司白领体检套餐",
      goodsDescription:
        "「感恩季到检钜惠」适合人群：适用于中、青年人白领及熬夜党 （参加第二件0元活动 需加入购物车后数量选2）",
      goodsPrice: 5000,
      quantity: 4,
      totalAmount: 20000,
      orderStatus: "已付款",
    },
    {
      createTime: "2025-10-01 10:10:10",
      outTradeNo: "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
      snapshotId: "888888",
      goodsImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      goodsTitle: "公司白领体检套餐",
      goodsDescription:
        "「感恩季到检钜惠」适合人群：适用于中、青年人白领及熬夜党 （参加第二件0元活动 需加入购物车后数量选2）",
      goodsPrice: 5000,
      quantity: 4,
      totalAmount: 20000,
      orderStatus: "已付款",
    },
    {
      createTime: "2025-10-01 10:10:10",
      outTradeNo: "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
      snapshotId: "888888",
      goodsImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      goodsTitle: "公司白领体检套餐",
      goodsDescription:
        "「感恩季到检钜惠」适合人群：适用于中、青年人白领及熬夜党 （参加第二件0元活动 需加入购物车后数量选2）",
      goodsPrice: 5000,
      quantity: 4,
      totalAmount: 20000,
      orderStatus: "已付款",
    },
    {
      createTime: "2025-10-01 10:10:10",
      outTradeNo: "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
      snapshotId: "888888",
      goodsImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      goodsTitle: "公司白领体检套餐",
      goodsDescription:
        "「感恩季到检钜惠」适合人群：适用于中、青年人白领及熬夜党 （参加第二件0元活动 需加入购物车后数量选2）",
      goodsPrice: 5000,
      quantity: 4,
      totalAmount: 20000,
      orderStatus: "已付款",
    },
  ],
  pageIndex: 1,
  pageSize: 10,
  totalCount: 0,
  loading: false,
});

const sizeChangeHandle = () => {};
const currentChangeHandle = () => {};
const refundHandle = (id: number) => {};
const appointHandle = (
  id: number,
  quantity: number,
  appointCount: number,
) => {};
const closeOrderHandle = (id: number) => {};
const paymentHandle = (no: string) => {};
const searchHandle = () => {};
const searchDetailHandle = (snapshotId: string) => {};
</script>
<style lang="less" scoped>
@import url("order_list.less");
</style>