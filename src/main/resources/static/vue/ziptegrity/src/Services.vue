<template>
  <HeaderSection></HeaderSection>
  <NavigationSection></NavigationSection>
  <div
      id="view-holder">
    <router-view></router-view>
  </div>
  <div id="popup-holder" v-if="popupStore.isVisible && popupStore.component">
    <Popup/>
  </div>
  <div id="tooltip-holder" v-if="tooltipStore.isVisible && tooltipStore.component" @click="tooltipStore.hide()">
    <SimplePopup/>
  </div>
</template>

<script setup>
import HeaderSection from "@/sections/header/HeaderSection.vue";
import NavigationSection from "@/sections/navigation/NavigationSection.vue";
import Popup from "@/Popup.vue";
import {popupStore} from "@/popupStore.js";
import {tooltipStore} from "@/tooltipStore.js";
import SimplePopup from "@/Tooltip.vue";
</script>
