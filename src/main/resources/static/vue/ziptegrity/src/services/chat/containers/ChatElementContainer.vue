<script setup>
import ChatElement from "@/services/chat/elements/ChatElement.vue";

defineProps({
  chats: Array,
  openChatAction: Function
});
</script>

<template>
  <div class="chat-service__chats-container">
    <ChatElement v-for="(item, index) in chats" :key="index"
                 :username="item.username"
                 :message="item.message"
                 :date="item.date" @click="openChatAction(item.targetId)"/>
  </div>
</template>

<style scoped>

</style>