<script setup>
defineProps({
  time: String,
  state: Number,
  temperature: Number
});
</script>

<template>
  <tr>
    <td>{{time}}</td>
    <td>{{state}}</td>
    <td>{{temperature}}°C</td>
  </tr>
</template>

<style scoped>

</style>