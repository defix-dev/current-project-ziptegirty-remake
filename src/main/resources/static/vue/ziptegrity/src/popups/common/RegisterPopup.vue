<script setup>
import {popupStore} from "@/popupStore.js";
import {ref} from "vue";
const registerData = ref({
  username: "",
  password: ""
});
const error = ref(false);
const first = ref(true);
const submit = () => {
  fetch("/api/v1/authentication/register", {
    method: "POST",
    body: JSON.stringify(registerData.value),
    headers: { "Content-Type": "application/json" }
  }).then(res => { first.value = false; error.value = res.status !== 204; }).catch(_ => {
    first.value = false;
    error.value = true;
  });
}
</script>

<template>
  <link rel="stylesheet" href="/css/sections/authPopup.css" />
  <div class="auth-popup">
    <div class="auth-popup__header">
      <span class="auth-popup__title">Регистрация</span>
      <a class="auth-popup__close" @click="popupStore.hide"></a>
    </div>
    <span class="auth-popup__error" v-if="error">При регистрации произошла ошибка, пожалуйста проверьте корректность введенных данных и попробуйте еще раз.</span>
    <span class="auth-popup__info" v-if="!error && !first">Аккаунт под логином "{{registerData.username}}" был успешно создан.</span>
    <form @submit.prevent="submit" class="auth-popup__layout">
      <div class="auth-popup__inner-layout">
        <label for="username" class="auth-popup__text">Логин</label>
        <input v-model="registerData.username" type="text" placeholder="Введите логин..." name="username" class="auth-popup__input" id="username" required/>
      </div>
      <div class="auth-popup__inner-layout">
        <label for="password" class="auth-popup__text">Пароль</label>
        <input v-model="registerData.password" type="password" placeholder="Введите пароль..." name="password" class="auth-popup__input" id="password" required/>
      </div>
      <input type="submit" class="auth-popup__button" value="Создать аккаунт"/>
    </form>
  </div>
</template>

<style scoped>

</style>