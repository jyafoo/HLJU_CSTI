import Vue from 'vue';
import Router from 'vue-router';
import HomeComponent from '@/components/HomeComponent.vue';
import LoginComponent from '@/components/LoginComponent.vue';
import RegisterComponent from '@/components/RegisterComponent.vue'; // 新添加的注册组件

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Register',
            component: RegisterComponent
        },
        {
            path: '/home',
            name: 'Home',
            component: HomeComponent
        },
        {
            path: '/login',
            name: 'Login',
            component: LoginComponent
        },
        {
            path: '/register',  // 注册页面的路由
            name: 'Register',
            component: RegisterComponent
        }
    ]
});