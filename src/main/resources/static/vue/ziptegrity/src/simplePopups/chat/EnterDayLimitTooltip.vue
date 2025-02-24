<script setup>
import {searchStore} from "@/searchStore.js";
import {ref} from "vue";
import {tooltipStore} from "@/tooltipStore.js";
const dayLimitValue = ref(0);

defineProps({
  onChangeLimit: Function
});
</script>

<template>
  <span class="tooltip__title">Дневной лимит</span>
  <input v-model="dayLimitValue" type="number" placeholder="Введите дневной лимит..."
         class="tooltip__input"/>
  <a class="day-limit-tooltip__button" type="button" @click="() => {
    onChangeLimit(dayLimitValue);
    tooltipStore.hide();
  }">Установить лимит</a>
</template>

<style scoped>

</style>