<script setup>
defineProps({
  message: String,
  date: Date,
  isUser: Boolean
});
</script>

<template>
  <div class="user-message" v-if="isUser">
    <span class="user-message__text">{{message}}</span>
    <span class="user-message__date">{{date}}</span>
  </div>
  <div class="other-message" v-else>
    <span class="other-message__text">{{message}}</span>
    <span class="other-message__date">{{date}}</span>
  </div>
</template>

<style scoped>

</style>