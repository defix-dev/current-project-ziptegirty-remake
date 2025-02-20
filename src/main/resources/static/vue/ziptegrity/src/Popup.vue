<script setup>
import {popupStore} from "@/popupStore.js";
</script>

<template>
  <component :is="popupStore.component" v-bind="popupStore.props"/>
</template>

<style scoped>

</style>