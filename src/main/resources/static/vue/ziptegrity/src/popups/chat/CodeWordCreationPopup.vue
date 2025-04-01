<script setup>
import {popupStore} from "@/popupStore.js";
import {ref} from "vue";
import {decodeFromBase64, encryptValueByCodeWord, generateECDHKeyPair} from "@/cryptUtils.js";

const codeWord = ref("");
const onSubmitCodeWord = async () => {
  const keys = await generateECDHKeyPair();
  keys.privateKey = await encryptValueByCodeWord(codeWord.value, keys.privateKey);
  await fetch(`/api/v1/encryption/users/keys`, {
    method: "POST",
    body: JSON.stringify(keys),
    headers: {
      "Content-Type": "application/json"
    }
  });
  localStorage.setItem("codeWord", codeWord.value);
  window.location.reload();
}
</script>

<template>
  <div class="code-word-popup">
    <div class="code-word-popup__header">
      <span class="code-word-popup__title">Создание кодового слова</span>
    </div>
    <form class="code-word-popup__layout" @submit.prevent="onSubmitCodeWord">
      <span class="code-word-popup__info">Внимание! Данное слово будет использоваться для шифрования ваших сообщений, в случае его потери данные к сообщениям будут утеряны навсегда.</span>
      <input type="text" placeholder="Введите кодовое слово..." class="code-word-popup__input" v-model="codeWord" required/>
      <input type="submit" value="Создать" class="code-word-popup__button"/>
    </form>
  </div>
</template>

<style scoped>

</style>