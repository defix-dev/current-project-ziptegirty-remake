<script setup>
import {popupStore} from "@/popupStore.js";
import {ref} from "vue";
import {useRouter} from "vue-router";
const countryName = ref("");
const router = useRouter();

function onSubmitCountryName() {
  router.push(`/services/weather/${countryName.value}`).then(() => window.location.reload());
}
</script>

<template>
  <link rel="stylesheet" href="/css/sections/entryCountryNamePopup.css" />
  <div class="country-popup">
    <div class="country-popup__header">
      <span class="country-popup__title">Ввод города</span>
    </div>
    <form class="country-popup__layout" @submit.prevent="onSubmitCountryName">
      <input type="text" placeholder="Введите город..." class="country-popup__input" v-model="countryName" required/>
      <input type="submit" value="Узнать погоду" class="country-popup__button"/>
    </form>
  </div>
</template>

<style scoped>

</style>