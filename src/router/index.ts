import { createRouter, createWebHistory } from 'vue-router';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/front",
      name: "Front",
      component: () => import("../views/front/index.vue"),
      children: [
        {
          path: "index",
          name: "FrontIndex",
          component: () => import("../views/front/index.vue"),
        },
      ],
    },
    {
      path: "/mis",
      name: "Mis",
      component: () => import("../views/mis/main.vue"),
      children: [],
    },
    {
      path: "/mis/login",
      name: "MisLogin",
      component: () => import("../views/mis/login.vue"),
    },
    {
      path: "/404",
      name: "404",
      component: () => import("@/views/404.vue"),
    },
    {
      path: "/:pathMatch(.*)*",
      redirect: "/404",
    },
  ],
});

router.beforeEach((to, from) => {
  const token = localStorage.getItem("token");
  const permissions = localStorage.getItem("permissions") || "";
  const isLoggedIn = !!token && !!permissions;
  const path = to.path;

  // 白名单：直接放行
  if (path === "/mis/login" || path === "/front/index") {
    return true;
  }

  // MIS 端（除了登录页，其他都要登录）
  if (path.startsWith("/mis")) {
    if (isLoggedIn) {
      return true;
    } else {
      return { path: "/mis/login" };
    }
  }

  // 业务端：客户详情和商品快照需要登录（其他都不需要登录）
  if (
    path.startsWith("/front/customer") ||
    path.startsWith("/front/goods_snapshot")
  ) {
    if (isLoggedIn) {
      return true;
    } else {
      return { path: "/front/index" };
    }
  }

  // 其他页面：放行
  return true;
});

export default router;