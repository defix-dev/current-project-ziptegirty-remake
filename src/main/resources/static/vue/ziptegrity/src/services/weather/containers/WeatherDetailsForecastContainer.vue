<script setup>
import WeatherDetailForecastElement from "@/services/weather/elements/WeatherDetailForecastElement.vue";

defineProps({
  detailForecasts: Array
});
</script>

<template>
  <table class="weather-service__details-forecast">
    <tr>
      <th>Время</th>
      <th>Состояние</th>
      <th>Температура</th>
    </tr>
    <WeatherDetailForecastElement v-for="(item, index) in detailForecasts" :key="index"
                                  :state="item.state"
                                  :temperature="item.temperature"
                                  :time="item.time"/>
  </table>
</template>

<style scoped>

</style>