<script setup>
import {tooltipStore} from "@/tooltipStore.js";
import {onMounted} from "vue";

function movePopup() {
  const tooltip = document.querySelector(".tooltip");
  const tooltipRect = tooltip.getBoundingClientRect();
  const rect = tooltipStore.targetElement.getBoundingClientRect();
  const gapY = 20;
  tooltip.style.left = `${rect.x - (tooltipRect.width / 2) + (rect.width / 2)}px`;
  tooltip.style.top = `${rect.y + rect.height + gapY}px`;
}

onMounted(() => {
  movePopup();
  window.addEventListener("resize", () => movePopup());
});
</script>

<template>
  <div class="tooltip" @click.stop>
    <div class="tooltip__cursor"></div>
    <div class="tooltip__body">
      <component :is="tooltipStore.component" v-bind="tooltipStore.props"/>
    </div>
  </div>
</template>

<style scoped>

</style>