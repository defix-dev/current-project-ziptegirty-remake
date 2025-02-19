<script setup>
import {searchStore} from "@/searchStore.js";
</script>

<template>
      <span class="tooltip__title">Поиск сообщения</span>
      <input v-model="searchStore.searchText" type="text" placeholder="Ключевое слово(-ва)"
             class="tooltip__search-input"/>
</template>

<style scoped>

</style>