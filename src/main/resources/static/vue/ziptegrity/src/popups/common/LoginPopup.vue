<script setup>
import {popupStore} from "@/popupStore.js";
import {ref} from "vue";
const loginData = ref({
  username: "",
  password: ""
});
const error = ref(false);
const submit = () => {
  fetch("/api/v1/authentication/login", {
    method: "POST",
    body: JSON.stringify(loginData.value),
    headers: { "Content-Type": "application/json" }
  }).then(res => { if(res.status === 200) { window.location.reload(); } else { error.value = true; } }).catch(_ => error.value = true);
}
</script>

<template>
  <link rel="stylesheet" href="/css/sections/authPopup.css" />
  <div class="auth-popup">
    <div class="auth-popup__header">
      <span class="auth-popup__title">Авторизация</span>
      <a class="auth-popup__close" @click="popupStore.hide"></a>
    </div>
    <span class="auth-popup__error" v-if="error">При авторизации произошла ошибка, пожалуйста проверьте корректность введенных данных и попробуйте еще раз.</span>
    <form @submit.prevent="submit" class="auth-popup__layout">
      <div class="auth-popup__inner-layout">
        <label for="username" class="auth-popup__text">Логин</label>
        <input v-model="loginData.username" type="text" placeholder="Введите логин..." name="username" class="auth-popup__input" id="username" required/>
      </div>
      <div class="auth-popup__inner-layout">
        <label for="password" class="auth-popup__text">Пароль</label>
        <input v-model="loginData.password" type="password" placeholder="Введите пароль..." name="password" class="auth-popup__input" id="password" required/>
      </div>
      <input type="submit" class="auth-popup__button" value="Войти"/>
    </form>
  </div>
</template>

<style scoped>

</style>