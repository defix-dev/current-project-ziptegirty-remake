<script setup>
import {popupStore} from "@/popupStore.js";
</script>

<template>
  <component :is="popupStore.component"/>
</template>

<style scoped>

</style>