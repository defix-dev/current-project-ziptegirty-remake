<script setup>
import {ref} from "vue";
import {CryptoManager} from "@/services/chat/cryptoManager.js";

const codeWord = ref("");
const onApplyCodeWord = () => {
  CryptoManager.setCodeWord(codeWord.value);
  window.location.reload();
}
</script>

<template>
  <link rel="stylesheet" href="/css/sections/codeWordPopup.css"/>
  <div class="code-word-popup">
    <div class="code-word-popup__header">
      <span class="code-word-popup__title">Снятие шифрования</span>
    </div>
    <form class="code-word-popup__layout" @submit.prevent="onApplyCodeWord">
      <span class="code-word-popup__info">Текущий клиент потерял доступ к кодовому слову, пожалуйста введите его снова, чтобы снять шифрование сообщений.</span>
      <input type="text" placeholder="Введите кодовое слово..." class="code-word-popup__input" v-model="codeWord"
             required/>
      <input type="submit" value="Попробовать" class="code-word-popup__button"/>
    </form>
  </div>
</template>

<style scoped>

</style>