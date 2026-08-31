<template>
  <img src="../../assets/front/index/banner.jpg" class="banner" />
  <div class="service-container">
    <div class="service">
      <img src="../../assets/front/index/service-icon-1.png" />
      <div>
        <h4>体检预约</h4>
        <p>在线预约体检服务</p>
      </div>
    </div>
    <div class="service">
      <img src="../../assets/front/index/service-icon-2.png" />
      <div>
        <h4>报告查询</h4>
        <p>快速获取体检报告</p>
      </div>
    </div>
    <div class="service">
      <img src="../../assets/front/index/service-icon-3.png" />
      <div>
        <h4>热门活动</h4>
        <p>精选特惠体检套餐</p>
      </div>
    </div>
    <div class="service">
      <img src="../../assets/front/index/service-icon-4.png" />
      <div>
        <h4>企业服务</h4>
        <p>企业团体健康管理</p>
      </div>
    </div>
  </div>
  <div class="goods-container">
    <nav>
      <h3>活动专区</h3>
      <el-link :icon="Plus" :underline="false" @click="moreHandle"
        >查看更多</el-link
      >
    </nav>
    <ul class="goods-list">
      <li class="item" v-for="one in data.part_1">
        <div class="card">
          <img :src="one.coverImage" />
          <h4>{{ one.packageName }}</h4>
          <el-tooltip class="box-item" effect="dark" placement="top">
            <template #content>
              <div style="width: 260px">{{ one.description }}</div>
            </template>
            <p class="desc">
              <span>折</span>
              {{ one.description }}
            </p>
          </el-tooltip>
          <p class="price">
            <span class="current">￥{{ one.currentPrice }}</span>
            <span class="old">￥{{ one.originalPrice }}</span>
            <span class="sale">已售{{ one.salesVolume }}</span>
          </p>
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

  <div class="goods-container">
    <nav>
      <h3>热卖套餐</h3>
      <el-link :icon="Plus" :underline="false" @click="moreHandle"
        >查看更多</el-link
      >
    </nav>
    <ul class="goods-list">
      <li class="item" v-for="one in data.part_2">
        <div class="card">
          <img :src="one.coverImage" />
          <h4>{{ one.packageName }}</h4>
          <el-tooltip class="box-item" effect="dark" placement="top">
            <template #content>
              <div style="width: 260px">{{ one.description }}</div>
            </template>
            <p class="desc">
              <span>折</span>
              {{ one.description }}
            </p>
          </el-tooltip>
          <p class="price">
            <span class="current">￥{{ one.currentPrice }}</span>
            <span class="old">￥{{ one.originalPrice }}</span>
            <span class="sale">已售{{ one.salesVolume }}</span>
          </p>
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

  <div class="goods-container">
    <nav>
      <h3>新品推荐</h3>
      <el-link :icon="Plus" :underline="false" @click="moreHandle"
        >查看更多</el-link
      >
    </nav>
    <ul class="goods-list">
      <li class="item" v-for="one in data.part_3">
        <div class="card">
          <img :src="one.coverImage" />
          <h4>{{ one.packageName }}</h4>
          <el-tooltip class="box-item" effect="dark" placement="top">
            <template #content>
              <div style="width: 260px">{{ one.description }}</div>
            </template>
            <p class="desc">
              <span>折</span>
              {{ one.description }}
            </p>
          </el-tooltip>
          <p class="price">
            <span class="current">￥{{ one.currentPrice }}</span>
            <span class="old">￥{{ one.originalPrice }}</span>
            <span class="sale">已售{{ one.salesVolume }}</span>
          </p>
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
  <!-- 合作伙伴展示区域 -->
  <div class="partner-container">
    <!-- 分区标题，使用特殊符号装饰 -->
    <h3>—&nbsp;&nbsp;合作伙伴&nbsp;&nbsp;—</h3>

    <!-- Element Plus 走马灯组件，设置固定高度并关闭自动播放 -->
    <el-carousel height="220px" :autoplay="true">
      <!-- 第一个轮播页 -->
      <el-carousel-item>
        <!-- 第一行合作伙伴Logo，使用24栅格布局，列间距20 -->
        <el-row :gutter="20">
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c1.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c2.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c3.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c4.png" />
          </el-col>
        </el-row>
        <!-- 第二行合作伙伴Logo -->
        <el-row :gutter="20">
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c5.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c6.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c7.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c8.png" />
          </el-col>
        </el-row>
      </el-carousel-item>

      <!-- 第二个轮播页 -->
      <el-carousel-item>
        <!-- 第三行合作伙伴Logo -->
        <el-row :gutter="20">
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c9.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c10.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c11.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c12.png" />
          </el-col>
        </el-row>
        <!-- 第四行合作伙伴Logo -->
        <el-row :gutter="20">
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c13.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c14.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c15.png" />
          </el-col>
          <el-col :span="6">
            <img class="logo" src="../../assets/front/index/c16.png" />
          </el-col>
        </el-row>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup lang="ts">
import { reactive, inject } from "vue";
import { Plus } from "@element-plus/icons-vue";
import axios from "axios";
import router from "@/router";
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
  part_1: [] as Goods[],
  part_2: [] as Goods[],
  part_3: [] as Goods[],
});
const loadTop4 = async () => {
  try {
    const { data: responseData } = await axios.post("/api/front/goods/top4", {
      categoryIds: [1, 2, 3],
    });

    const top4 = responseData.data.top4;

    Object.keys(top4).forEach((key) => {
      
      // 遍历每个套餐，将套餐中的 coverImage 的相对路径修改为绝对路径。
      top4[key].forEach((item: Goods) => {
        item.coverImage = `${minioUrl}/${item.coverImage}`;
      });
      
      (data as any)[`part_${key}`] = top4[key];
    });
  } catch (error) {
    console.error("加载Top4套餐失败:", error);
  }
};

loadTop4();

const moreHandle = () => {
  router.push({ name: "FrontGoodsList"});
};
const buyHandle = (id: number) => {
  router.push({ name: "FrontGoods", params: { id } });
};
</script>

<style lang="less" scoped>
@import url("index.less");
</style>

<script lang="ts" setup></script>

<style lang="less" scoped></style>
