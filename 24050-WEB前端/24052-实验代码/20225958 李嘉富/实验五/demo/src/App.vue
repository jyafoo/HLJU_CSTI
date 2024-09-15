<!-- src/App.vue-->
<!--<template>-->
<!--  <div id="app">-->
<!--    <nav>-->

<!--    </nav>-->
<!--    <router-view></router-view>-->
<!--  </div>-->
<!--</template>-->

<!--<style>-->
<!--nav a {-->
<!--  margin: 0 10px;-->
<!--  text-decoration: none;-->
<!--  color: #42b983;-->
<!--}-->

<!--nav a.router-link-exact-active {-->
<!--  font-weight: bold;-->
<!--  color: #000;-->
<!--}-->
<!--</style>-->
<template>
  <div id="app">
    <nav>
      <router-link to="/home">Home</router-link>
      <router-link to="/login">Login</router-link>
      <router-link to="/">Register</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 16px;
  background-color: #42b983;
}

nav a {
  font-weight: bold;
  color: #fff;
  text-decoration: none;
  margin: 0 15px;
}

nav a.router-link-exact-active {
  text-decoration: underline;
}
</style>