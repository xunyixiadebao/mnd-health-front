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
          <h4>{{ one.title }}</h4>
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
import { reactive, ref, getCurrentInstance } from "vue";

let radio = ref();

const priceOrder = reactive({
  icon: "sort-default", // sort-asc 升序图标。sort-desc 降序图标。
});

const dataForm = reactive({
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
const data = reactive({
  dataList: [
    {
      id: 1,
      title: "银龄关怀健康呵护套餐",
      coverImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      description:
        "「感恩回馈季 到检享优惠」适用对象：中老年群体及心血管健康关注者 （参与买一赠一活动 需在购物车内选择2件商品）",
      currentPrice: 5000.0,
      originalPrice: 8000.0,
      salesVolume: 888,
    },
    {
      id: 2,
      title: "银龄关怀健康呵护套餐",
      coverImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      description:
        "「感恩回馈季 到检享优惠」适用对象：中老年群体及心血管健康关注者 （参与买一赠一活动 需在购物车内选择2件商品）",
      currentPrice: 5000.0,
      originalPrice: 8000.0,
      salesVolume: 888,
    },
    {
      id: 3,
      title: "银龄关怀健康呵护套餐",
      coverImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      description:
        "「感恩回馈季 到检享优惠」适用对象：中老年群体及心血管健康关注者 （参与买一赠一活动 需在购物车内选择2件商品）",
      currentPrice: 5000.0,
      originalPrice: 8000.0,
      salesVolume: 888,
    },
    {
      id: 4,
      title: "银龄关怀健康呵护套餐",
      coverImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      description:
        "「感恩回馈季 到检享优惠」适用对象：中老年群体及心血管健康关注者 （参与买一赠一活动 需在购物车内选择2件商品）",
      currentPrice: 5000.0,
      originalPrice: 8000.0,
      salesVolume: 888,
    },
    {
      id: 5,
      title: "银龄关怀健康呵护套餐",
      coverImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      description:
        "「感恩回馈季 到检享优惠」适用对象：中老年群体及心血管健康关注者 （参与买一赠一活动 需在购物车内选择2件商品）",
      currentPrice: 5000.0,
      originalPrice: 8000.0,
      salesVolume: 888,
    },
    {
      id: 6,
      title: "银龄关怀健康呵护套餐",
      coverImage: `${minioUrl}/front/goods/d079bf3cfbe64aa9b5e17a2e77244bb7.jpg`,
      description:
        "「感恩回馈季 到检享优惠」适用对象：中老年群体及心血管健康关注者 （参与买一赠一活动 需在购物车内选择2件商品）",
      currentPrice: 5000.0,
      originalPrice: 8000.0,
      salesVolume: 888,
    },
  ],
  pageIndex: 0, // 这个是当前页码，为什么从0开始，mis端都是从1开始呀。这个后面再说。
  pageSize: 12, // 12正好是4的倍数，三行比较好看。
  totalCount: 0,
  isLast: false, // 是否为最后一页的标记。
});

const load = () => {};
const buyHandle = (id: number) => {};
const selectHandle = (type: string, name: string) => {};
const selectRadio = () => {};
const selectPrice = () => {};
</script>

<style lang="less" scoped>
@import url("goods_list.less");
</style>
