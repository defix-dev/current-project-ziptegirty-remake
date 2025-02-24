<script setup>

import {popupStore} from "@/popupStore.js";
import LoginPopup from "@/popups/header/LoginPopup.vue";
import {ref} from "vue";
const authorized = ref(false);
fetch("/api/v1/authentication/authorized").then(
    res => res.text().then(result => authorized.value = result === "true")
);
</script>

<template>
  <link rel="stylesheet" href="/css/pages/main.css"/>
  <div class="main">
    <span class="main__title">Добро пожаловать!</span>
    <span class="main__tip" v-if="authorized">Для выбора сервиса, воспользуйтесь панелью выше</span>
    <span class="main__error" v-else>Вы не авторизированы! Для работы веб-приложения необходимо <a class="main__login" href="#" @click="popupStore.show(LoginPopup)">пройти авторизацию</a></span>
  </div>
</template>

<style scoped>

</style>