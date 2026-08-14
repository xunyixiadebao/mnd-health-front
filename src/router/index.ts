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

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");
  // 可能调用permissions的split方法，所以添加 || ''
  const permissions = localStorage.getItem("permissions") || "";
  // !! 在JS中可以将非布尔值转换为布尔值，等同于Boolean()
  // !! 在这里不写也行。写上更明确结果是布尔值。
  const isLoggedIn = !!token && !!permissions;
  const path = to.path;

  // 白名单：直接放行
  if (path === "/mis/login" || path === "/front/index") {
    return next();
  }

  // MIS 端（除了登录页，其他都要登录）
  if (path.startsWith("/mis")) {
    return isLoggedIn ? next() : next({ path: "/mis/login" });
  }

  // 业务端：客户详情和商品快照需要登录（其他都不需要登录）
  if (
    path.startsWith("/front/customer") ||
    path.startsWith("/front/goods_snapshot")
  ) {
    return isLoggedIn ? next() : next({ path: "/front/index" });
  }

  // 其他页面：放行
  return next();
});

export default router;