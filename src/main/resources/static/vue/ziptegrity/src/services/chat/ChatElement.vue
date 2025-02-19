<script setup>
defineProps({
  username: String,
  message: String,
  date: Date
});
</script>

<template>
  <div class="chat">
    <span class="chat__username chat__text">{{username}}</span>
    <span class="chat__message chat__text">{{message}}</span>
    <span class="chat__date chat__text">{{date}}</span>
  </div>
</template>

<style scoped>

</style>