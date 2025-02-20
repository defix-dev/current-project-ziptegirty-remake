<script setup>
defineProps({
  pathToIcon: String,
  value: String
});
</script>

<template>
  <div class="forecast-feature">
    <img :src="pathToIcon" class="forecast-feature__icon" alt="FEATURE_ICON"/>
    <span class="forecast-feature__value">{{value}}</span>
  </div>
</template>

<style scoped>

</style>