<script setup>
defineProps({
  type: String,
  keyword: String
})
const pathToCopyIcon = "/images/copy_icon.png";
const pathToInfoIcon = "/images/info_icon.png";
const copyToClipboard = (value) => navigator.clipboard.writeText(value);
</script>

<template>
  <div class="calculator-service__token">
    <div class="calculator-service__token__details">
      <span class="calculator-service__token__type-detail">{{ type.toUpperCase() }}</span>
      <div class="calculator-service__token__value-detail" @click="copyToClipboard(keyword)">
        <a><img :src="pathToCopyIcon" alt="COPY" /></a>
        <span>{{keyword}}</span>
      </div>
    </div>
    <a class="calculator-service__token__info"><img :src="pathToInfoIcon" alt="INFO"></a>
  </div>
</template>

<style scoped>

</style>