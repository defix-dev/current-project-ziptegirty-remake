<script setup>
import CurrencyTypeElement from "@/services/currencyConverter/CurrencyTypeElement.vue";

defineProps({
  currencies: Array,
  onChange: Function,
  from: Boolean
});
</script>

<template>
  <div class="currency-converter__currency-container">
    <CurrencyTypeElement v-for="(item, key) in currencies" :key="key"
                         :currencyType="item"
                         :onChange="onChange"
                         :from="from"/>
  </div>
</template>

<style scoped>

</style>