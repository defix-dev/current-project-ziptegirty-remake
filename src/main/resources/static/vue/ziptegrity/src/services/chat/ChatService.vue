<script setup>
import {onMounted, ref, watch} from "vue";
import ChatElementContainer from "@/services/chat/ChatElementContainer.vue";
import MessageElementContainer from "@/services/chat/MessageElementContainer.vue";
import {Stomp} from "@stomp/stompjs";
import SockJS from 'sockjs-client';
import {useRoute, useRouter} from "vue-router";
import {tooltipStore} from "@/tooltipStore.js";
import {searchStore} from "@/searchStore.js";
import SearchTooltip from "@/simplePopups/chat/SearchTooltip.vue";

const pathToSearchIcon = "/images/search_chat_icon.png";
const pathToAddIcon = "/images/add_chat_icon.png";
const pathToSendIcon = "/images/send_message_icon.png";

const showMessages = ref(false);
const showChats = ref(false);

const chats = ref([]);
const messages = ref([]);

const userInfo = ref(null);
const chatInfo = ref(null);

const userMessage = ref("");
const stompClient = ref(null);

const searchedChats = ref([]);

async function loadUserInfo() {
  await fetch("/api/user/get_user_info")
      .then(res => res.json().then(json => userInfo.value = {
        username: json.username,
        id: json.id
      }));
}

function loadChats() {
  fetch("/api/chat/get_chats").then(res => res.json()).then(
      json => {
        if (json.status !== undefined) return;
        chats.value = json.map(chat => ({
          username: chat.targetUsername,
          message: chat.lastMessage,
          date: chat.localDateTime,
          targetId: chat.targetId
        }));
        showChats.value = true;
      });
}

async function loadMessages(userId) {
  await loadUserInfo();
  fetch(`/api/chat/get_messages?targetUserId=${userId}`).then(res => res.json())
      .then(json => {
        if (json.status !== undefined) { messages.value = []; return; }
        messages.value = json.map(message => ({
          message: message.message,
          date: message.createdAt,
          isUser: (message.senderId === userInfo.value.id)
        }));
      });
}

async function openChat(userId) {
  await loadMessages(userId);
  fetch(`/api/chat/get_chat_info?targetUserId=${userId}`).then(res => res.json())
      .then(json => {
        chatInfo.value = {
          targetUsername: json.targetUsername,
          targetId: json.targetId
        };
        showMessages.value = true;
      });
}

const route = useRoute();
const id = route.params.id;

if (id !== "") {
  openChat(Number.parseInt(id));
}

const router = useRouter();

function changeChat(targetId) {
  if(chatInfo.value != null && chatInfo.value.targetId === targetId) return;
  router.push(`/services/chat/${targetId}`).then(
      _ => openChat(targetId)
  );
}

async function initStomp() {
  await loadUserInfo();
  const socket = new SockJS('http://localhost:3030/ws');
  stompClient.value = Stomp.over(socket);
  stompClient.value.connect({}, () => {
    stompClient.value.subscribe(`/chat/listen/${userInfo.value.id}`, (messageObj) => {
      const body = JSON.parse(messageObj.body);
      applyMessage({
        message: body.message,
        date: body.createdAt,
        fromId: body.senderId
      });
    });
  });
}

function sendMessage(userId) {
  if (stompClient.value === null || userMessage.value === "") return;
  stompClient.value.send(
      `/services/chat/${userId}`
      , {}, userMessage.value
  );
}

function applyMessage(msgData) {
  if (msgData.fromId === userInfo.value.id || msgData.fromId === chatInfo.value.targetId) {
    messages.value.push({
      message: msgData.message,
      date: msgData.date,
      isUser: userInfo.value.id === msgData.fromId
    });
  }
  if(msgData.fromId === userInfo.value.id) userMessage.value = "";
  loadChats();
}

watch(() => searchStore.searchText, (value) => {
  searchedChats.value = chats.value.filter(chat => chat.message.startsWith(value) || chat.username.startsWith(value));
});

onMounted(() => {
      initStomp();
      loadChats();
    }
);
</script>

<template>
  <link rel="stylesheet" href="/css/sections/searchTooltip.css"/>
  <link rel="stylesheet" href="/css/pages/chat.css"/>
  <div class="chat-service">
    <div class="chat-service__left side">
      <div class="chat-service__header">
        <span class="chat-service__title">Чаты</span>
        <div class="chat-service__header-button-container">
          <a class="chat-service__header-button" @click="(event) => tooltipStore.show(SearchTooltip, event.target)"><img :src="pathToSearchIcon" alt="SEARCH"/></a>
          <a href="#" class="chat-service__header-button"><img :src="pathToAddIcon" alt="ADD"/></a>
        </div>
      </div>
      <div class="chat-service__content">
        <span class="chat-service__mini-header" v-if="showChats">{{searchStore.searchText === '' ? chats.length : searchedChats.length}} чат(-ов)</span>
        <ChatElementContainer :chats="searchStore.searchText === '' ? chats : searchedChats" :openChatAction="changeChat" v-if="showChats"/>
        <div class="chat-service__info-layout" v-else>
          <span class="chat-service__info">У вас пока нету чатов =(</span>
        </div>
      </div>
    </div>
    <div class="chat-service__right side">
      <div class="chat-service__header chat-service__header--right">
        <span class="chat-service__title">{{ chatInfo !== null ? chatInfo.targetUsername : "Переписка" }}</span>
      </div>
      <div class="chat-service__messages-layout" v-if="showMessages">
        <MessageElementContainer :messages="messages" v-if="showMessages"/>
        <div class="chat-service__message-sender-layout">
          <input v-model="userMessage" :value="userMessage" type="text" placeholder="Введите сообщение..."
                 class="chat-service__message-input" required/>
          <a class="chat-service__message-send" @click="sendMessage(chatInfo.targetId)">
            <img :src="pathToSendIcon" alt="SEND"/>
          </a>
        </div>
      </div>
      <div class="chat-service__content" v-else>
        <div class="chat-service__info-layout">
          <span class="chat-service__info">Выберите чат в блоке «Чаты», чтобы увидеть переписку</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>