<script setup>
import {popupStore} from "@/popupStore.js";
import LoginPopup from "@/popups/common/LoginPopup.vue";
import RegisterPopup from "@/popups/common/RegisterPopup.vue";
import {ref} from "vue";
import {authorizationStore} from "@/authorizationStore.js";

const pathToLogo = "/images/logo.png";
const logout = () => {
  fetch("/api/v1/authentication/logout", {
    method: "POST"
  }).then(_ => window.location.reload());
}
</script>

<template>
  <link rel="stylesheet" href="/css/sections/header.css" />
  <div class="header">
    <div class="header__left">
      <img :src="pathToLogo" class="header__logo" alt="LOGO"/>
    </div>
    <div class="header__right">
      <a href="#" class="header__menu-button">Тарифный план</a>
      <a href="#" class="header__menu-button">Действия с API</a>
      <div class="header__unauthorized" v-if="!authorizationStore.isAuthorized">
        <a class="header__login-button" @click="popupStore.show(LoginPopup)">Войти</a>
        <a class="header__register-button" @click="popupStore.show(RegisterPopup)">Регистрация</a>
      </div>
      <div class="header__authorized" v-else>
        <a class="header__logout-button" @click="logout">Выйти</a>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>