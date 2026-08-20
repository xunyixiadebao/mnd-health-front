<template>
  <!-- 默认使用的样式是site-wrapper,当sidebar.sidebarFold值是true时，再叠加一个site-sidebar--fold样式 -->
  <!-- site-sidebar--fold这个样式专门负责折叠的左边栏的。 -->
  <!-- v-loading 是 Element Plus 提供的自定义指令，用于在元素上显示加载状态。 -->
  <!-- .fullscreen：表示全屏加载。 -->
  <!-- .lock：修饰符，表示锁定屏幕（防止用户在加载时进行其他操作）。 -->
  <!-- "loading"：对应模型层的loading变量，当 loading 为 true 时显示加载动画，为 false 时隐藏。 -->
  <!-- element-loading-text：是 v-loading 指令的配套属性，用于设置加载时显示的文字。 -->
  <div
    class="site-wrapper"
    :class="{ 'site-sidebar--fold': sidebar.sidebarFold }"
    v-loading.fullscreen.lock="loading"
    element-loading-text="加载中"
  >
    <!-- 导航栏：site-navbar样式决定了导航栏的背景色是蓝色，可以看源码确认下。 -->
    <nav
      class="site-navbar"
      :class="{ 'site-navbar--fold': sidebar.sidebarFold }"
    >
      <!-- 这部分是显示标题的 -->
      <div class="site-navbar__header">
        <h1 class="site-navbar__brand">
          <!-- 到底显示哪个是需要编写TS代码来实现的。 -->
          <!-- 展开时显示它 -->
          <a class="site-navbar__brand-lg">美年大健康体检系统</a>
          <!-- 折叠时显示它 -->
          <a class="site-navbar__brand-mini">体检</a>
        </h1>
      </div>

      <!-- 这一部分是折叠图标和用户信息，这个样式navbar-container决定了背景色是白色，可以通过源码看到背景色是白色，它把导航栏的蓝色遮蔽了。 -->
      <div
        class="navbar-container"
        :class="{ 'navbar-container--fold': sidebar.sidebarFold }"
      >
        <!-- 折叠图标 -->
        <div class="switch" @click="handleSwitch">
          <SvgIcon name="zhedie" class="icon-svg" />
        </div>
        <!-- 用户信息是一个下拉菜单 -->
        <div class="right-container">
          <el-dropdown>
            <span class="el-dropdown-link">
              <!-- 头像外边套一个span的目的是为了挪动头像的位置。为啥不能是div呢？因为它的父控件是span。父控件是span的，子控件不能使用div。 -->
              <span class="avatar-container">
                <!-- 头像：circle将头像修饰为圆形，user.photo不为空时显示它，如果为空时使用EP库中的UserFilled图标代替 -->
                <el-avatar
                  shape="circle"
                  :size="25"
                  :src="user.photo"
                  :icon="UserFilled"
                ></el-avatar>
              </span>
              <!-- 名字 -->
              {{ user.name }}
            </span>
            <!-- 下拉菜单 -->
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="updatePassword"
                  >修改密码</el-dropdown-item
                >
                <el-dropdown-item @click="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </nav>
    <!--样式是公共样式，主题色使用深色，这是边栏-->
    <aside class="site-sidebar site-sidebar--dark">
      <!--边栏中放一个div-->
      <div class="site-sidebar__inner">
        <!--div中放的是菜单组件-->
        <!--菜单项文字颜色：text-color="#8a979e" 灰黑色-->
        <!--菜单项被选中的话，文字颜色变为白色：active-text-color="#fff"-->
        <!--它来控制展开或折叠   :collapse="sidebar.sidebarFold" 菜单折叠后（只显示图标，宽度变窄）-->
        <!--展开或折叠时是否需要动画，它来决定：:collapseTransition="false" false表示不用动画-->
        <!--它决定了默认选择的菜单项  :default-active="siteContent.menuActiveName || 'Home'" -->
        <!-- background-color="#263238" 来规定一级菜单鼠标悬停时的背景色。-->
        <el-menu
          :default-active="siteContent.menuActiveName || 'Home'"
          :collapse="sidebar.sidebarFold"
          :collapseTransition="false"
          class="site-sidebar__menu"
          background-color="#263238"
          active-text-color="#fff"
          text-color="#8a979e"
        >
          <!--没有二级菜单项就不需要定义template-->
          <!-- $router.push({ name: 'MisHome' })的作用：当点击这个菜单项时，会跳转到名为 'MisHome' 的路由页面 -->
          <el-menu-item index="Home" @click="$router.push({ name: 'MisHome' })">
            <el-icon>
              <SvgIcon name="home" class="icon-svg" />
            </el-icon>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-sub-menu
            index="组织管理"
            :popper-class="
              'site-sidebar--' + sidebar.sidebarLayoutSkin + '-popper'
            "
          >
            <template #title>
              <el-icon>
                <SvgIcon name="users_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">组织管理</span>
            </template>
            <el-menu-item
              index="MisDept"
              v-if="isAuth(['ROOT', 'DEPT:SELECT'])"
              @click="$router.push({ name: 'MisDept' })"
            >
              <el-icon>
                <SvgIcon name="company_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">部门管理</span>
            </el-menu-item>
            <el-menu-item
              index="MisRole"
              v-if="isAuth(['ROOT', 'ROLE:SELECT'])"
              @click="$router.push({ name: 'MisRole' })"
            >
              <el-icon>
                <SvgIcon name="role_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">角色管理</span>
            </el-menu-item>
            <el-menu-item
              index="MisUser"
              v-if="isAuth(['ROOT', 'USER:SELECT'])"
              @click="$router.push({ name: 'MisUser' })"
            >
              <el-icon>
                <SvgIcon name="user_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">用户管理</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu
            index="业务管理"
            :popper-class="
              'site-sidebar--' + sidebar.sidebarLayoutSkin + '-popper'
            "
          >
            <template #title>
              <el-icon>
                <SvgIcon name="trust_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">业务管理</span>
            </template>
            <el-menu-item
              index="MisGoods"
              v-if="isAuth(['ROOT', 'GOODS:SELECT'])"
              @click="$router.push({ name: 'MisGoods' })"
            >
              <el-icon>
                <SvgIcon name="goods_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">体检套餐</span>
            </el-menu-item>
            <el-menu-item
              index="MisRule"
              v-if="isAuth(['ROOT', 'RULE:SELECT'])"
              @click="$router.push({ name: 'MisRule' })"
            >
              <el-icon>
                <SvgIcon name="rule_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">促销规则</span>
            </el-menu-item>
            <el-menu-item
              index="MisCustomer"
              v-if="isAuth(['ROOT', 'CUSTOMER:SELECT'])"
              @click="$router.push({ name: 'MisCustomer' })"
            >
              <el-icon>
                <SvgIcon name="customer_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">客户档案</span>
            </el-menu-item>
            <el-menu-item
              index="MisOrder"
              v-if="isAuth(['ROOT', 'ORDER:SELECT'])"
              @click="$router.push({ name: 'MisOrder' })"
            >
              <el-icon>
                <SvgIcon name="order_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">订单管理</span>
            </el-menu-item>
            <el-menu-item
              index="MisCustomerIm"
              v-if="isAuth(['ROOT', 'CUSTOMER_IM:SELECT'])"
              @click="$router.push({ name: 'MisCustomerIm' })"
            >
              <el-icon>
                <SvgIcon name="im_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">客服IM</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu
            index="体检管理"
            :popper-class="
              'site-sidebar--' + sidebar.sidebarLayoutSkin + '-popper'
            "
          >
            <template #title>
              <el-icon>
                <SvgIcon name="night_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">体检管理</span>
            </template>
            <el-menu-item
              index="MisAppointment"
              v-if="isAuth(['ROOT', 'APPOINTMENT:SELECT'])"
              @click="$router.push({ name: 'MisAppointment' })"
            >
              <el-icon>
                <SvgIcon name="appointment_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">体检预约</span>
            </el-menu-item>
            <el-menu-item
              index="MisCustomerCheckin"
              v-if="isAuth(['ROOT', 'CUSTOMER_CHECKIN:SELECT'])"
              @click="$router.push({ name: 'MisCustomerCheckin' })"
            >
              <el-icon>
                <SvgIcon name="checkin_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">体检签到</span>
            </el-menu-item>
            <el-menu-item
              index="MisAppointmentRestriction"
              v-if="isAuth(['ROOT', 'APPOINTMENT_RESTRICTION:SELECT'])"
              @click="$router.push({ name: 'MisAppointmentRestriction' })"
            >
              <el-icon>
                <SvgIcon name="setting_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">预约设置</span>
            </el-menu-item>
            <el-menu-item
              index="MisCheckup"
              v-if="isAuth(['ROOT', 'CHECKUP:SELECT'])"
              @click="$router.push({ name: 'MisCheckup' })"
            >
              <el-icon>
                <SvgIcon name="doctor_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">医生检查</span>
            </el-menu-item>
            <el-menu-item
              index="MisCheckupReport"
              v-if="isAuth(['ROOT', 'CHECKUP_REPORT:SELECT'])"
              @click="$router.push({ name: 'MisCheckupReport' })"
            >
              <el-icon>
                <SvgIcon name="file_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">体检报告</span>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu
            index="系统设置"
            :popper-class="
              'site-sidebar--' + sidebar.sidebarLayoutSkin + '-popper'
            "
            v-if="isAuth(['ROOT', 'SYSTEM:SELECT'])"
          >
            <template #title>
              <el-icon>
                <SvgIcon name="system_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">系统设置</span>
            </template>
            <el-menu-item
              index="MisFlowRegulation"
              v-if="isAuth(['ROOT', 'FLOW_REGULATION:SELECT'])"
              @click="$router.push({ name: 'MisFlowRegulation' })"
            >
              <el-icon>
                <SvgIcon name="people_fill" class="icon-svg" />
              </el-icon>
              <span slot="title">人员限流</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>
    </aside>
    <!--main本身是一个div，为什么外边再套一个？主要是为了使用外边div的背景色样式-->
    <!-- 内容区 -->
    <div class="site-content__wrapper">
      <main
        class="site-content"
        :class="{ 'site-content--tabs': $route.meta.isTab }"
      >
        <!-- 在该标签上添加关闭tab的事件 -->
        <el-tabs
          v-if="$route.meta.isTab"
          v-model="siteContent.mainTabsActiveName"
          :closable="true"
          @tab-click="selectedTabHandle"
          @tab-remove="removeTabHandle"
        >
          <el-tab-pane
            v-for="item in siteContent.mainTabs"
            :label="item.title"
            :name="item.name"
          >
            <el-card :body-style="siteContent.siteContentViewHeight">
              <router-view
                :key="router.currentRoute.value.fullPath"
              ></router-view>
            </el-card>
          </el-tab-pane>
        </el-tabs>
        <el-card v-else :body-style="siteContent.siteContentViewHeight">
          <router-view :key="router.currentRoute.value.fullPath"></router-view>
        </el-card>
      </main>
    </div>
  </div>
  <el-dialog title="提示" v-model="dialog.visible" width="25%">
    <el-form
      :model="dialog.dataForm"
      :rules="dialog.dataRule"
      ref="dialogForm"
      label-width="80px"
    >
      <el-form-item label="原密码" prop="password">
        <el-input
          type="password"
          v-model="dialog.dataForm.password"
          size="default"
          maxlength="20"
          clearable
        />
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input
          type="password"
          v-model="dialog.dataForm.newPassword"
          size="default"
          maxlength="20"
          clearable
        />
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          type="password"
          v-model="dialog.dataForm.confirmPassword"
          size="default"
          maxlength="20"
          clearable
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="default" @click="dialog.visible = false"
          >取消</el-button
        >
        <el-button type="primary" size="default" @click="dataFormSubmit"
          >确定</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { UserFilled } from "@element-plus/icons-vue";
import { isURL } from "../../utils/validate";
import {
  ref,
  reactive,
  provide,
  getCurrentInstance,
  onMounted,
  watch,
  nextTick,
} from "vue";
import { useRoute, useRouter } from "vue-router";
import { isAuth } from "@/utils/auth";
// 控制页面全屏加载动画的显示与隐藏
const loading = ref(false);

// 用于获取路由参数
const route = useRoute();
// 用于切换页面
const router = useRouter();
// 获取当前上文对象中的proxy属性，用它来访问全局变量和全局函数
const { proxy } = getCurrentInstance()!;

// 左边栏常量
const sidebar = reactive({
  // 左边栏状态：true折叠，false展开
  sidebarFold: false,
  // 左边栏主题色
  sidebarLayoutSkin: "dark",
});

//内容区相关常量（后续用到）
const siteContent = reactive({
  documentClientHeight: 0,
  siteContentViewHeight: {},
  height: 0,
  mainTabs: [] as Tab[],
  mainTabsActiveName: "",
  menuActiveName: "",
});

// 用户信息常量
const user = reactive({
  // 将来ajax请求动态获取姓名和头像
  // 姓名
  name: "",
  // 头像
  photo: "",
  // 是否显示修改密码的弹窗,false隐藏，true显示
  updatePasswordVisible: false,
});

// 退出功能
const logout = async () => {
  try {
    // 发送ajax post请求退出系统
    const response = await axios.post("/api/mis/user/logout", null, {
      headers: {
        satoken: localStorage.getItem("token"),
      },
    });
    if (response.data.code === 200) {
      // 退出成功，清空localStorage中的数据
      localStorage.removeItem("token");
      localStorage.removeItem("permissions");
      // 跳转到登录页面
      router.push({ name: "MisLogin" });
    }
  } catch {
    ElMessage.error("网络异常，稍后再试");
  }
};

// 修改密码
const dialogForm = ref();
const updatePassword = async() => {
  // 显示控件
  dialog.visible = true;
  // 等待 Vue 完成 DOM 更新后再执行后续代码
  await nextTick();
  // 重置表单
  dialogForm.value.resetFields();
};

// 折叠/展开
function handleSwitch() {
  sidebar.sidebarFold = !sidebar.sidebarFold;
}

import { type RouteLocationNormalized } from "vue-router";

interface Tab {
  title: string;
  name: string;
}

interface SiteContent {
  mainTabs: Tab[];
  mainTabsActiveName: string;
  menuActiveName: string;
}

// 方法名随意
// 调用该函数需要传递过来一个route对象，通过该对象可以获取当前路由参数。
function routeHandle(route: RouteLocationNormalized) {
  resetDocumentClientHeight();
  loadSiteContentViewHeight();
  // 判断：如果当前路由参数中meta.isTab是true时，才会显示tab页。
  if (route.meta.isTab) {
    // 从数组中查找这个tab。
    let tab = siteContent.mainTabs.find((item) => item.name === route.name);
    // 如果tab对象为空，说明数组中没有这个tab页。
    if (tab == null) {
      // 创建新的tab对象
      tab = {
        title: route.meta.title as string,
        name: route.name as string,
      };
      // 把tab对象存储到数组中，这样v-for立即遍历，会生成新的tab页。
      siteContent.mainTabs.push(tab);
    }
    // 不管是新的tab页，还是已存在的tab页，最终都要选中对应的菜单项和选中对应的tab页。
    // 选中对应的菜单项
    siteContent.menuActiveName = tab.name;
    // 选中对应的tab页
    siteContent.mainTabsActiveName = tab.name;
  } else {
    // 关闭所有打开的tab页
    siteContent.mainTabs = [];
    // tab页选中状态置空
    siteContent.mainTabsActiveName = "";
    // 菜单项选中Home
    siteContent.menuActiveName = "Home";
  }
}

// 用户有可能是在浏览器直接输入URL。或者用户有可能是刷新页面。而不是点击的菜单项。
// 在这里编写的代码是在初始化阶段执行的，也就是说在setup函数第一次执行时执行的。
// 专门用来处理用户直接输入URL，或者刷新浏览器。
routeHandle(route);

// 这个是用来监听路由变化的，也就是说初始化已经在第一次访问时已经完成了。
// 后续用户点击某个菜单项的时候，会再次执行 routeHandle(route);
watch(
  () => route.path,
  (newPath, oldPath) => {
    routeHandle(route);
  },
  { immediate: true },
);
//计算网页可见区域的高度
function resetDocumentClientHeight() {
  //获取网页可见区域的高度
  siteContent.documentClientHeight = document.documentElement.clientHeight;
}

//计算内容区卡片控件高度
function loadSiteContentViewHeight() {
  //卡片控件高度 = 网页可见区域高度 - 导航区高度 - 卡片控件上下外填充 - 上下边框
  let height = siteContent.documentClientHeight - 50 - 30 - 2;
  if (route.meta.isTab) {
    //如果引用的Vue页面需要Tab控件，卡片控件高度还要减去40
    height -= 40;
  }
  //保存卡片控件高度
  siteContent.height = height;
  //声明CSS样式
  siteContent.siteContentViewHeight = { minHeight: height + "px" };
}

//浏览器尺寸发生变化的回调函数
window.onresize = () => {
  //更新保存的网页可见区域高度
  siteContent.documentClientHeight = document.documentElement.clientHeight;
  //重新计算内容区的高度
  loadSiteContentViewHeight();
  // 当用户选中某个tab页的时候加载该tab页
};
import {
  type TabsPaneContext,
  type TabPaneName,
  ElMessage,
  type FormItemRule,
} from "element-plus";
import axios from "axios";

function selectedTabHandle(tab: TabsPaneContext) {
  router.push({
    name: tab.paneName as string,
  });
}
// 当用户点击关闭tab时执行回调函数
function removeTabHandle(tabName: TabPaneName) {
  siteContent.mainTabs = siteContent.mainTabs.filter(
    (item) => item.name !== tabName,
  );

  if (siteContent.mainTabs.length > 0) {
    const tab = siteContent.mainTabs[siteContent.mainTabs.length - 1]!;
    siteContent.mainTabsActiveName = tab.name;
    router.push({ name: tab.name });
  } else {
    siteContent.mainTabsActiveName = "";
    router.push({ name: "MisHome" });
  }
}
//修改密码

const validateConfirmPassword = (
  rule: FormItemRule,
  value: string,
  callback: Function,
) => {
  // value是确认密码
  if (value != dialog.dataForm.newPassword) {
    // 通过调用回调函数来通知表单验证系统，表单验证系统根据结果动态更新UI。
    callback(new Error("两次输入的密码不一致"));
  } else {
    // 通过调用回调函数来通知表单验证系统，表单验证系统根据结果动态更新UI。
    // 函数不传参数表示验证通过。
    callback();
  }
};
const dataFormSubmit = async () => {
  // 提交表单前，再次校验数据是否合法（校验是异步的）
  const valid = await dialogForm.value.validate();
  if (valid) {
    try {
      // 准备数据
      const sendData = {
        oldPassword: dialog.dataForm.password,
        newPassword: dialog.dataForm.newPassword,
      };
      // 准备配置
      const config = {
        headers: {
          satoken: localStorage.getItem("token"),
        },
      };
      // 发送ajax put请求
      const response = await axios.put(
        "/api/mis/user/password",
        sendData,
        config,
      );
      if (response.data.code === 200) {
        ElMessage.success(response.data.msg || "修改成功");
        dialog.visible = false;
        localStorage.removeItem("token")
        localStorage.removeItem("permissions")
        router.push({name: "MisLogin"})
      } else {
        ElMessage.error(response.data.msg || "修改失败");
      }
    } catch {
      ElMessage.error("网络异常，稍后再试");
    }
  }
};
const dialog = reactive({
  visible: false,
  dataForm: {
    password: "",
    newPassword: "",
    confirmPassword: "",
  },
  dataRule: {
    password: [
      {
        required: true,
        pattern: "^[a-zA-Z0-9]{6,20}$",
        message: "密码格式错误",
      },
    ],
    newPassword: [
      {
        required: true,
        pattern: "^[a-zA-Z0-9]{6,20}$",
        message: "密码格式错误",
      },
    ],
    confirmPassword: [
      {
        required: true,
        pattern: "^[a-zA-Z0-9]{6,20}$",
        message: "密码格式错误",
      },
      // blur失去焦点则调用validateConfirmPassword进行验证
      { validator: validateConfirmPassword, trigger: "blur" },
    ],
  },
});

</script>

<style lang="scss">
@import "@/assets/scss/index.scss";

// 当鼠标悬停到头像上的时候，el自带的黑边框就会显示出来，以下样式可以消除头像四周的黑边框
.el-dropdown-link {
  outline: none !important;
}

// 由于大部分样式使用了开源的scss样式，因此样式就不再定义到单独的文件中了。
// 以下样式是头像定位的样式
.avatar-container {
  // 微调头像元素的垂直位置和右边间距
  // 向下偏移8px
  vertical-align: -8px;
  // 右边距5px
  margin-right: 5px;
}
</style>
