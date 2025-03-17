<script setup>
import TokenElement from "@/services/calculator/elements/TokenElement.vue";

defineProps({
  tokens: Array
});
</script>

<template>
  <div class="calculator-service__token-container">
    <TokenElement v-for="(item, index) in tokens" :key="index"
                  :type="item.type"
                  :keyword="item.keyword"/>
  </div>
</template>

<style scoped>

</style>