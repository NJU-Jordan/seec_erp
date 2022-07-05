import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import "@/assets/css/normalize.css";
import "@/assets/css/base.css";
import "@/plugins/elementui";

/*
import axios from 'axios'
axios.defaults.baseURL = '/api'
Vue.prototype.$http = axios;*/

Vue.config.productionTip = false;
Vue.prototype.$bus = new Vue(); //用于取代Vuex实现组件之间的消息传递

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
