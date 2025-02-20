<script setup>
import WeatherFeatureElement from "@/services/weather/elements/WeatherFeatureElement.vue";

defineProps({
  features: Array
});
</script>

<template>
  <div class="weather-service__weather-feature-container">
    <WeatherFeatureElement v-for="(item, index) in features" :key="index"
                           :path-to-icon="item.iconPath"
                           :value="item.value"/>
  </div>
</template>

<style scoped>

</style>