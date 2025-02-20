<script setup>
import WeatherForecastFeatureContainer from "@/services/weather/containers/WeatherForecastFeatureContainer.vue";
defineProps({
  forecastFeatures: Array,
  date: String
});
</script>

<template>
  <div class="forecast">
    <div class="forecast__left">
      <span class="forecast__date">{{date}}</span>
    </div>
    <WeatherForecastFeatureContainer :forecast-features="forecastFeatures"/>
  </div>
</template>

<style scoped>

</style>