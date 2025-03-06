<script setup>
import {popupStore} from "@/popupStore.js";

defineProps({
  onSubmit: Function,
  message: String
});
</script>

<template>
  <link rel="stylesheet" href="/css/sections/errorPopup.css" />
  <div class="error-popup">
    <div class="error-popup__header">
      <span class="error-popup__title">Произошла ошибка</span>
      <a class="error-popup__close" @click="() => { popupStore.hide(); onSubmit(); }"></a>
    </div>
    <div class="error-popup__layout">
      <span class="error-popup__error">{{ message }}</span>
      <a class="error-popup__button" @click="() => { popupStore.hide(); onSubmit(); }">OK</a>
    </div>
  </div>
</template>

<style scoped>

</style>