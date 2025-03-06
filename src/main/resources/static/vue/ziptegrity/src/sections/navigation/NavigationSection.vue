<script setup>
import {computed, nextTick, onMounted, ref, watch, watchPostEffect} from "vue";
import {useRoute} from "vue-router";

const route = useRoute();
let cursorRecord = null;

const moveCursor = (target, wait=false) => {
  const cursor = document.querySelector(".navigation__cursor");
  const move = () => {
    const parentRect = document.querySelector(".navigation").getBoundingClientRect();
    const targetRect = target.getBoundingClientRect();
    const offsetY = 24;
    cursorRecord = target;
    cursor.style.top = `${targetRect.top - parentRect.top + offsetY}px`;
    cursor.style.left = `${(targetRect.left + (targetRect.width / 2) - (cursor.getBoundingClientRect().width / 2)) - parentRect.left}px`;
  }
  if(!wait) {
    move();
    return;
  }
  cursor.addEventListener('transitionend', () => move());
}

const moveCursorOnClick = (e) => {
  moveCursor(e.target);
}

watch(() => route.name,name => {
  nextTick(() => {
    const e = document.querySelector(`#${name}`);
    if (e) {
      moveCursor(e, true);
    }
  });
});

window.addEventListener("resize", () => moveCursor(cursorRecord));
</script>

<template>
  <link rel="stylesheet" href="/css/sections/navigation.css"/>
  <div class="navigation">
    <div class="navigation__left side">
      <router-link id="Calculator" to="/services/calculator" class="navigation__router-button"
                   @click="moveCursorOnClick">Калькулятор
      </router-link>
      <router-link id="Weather" to="/services/weather" class="navigation__router-button" @click="moveCursorOnClick">
        Погода
      </router-link>
      <router-link id="Chat" to="/services/chat" class="navigation__router-button" @click="moveCursorOnClick">Чат
      </router-link>
      <router-link id="Currency-Converter" to="/services/currency_converter" class="navigation__router-button"
                   @click="moveCursorOnClick">Конвертатор валют
      </router-link>
    </div>
    <div class="navigation__right side">
      <router-link id="Main" to="/main" class="navigation__router-button" @click="moveCursorOnClick">Главная
      </router-link>
    </div>
    <div class="navigation__cursor"></div>
  </div>
</template>

<style scoped>

</style>