<template>
  <div class="register-container">
    <h1 class="register-title">Register Page</h1>
    <form @submit.prevent="RegisterComponent" class="register-form">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" required class="form-control">
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="email" required class="form-control">
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" required class="form-control">
      </div>
      <button @click="register" class="register-btn">Register</button>
    </form>
  </div>
</template>

<script>

export default {
  name: 'RegisterComponent',
  data() {
    return {
      username: '',
      email: '',
      password: ''
    };
  },
  methods: {
    register() {
      // const newUser = { username: this.username, email: this.email, password: this.password };
      // console.log(newUser);
      // // Here you can implement registration logic, e.g., API calls
      //
      // // Set cookie or perform other necessary actions upon successful registration
      // Cookies.set('username', this.username, { path: '/' });

      alert('Registration successful!');
      this.$router.push('/home');
    }
  }
};
</script>

<style scoped>
.register-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f5f5f5;
  padding: 20px;
}

.register-title {
  color: #42b983;
  font-size: 24px;
  margin-bottom: 20px;
}

.register-form {
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

.register-btn {
  padding: 10px 20px;
  background-color: #42b983;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.register-btn:hover {
  background-color: #36a06e;
}
</style>