<script setup>
import MessageElement from "@/services/chat/MessageElement.vue";

defineProps({
  messages: Array
});
</script>

<template>
  <div class="chat-service__messages-container">
    <MessageElement v-for="(item, index) in messages" :key="index"
                    :message="item.message"
                    :date="item.date"
                    :is-user="item.isUser"/>
  </div>
</template>

<style scoped>

</style>