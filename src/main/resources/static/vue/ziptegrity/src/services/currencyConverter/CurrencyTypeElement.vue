<script setup>
defineProps({
  currencyType: String,
  onChange: Function,
  from: Boolean
});
</script>

<template>
  <input type="button" :value="currencyType" :id="(from ? 'from' : 'to') + '_'+currencyType" class="currency-converter__currency-type"
         @click="onChange"/>
</template>

<style scoped>

</style>