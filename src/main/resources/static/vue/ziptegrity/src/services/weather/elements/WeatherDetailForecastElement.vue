<script setup>
defineProps({
  time: String,
  state: String,
  temperature: Number
});
</script>

<template>
  <tr>
    <td>{{time.substring(0, time.lastIndexOf(":"))}}</td>
    <td>{{state}}</td>
    <td>{{temperature}}°C</td>
  </tr>
</template>

<style scoped>

</style>