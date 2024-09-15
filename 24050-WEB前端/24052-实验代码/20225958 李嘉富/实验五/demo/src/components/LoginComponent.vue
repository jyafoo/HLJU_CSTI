<template>
  <div class="login-container">
    <h1 class="login-title">Login Page</h1>
    <form @submit.prevent="login" class="login-form">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" required class="form-control">
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required class="form-control">
      </div>
      <button type="submit" class="login-btn">Login</button>
    </form>
  </div>
</template>

<script>
import Cookies from 'js-cookie';



export default {
  data() {
    return {
      username: '',
      password: ''
    };
  },

  methods: {
    login() {
      const user = { username: this.username, password: this.password };
      console.log(user);
      // 设置 cookie
      Cookies.set('username', this.username, { path: '/' });
      alert('Login successful!');
      this.$router.push('/home');
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f5f5f5;
  padding: 20px;
}

.login-title {
  color: #333;
  font-size: 24px;
  margin-bottom: 20px;
}

.login-form {
  display: flex;
  flex-direction: column;
  width: 300px;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #666;
}

.form-control {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

.login-btn {
  padding: 10px 20px;
  background-color: #42b983;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-btn:hover {
  background-color: #36a06e;
}
</style>