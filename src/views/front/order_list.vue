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

import { dayjs, ElMessage, ElMessageBox } from "element-plus";
import isBetween from "dayjs/plugin/isBetween";
import axios from "axios";
dayjs.extend(isBetween);

let empty = ref(false);

const dataForm : any = reactive({
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
interface Order {
  createTime: string,
  outTradeNo: string,
  snapshotId: string,
  goodsImage: string,
  goodsTitle: string,
  goodsDescription: string,
  goodsPrice: number,
  quantity: number,
  totalAmount: number,
  orderStatus: string,
  disabled: boolean,
  appointCount: number,
  id: number
}
interface Data {
  dataList: Order[],
  pageIndex: number,
  pageSize: number,
  totalCount: number,
  loading: boolean,
}
const data: Data = reactive({
  dataList: [],
  pageIndex: 1,
  pageSize: 10,
  totalCount: 0,
  loading: false,
});
const loadPageList = async () => {
  // 显示加载进度条
  data.loading = true;

  try {
    // 根据用户的选择，设置订单状态值
    if (dataForm.statusLabel === "全部") {
      dataForm.status = null;
    } else if (dataForm.statusLabel === "未付款") {
      dataForm.status = 1;
    } else {
      dataForm.status = 3;
    }

    // 发送ajax请求
    const response = await axios.get("/api/front/order/list", {
      params: {
        keyword: dataForm.keyword,
        orderStatus: dataForm.status,
        pageNo: data.pageIndex,
        pageSize: data.pageSize,
      },
      headers: {
        satoken: localStorage.getItem("token"),
      },
    });

    const pageResult = response.data.data?.result;

    const statusEnum: Record<string, string> = {
      "1": "未付款",
      "2": "已关闭",
      "3": "已付款",
      "4": "已退款",
      "5": "已预约",
      "6": "已结束",
    };

    const list = pageResult.records || [];
    for (let one of list) {
      one.goodsImage = `${minioUrl}/${one.goodsImage}`;
      one.orderStatus = statusEnum[one.orderStatus + ""];
    }

    data.dataList = list;
    data.totalCount = pageResult.total || 0;
    empty.value = list.length === 0;
  } catch (error) {
    console.error("加载订单列表失败:", error);
    ElMessage.error("网络异常，请稍后再试");
  } finally {
    data.loading = false;
  }
};

loadPageList();

const refundHandle = async (orderId: number) => {
  try {
    await ElMessageBox.confirm("您确定要退款吗？", "提示信息", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    });

    const { data: responseData } = await axios.put(
      "/api/front/order/refund",
      { orderId },
      {
        headers: {
          satoken: localStorage.getItem("token"),
        },
      },
    );
    if (responseData.code === 200) {
      ElMessage.success({
        message: "退款申请已提交，请稍后查看到账情况",
        duration: 1200,
      });
    } else {
      ElMessage.error({
        message: "退款失败，请联系客服",
        duration: 1200,
      });
    }
  } catch (error) {
    if (error === "cancel") return;
    ElMessage.error({
      message: "退款失败，请联系客服",
      duration: 1200,
    });
  }
};
const appointHandle = (
  id: number,
  quantity: number,
  appointCount: number,
) => {};
const closeOrderHandle = (id: number) => {};
const paymentHandle = (no: string) => {};
const sizeChangeHandle = (pageSize: number) => {
  data.pageSize = pageSize;
  data.pageIndex = 1;
  loadPageList();
};

const currentChangeHandle = (pageNo: number) => {
  data.pageIndex = pageNo;
  loadPageList();
};
const form = ref();
const searchHandle = async () => {
  try {
    await form.value.validate();
    // 重置页码并加载数据
    data.pageIndex = 1;
    loadPageList();
  } catch {
    // 验证失败，不做任何操作
  }
};
const searchDetailHandle = (snapshotId: string) => {};
</script>
<style lang="less" scoped>
@import url("order_list.less");
</style>