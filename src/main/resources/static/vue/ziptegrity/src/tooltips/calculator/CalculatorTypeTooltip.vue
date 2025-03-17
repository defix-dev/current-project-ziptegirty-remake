<script setup>
import {CalculatorType} from "@/services/calculator/CalculatorType.js";

defineProps({
  onChangeType: Function
});
</script>

<template>
<span class="tooltip__title">Список доступных калькуляторов</span>
<span class="tooltip__button" @click="onChangeType(CalculatorType.SIMPLE)">Простой</span>
<span class="tooltip__button" @click="onChangeType(CalculatorType.PROGRAMMER)">Программист</span>
<span class="tooltip__button" @click="onChangeType(CalculatorType.COMBINED)">Общий</span>
</template>

<style scoped>

</style>