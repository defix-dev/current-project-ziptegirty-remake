import {createApp} from "vue";
import Services from "./Services.vue";
import router from "./router.js";

createApp(Services).use(router).mount("#services");