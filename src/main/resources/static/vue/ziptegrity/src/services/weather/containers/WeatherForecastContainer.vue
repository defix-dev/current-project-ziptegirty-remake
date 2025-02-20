<script setup>
import WeatherForecastElement from "@/services/weather/elements/WeatherForecastElement.vue";

defineProps({
  forecasts: Array
});
</script>

<template>
  <div class="weather-service__forecast-container">
    <WeatherForecastElement v-for="(item, index) in forecasts" :key="index"
                            :forecast-features="item.forecastFeatures"
                            :date="item.date"/>
  </div>
</template>

<style scoped>

</style>