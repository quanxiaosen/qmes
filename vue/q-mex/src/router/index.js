import Vue from 'vue'
import Router from 'vue-router'
import Dashboard from '@/views/Dashboard.vue'
import UserManagement from '@/views/UserManagement.vue'
import RoleManagement from "@/views/RoleManagemen.vue"
import Login from "@/views/Login.vue"
import WorkOrderPrint from "@/views/workOrderPrint"
import LabelPrint from "@/views/labelPrint";
import DepartmentManagement from "@/views/DepartmentManagement";

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Dashboard',
      component: Dashboard,
      meta: { requiresAuth: true },
    },
    {
      path: '/user-management',
      name: 'UserManagement',
      component: UserManagement,
      meta: { requiresAuth: true },  // 该页面需要认证
    },
    {
      path: '/role-management',
      name: 'RoleManagement',
      component: RoleManagement,
      meta: { requiresAuth: true },  // 该页面需要认证
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path:'/labelPrint',
      name:'LabelPrint',
      component:LabelPrint,
    },
    {
      path:'/workOrderPrint',
      name:'workOrderPrint',
      component: WorkOrderPrint,
   //   meta: { requiresAuth: true },
    },{
      path:'/departmentManagement',
      name:'departmentManagement',
      component:DepartmentManagement,
    },
    {
      path: "*",
      redirect: "/login",  // 默认跳转到登录页面
    }
  ]
})

// 添加全局路由守卫
router.beforeEach((to, from, next) => {
  // 检查是否需要登录权限
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 如果用户未登录
    if (!localStorage.getItem("isLoggedIn")) {
      // 跳转到登录页面
      next({ name: "Login" });
    } else {
      // 如果已经登录，继续导航
      next();
    }
  } else {
    // 如果不需要登录权限，直接放行
    next();
  }
});

export default router;
