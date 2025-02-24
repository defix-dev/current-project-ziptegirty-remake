<script setup>
import {tooltipStore} from "@/tooltipStore.js";
import {onMounted} from "vue";

function movePopup() {
  const tooltip = document.querySelector(".tooltip");
  const tooltipRect = tooltip.getBoundingClientRect();
  const cursor = document.querySelector(".tooltip__cursor");
  const cursorRect = cursor.getBoundingClientRect();
  const rect = tooltipStore.targetElement.getBoundingClientRect();
  const gapY = 20;
  const offsetX = 5;

  let xCalcPos = rect.x - (tooltipRect.width / 2) + (rect.width / 2);
  if(xCalcPos + tooltipRect.width > window.innerWidth) {
    xCalcPos = window.innerWidth - tooltipRect.width - offsetX;
  }

  tooltip.style.left = `${xCalcPos}px`;
  tooltip.style.top = `${rect.y + rect.height + gapY}px`;
  cursor.style.left = `${rect.x - tooltip.getBoundingClientRect().x + rect.width/2 - cursorRect.width/2}px`;
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