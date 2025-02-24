import { createRouter, createWebHistory } from 'vue-router';
import CalculatorService from "@/services/calculator/CalculatorService.vue";
import WeatherService from "@/services/weather/WeatherService.vue";
import ChatService from "@/services/chat/ChatService.vue";
import CurrencyConverterService from "@/services/currencyConverter/CurrencyConverterService.vue";
import Main from "@/Main.vue";

const routes = [
    { name: "Calculator", path: "/services/calculator", component: CalculatorService },
    { name: "Weather", path: "/services/weather/:name?", component: WeatherService },
    { name: "Chat", path: "/services/chat/:id?", component: ChatService },
    { name: "Currency-Converter", path: "/services/currency_converter", component: CurrencyConverterService },
    { name: "Main", path: "/main", component: Main }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

let first = true;

router.beforeEach(to => {
    fetch("/api/v1/authentication/authorized").then(res => res.text().then(result => {
        if (result === "false" || to.name === undefined) {
            router.push('/main');
        }
    }));
    if(!first) {
        document.querySelector("#view-holder").style.filter = "blur(100px)";
    } else { first = false; }
});

router.afterEach(() => {
    setTimeout(() => document.querySelector("#view-holder").style.filter = "blur(0)", 250);
});

export default router;