<script setup>
import {onMounted, ref, watch} from "vue";
import ChatElementContainer from "@/services/chat/containers/ChatElementContainer.vue";
import MessageElementContainer from "@/services/chat/containers/MessageElementContainer.vue";
import {useRoute, useRouter} from "vue-router";
import {tooltipStore} from "@/tooltipStore.js";
import {searchStore} from "@/searchStore.js";
import SearchTooltip from "@/tooltips/chat/SearchTooltip.vue";
import {decodeFromBase64, decodeTextFromBase64} from "@/cryptUtils.js";
import {popupStore} from "@/popupStore.js";
import CodeWordCreationPopup from "@/popups/chat/CodeWordCreationPopup.vue";
import CodeWordValidationPopup from "@/popups/chat/CodeWordValidationPopup.vue";
import "./chatManager.js";
import "./messageManager.js";
import "./stompManager.js";
import "./userManager.js";
import "./cryptoManager.js";
import {MessageManager} from "@/services/chat/messageManager.js";
import {UserManager} from "@/services/chat/userManager.js";
import {ChatManager} from "@/services/chat/chatManager.js";
import {CryptoManager} from "@/services/chat/cryptoManager.js";
import {StompManager} from "@/services/chat/stompManager.js";
import ErrorPopup from "@/popups/common/ErrorPopup.vue";

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
const searchedChats = ref([]);
const router = useRouter();
const route = useRoute();

let secret;
let stompManager = new StompManager();

const openCodeWordPopup = (ls = false) => {
  popupStore.show(ls ? CodeWordValidationPopup : CodeWordCreationPopup);
}

const changeChat = (targetId) => {
  if (chatInfo.value != null && chatInfo.value.targetId === targetId) return;
  router.push(`/services/chat/${targetId}`).then(
      _ => window.location.reload()
  );
}

const applyMessage = (messageBody) => {
  if (messageBody.fromId === userInfo.value.id || messageBody.fromId === chatInfo.value.targetId) {
    messages.value.push({
      message: messageBody.message,
      date: messageBody.date,
      isUser: userInfo.value.id === messageBody.fromId
    });
  }
  if (messageBody.fromId === userInfo.value.id) userMessage.value = "";
}

watch(() => searchStore.searchText, (value) => {
  searchedChats.value = chats.value.filter(chat => chat.message.startsWith(value) || chat.username.startsWith(value));
});

onMounted(async () => {
      userInfo.value = await UserManager.getUserMetadata();
      if (userInfo.value == null) return;

      const rawPrivateKey = await CryptoManager.getPrivateKey();
      if (rawPrivateKey == null) {
        openCodeWordPopup(false);
        return;
      }

      const codeWord = CryptoManager.getCodeWord();
      if (codeWord === null) {
        openCodeWordPopup(true);
        return;
      }

      const id = route.params.id;
      if (id !== undefined && id !== "") {
        try {
          chatInfo.value = await ChatManager.getChatMetadata(id);
          secret = await CryptoManager.getDerivedSharedSecret(rawPrivateKey,
              await CryptoManager.getPublicKey(chatInfo.value.targetId),
              codeWord);
          messages.value = await MessageManager.getMessages(userInfo.value.id, id, secret);
          if (messages.value == null) messages.value = [];
          showMessages.value = true;
        } catch(e) {
          popupStore.show(ErrorPopup, {message:"Во время расшифровки возникла ошибка: "+e, onSubmit:() => window.location.reload()});
          return;
        }
      }

      const updateChats = async () => {
        chats.value = await ChatManager.getUserChats(async (id) => await CryptoManager.getDerivedSharedSecret(
            rawPrivateKey, await CryptoManager.getPublicKey(id), codeWord
        ));
        if (chats.value !== null) showChats.value = true;
      }

      stompManager.initialize();
      stompManager.subscribeChatListen(userInfo.value.id, async (message) => {
            applyMessage({
              message: await CryptoManager.decryptTextBySharedSecret(message.message, secret),
              date: message.createdAt,
              fromId: message.senderId
            });
            await updateChats();
          }
      );

      await updateChats();
    }
);
</script>

<template>
  <link rel="stylesheet" href="/css/sections/searchTooltip.css"/>
  <link rel="stylesheet" href="/css/pages/chat.css"/>
  <div class="chat-service">
    <div class="chat-service__left side">
      <div class="chat-service__header">
        <span class="chat-service__title" @click="popupStore.show(CodeWordValidationPopup)">Чаты</span>
        <div class="chat-service__header-button-container">
          <a class="chat-service__header-button" @click="(event) => tooltipStore.show(SearchTooltip, event.target)"><img
              :src="pathToSearchIcon" alt="SEARCH"/></a>
          <a href="#" class="chat-service__header-button"><img :src="pathToAddIcon" alt="ADD"/></a>
        </div>
      </div>
      <div class="chat-service__content">
        <span class="chat-service__mini-header"
              v-if="showChats">{{ searchStore.searchText === '' ? chats.length : searchedChats.length }} чат(-ов)</span>
        <ChatElementContainer :chats="searchStore.searchText === '' ? chats : searchedChats"
                              :openChatAction="changeChat" v-if="showChats"/>
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
          <a class="chat-service__message-send"
             @click="stompManager.sendMessage(chatInfo.targetId, userMessage, secret)">
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