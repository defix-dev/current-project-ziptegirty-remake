<script setup>

import {popupStore} from "@/popupStore.js";
import LoginPopup from "@/popups/common/LoginPopup.vue";
import {ref} from "vue";
import {authorizationStore} from "@/authorizationStore.js";
</script>

<template>
  <link rel="stylesheet" href="/css/pages/main.css"/>
  <div class="main">
    <span class="main__title">Добро пожаловать!</span>
    <span class="main__tip" v-if="authorizationStore.isAuthorized">Для выбора сервиса, воспользуйтесь панелью выше</span>
    <span class="main__error" v-else>Вы не авторизированы! Для работы веб-приложения необходимо <a class="main__login" href="#" @click="popupStore.show(LoginPopup)">пройти авторизацию</a></span>
  </div>
</template>

<style scoped>

</style>