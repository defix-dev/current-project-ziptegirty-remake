<script setup>
import WeatherForecastFeatureElement from "@/services/weather/elements/WeatherForecastFeatureElement.vue";

defineProps({
  forecastFeatures: Array
});
</script>

<template>
  <div class="forecast__right">
    <WeatherForecastFeatureElement v-for="(item, index) in forecastFeatures" :key="index"
                                   :pathToIcon="item.iconPath"
                                   :value="item.value"/>
  </div>
</template>

<style scoped>

</style>