<script setup>
defineProps({
  pathToIcon: String,
  value: String
})
</script>

<template>
  <div class="weather-feature">
    <img :src="pathToIcon" alt="WFEATURE_ICON" class="weather-feature__icon"/>
    <span class="weather-feature__value">{{value}}</span>
  </div>
</template>

<style scoped>

</style>