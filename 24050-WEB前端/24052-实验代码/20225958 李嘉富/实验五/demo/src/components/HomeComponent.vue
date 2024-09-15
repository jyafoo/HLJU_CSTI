<template>
  <div class="home-container">
    <h1 class="home-title">Home Page</h1>
    <p class="home-text">Welcome to the home page!</p>
  </div>
</template>

<script>
import Cookies from 'js-cookie';

export default {
  name: 'HomeComponent',
  created() {
    // 检查是否已登录，如果未登录则跳转到注册页面
    const username = Cookies.get('username');
    if (!username) {
      this.$router.push('/register');
    }
  },mounted() {
    this.$router.push('/register');
  }
};
</script>

<script>
export default {
  name: 'HomeComponent'
};
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f0f0f0;
  padding: 20px;
  text-align: center;
}

.home-title {
  color: #42b983;
  font-size: 36px;
  margin-bottom: 10px;
}

.home-text {
  color: #666;
  font-size: 18px;
  line-height: 1.6;
}
</style>


