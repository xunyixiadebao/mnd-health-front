<template>
  <!-- 面包屑导航组件，用于显示当前页面在网站结构中的位置 -->
  <el-breadcrumb separator="/" class="breadcrumb">
    <!-- 首页面包屑项，点击可跳转到首页 -->
    <el-breadcrumb-item :to="{ name: 'FrontIndex' }">首页</el-breadcrumb-item>
    <!-- 当前页面面包屑项，显示为体检套餐列表页面 -->
    <el-breadcrumb-item>体检套餐列表</el-breadcrumb-item>
  </el-breadcrumb>

  <!-- 搜索条件筛选区域，包含分类、性别、价格等多维度筛选 -->
  <div class="search-rows">
    <!-- 分类筛选行：使用Element Plus的栅格布局，gutter="0"表示列间无间隔 -->
    <el-row :gutter="0" class="row">
      <!-- 分类标签，占据2列宽度 -->
      <el-col :span="2"><span class="label">【 分类 】</span></el-col>
      <!-- 动态渲染分类选项，每个选项占据2列宽度 -->
      <el-col :span="2" v-for="one in condition.type" :key="one.name">
        <!-- 条件选项，根据active状态切换样式，点击触发筛选 -->
        <span
          :class="one.active ? 'item active' : 'item'"
          @click="selectHandle('type', one.name)"
        >
          {{ one.name }}
        </span>
      </el-col>
    </el-row>

    <!-- 性别筛选行：结构与分类筛选行类似 -->
    <el-row :gutter="0" class="row">
      <el-col :span="2"><span class="label">【 性别 】</span></el-col>
      <el-col :span="2" v-for="one in condition.sex" :key="one.name">
        <span
          :class="one.active ? 'item active' : 'item'"
          @click="selectHandle('sex', one.name)"
        >
          {{ one.name }}
        </span>
      </el-col>
    </el-row>

    <!-- 价格区间筛选行：提供不同价格范围的筛选选项 -->
    <el-row :gutter="0" class="row">
      <el-col :span="2"><span class="label">【 价格 】</span></el-col>
      <el-col :span="2" v-for="one in condition.priceType" :key="one.name">
        <span
          :class="one.active ? 'item active' : 'item'"
          @click="selectHandle('priceType', one.name)"
        >
          {{ one.name }}
        </span>
      </el-col>
    </el-row>
  </div>

  <!-- 排序筛选区域：提供多种排序方式选择 -->
  <div class="search-filter">
    <!-- 排序单选按钮组，v-model绑定当前选中的排序方式 -->
    <el-radio-group v-model="radio" @change="selectRadio">
      <!-- 按最新排序选项 -->
      <el-radio label="最新" size="large">最新</el-radio>
      <!-- 按销量排序选项 -->
      <el-radio label="销量" size="large">销量</el-radio>
    </el-radio-group>

    <!-- 价格排序操作区域，点击可切换升序/降序 -->
    <div class="sort-operate" @click="selectPrice">
      <span>价格</span>
      <!-- 价格排序图标，根据排序状态显示不同图标 -->
      <SvgIcon :name="priceOrder.icon" class="sort-icon" />
    </div>
  </div>
  <div class="goods-container">
    <el-empty
      description="无体检套餐"
      :image-size="200"
      v-if="data.dataList.length == 0"
    ></el-empty>
    <!-- 商品列表容器，使用v-infinite-scroll指令实现无限滚动加载 -->
    <ul class="goods-list" v-infinite-scroll="load">
      <!-- 遍历商品数据列表，生成商品项 -->
      <li
        class="item"
        v-for="(one, index) in data.dataList"
        :style="(index + 1) % 4 == 0 ? 'margin-right:0' : ''"
      >
        <!-- 每行第4个商品移除右边距 -->
        <div class="card">
          <!-- 商品图片 -->
          <img :src="one.coverImage" />
          <!-- 商品标题 -->
          <h4>{{ one.packageName }}</h4>
          <!-- 商品描述，使用Element Plus的提示框组件 -->
          <el-tooltip class="box-item" effect="dark" placement="top">
            <template #content>
              <div style="width: 260px">{{ one.description }}</div>
            </template>
            <p class="desc">
              <span>折</span>
              <!-- 折扣标签 -->
              {{ one.description }}
            </p>
          </el-tooltip>
          <!-- 价格信息区域 -->
          <p class="price">
            <span class="current">￥{{ one.currentPrice }}</span>
            <!-- 当前价格 -->
            <span class="old">￥{{ one.originalPrice }}</span>
            <!-- 原价 -->
            <span class="sale">已售{{ one.salesVolume }}</span>
            <!-- 销量 -->
          </p>
          <!-- 立即购买按钮 -->
          <input
            type="button"
            class="buy-btn"
            value="立即购买"
            @click="buyHandle(one.id)"
          />
        </div>
      </li>
    </ul>
  </div>
</template>
<script lang="ts" setup>
import router from "@/router";
import axios from "axios";
import { reactive, ref, getCurrentInstance } from "vue";

let radio = ref();
//滚动到页面的顶部，否则路由跳转页面之后，页面垂直位置还是上一个页面的地方
window.scrollTo(0, 0);

const priceOrder = reactive({
  icon: "sort-default", // sort-asc 升序图标。sort-desc 降序图标。
});

const dataForm: any = reactive({
  keyword: null,
  type: null,
  sex: null,
  priceType: null,
  orderType: null,
});

const condition = reactive({
  type: [
    { name: "不限", active: true },
    { name: "父母体检", active: false },
    { name: "入职体检", active: false },
    { name: "职场白领", active: false },
    { name: "个人高端", active: false },
    { name: "中青年体检", active: false },
  ],
  sex: [
    { name: "不限", active: true },
    { name: "男性", active: false },
    { name: "女性", active: false },
  ],
  priceType: [
    { name: "不限", active: true },
    { name: "0~100", value: 1, active: false },
    { name: "100~500", value: 2, active: false },
    { name: "500~1000", value: 3, active: false },
    { name: "1000以上", value: 4, active: false },
  ],
});
import { inject } from "vue";

const minioUrl = inject("minioUrl");
interface Goods {
  id: number;
  packageName: string;
  coverImage: string;
  description: string;
  currentPrice: number;
  originalPrice: number;
  salesVolume: number;
}

const data = reactive({
  dataList: [] as Goods[],
  pageIndex: 1, // 如果初始值为1，当页面第一次加载时，它会变成2，导致第一页数据丢失。
  pageSize: 12, // 12正好是4的倍数，三行比较好看。
  totalCount: 0,
  isLast: false, // 是否为最后一页的标记(当用户在滚动页面时，如果已经加载到最后一页了，isLast值将被设置为true，用来阻止继续发送ajax请求。)
});
const loadPageData = async () => {
  if (data.isLast) return;

  try {
    const { data: responseData } = await axios.get("/api/front/goods/list", {
      params: {
        keyword: dataForm.keyword,
        packageType: dataForm.type,
        sex: dataForm.sex,
        priceType: dataForm.priceType,
        orderType: dataForm.orderType,
        pageNo: data.pageIndex,
        pageSize: data.pageSize,
      },
    });

    const list = responseData.data.pageResult.records;

    if (list.length === 0) {
      data.isLast = true;
      data.pageIndex--;
      return;
    }

    list.forEach((item: Goods) => {
      item.coverImage = `${minioUrl}/${item.coverImage}`;
    });

    data.dataList = [...data.dataList, ...list];
    data.totalCount = responseData.data.pageResult.total;
  } catch (error) {
    console.error("加载商品列表失败:", error);
  }
};
dataForm.keyword = router.currentRoute.value.query.keyword || null;
loadPageData();

const load = () => {
  if (data.isLast) return;
  data.pageIndex++;
  loadPageData();
};
const buyHandle = (id: number) => {
  router.push({ name: "FrontGoods", params: { id } });
};
const selectHandle = (key: string, name: string) => {
  // 更新选中状态
  (condition as any)[key].forEach((one: any) => {
    one.active = one.name === name;
  });

  // 更新查询参数
  const isUnlimited = name === "不限";

  if (key === "type") {
    dataForm.type = isUnlimited ? null : name;
  } else if (key === "sex") {
    dataForm.sex = isUnlimited ? null : name;
  } else if (key === "priceType") {
    const target = condition[key].find((item) => item.name === name);
    dataForm.priceType = isUnlimited ? null : target?.value;
  }

  // 重置分页
  data.dataList = [];
  data.isLast = false;
  data.pageIndex = 1;
  data.pageSize = 12;
  data.totalCount = 0;

  // 加载第一页数据
  loadPageData();
};
const selectRadio = (value: any) => {
  // 按照价格升降序的图标恢复默认
  priceOrder.icon = "sort-default";
  // 修改响应式数据
  dataForm.orderType = value === "最新" ? 1 : value === "销量" ? 2 : null;

  // 重置分页
  data.dataList = [];
  data.isLast = false;
  data.pageIndex = 1;
  data.pageSize = 12;
  data.totalCount = 0;
  // 加载数据
  loadPageData();
};
const selectPrice = () => {
  // 先把销量和最新单选按钮取消选中。
  radio.value = null;

  // 然后再维护响应式对象的状态。
  if (priceOrder.icon === "sort-default" || priceOrder.icon === "sort-desc") {
    priceOrder.icon = "sort-asc";
    dataForm.orderType = 3;
  } else {
    priceOrder.icon = "sort-desc";
    dataForm.orderType = 4;
  }

  // 重置分页
  data.dataList = [];
  data.isLast = false;
  data.pageIndex = 1;
  data.pageSize = 12;
  data.totalCount = 0;
  // 加载数据
  loadPageData();
};
</script>

<style lang="less" scoped>
@import url("goods_list.less");
</style>
