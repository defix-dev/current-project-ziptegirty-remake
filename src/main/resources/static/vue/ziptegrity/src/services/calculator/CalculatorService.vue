<script setup>
import TokenContainer from "@/services/calculator/containers/TokenContainer.vue";
import {onMounted, ref} from "vue";
import {CalculatorType} from "@/services/calculator/CalculatorType.js";
import {ExpressionCalculator} from "@/services/calculator/ExpressionCalculator.js";
import {TokensLoader} from "@/services/calculator/TokensLoader.js";
import {popupStore} from "@/popupStore.js";
import ErrorPopup from "@/popups/common/ErrorPopup.vue";
import {tooltipStore} from "@/tooltipStore.js";
import CalculatorTypeTooltip from "@/tooltips/calculator/CalculatorTypeTooltip.vue";
import {useRoute, useRouter} from "vue-router";

const expression = ref("");
const answer = ref("");
const calcType = ref(CalculatorType.SIMPLE);
const tokens = ref([]);
const error = ref(false);
const router = useRouter();
const route = useRoute();

const loadTokens = async () => {
  const ts = await TokensLoader.load(calcType.value);
  if(ts == null) {
    popupStore.show(ErrorPopup, {message:"Код: 500", onSubmit: () => window.location.reload()});
    return;
  }
  tokens.value = ts;
}
const calculateExpression = async () => {
  error.value = false;
  const ar = await ExpressionCalculator.calculate(expression.value, calcType.value);
  if(ar == null) {
    error.value = true;
    return;
  }
  answer.value = ar;
}
const changeCalcType = (e) => {
  tooltipStore.show(CalculatorTypeTooltip, e.target, {onChangeType: (type) => {
      router.push(`/services/calculator/${type}`).then(() => window.location.reload());
    }})
}

onMounted(async () => {
  console.log(route.params.type);
  if(route.params.type !== undefined && route.params.type !== "") calcType.value = route.params.type;
  await loadTokens();
});
</script>

<template>
  <link rel="stylesheet" href="/css/pages/calculator.css"/>
  <div class="calculator-service">
    <div class="calculator-service__content">
      <div class="calculator-service__expression">
        <input v-model="expression" type="text" class="calculator-service__expression__input"
               placeholder="Введите выражение..."/>
        <div class="calculator-service__expression__button-container">
          <a class="calculator-service__expression__change-button" @click="changeCalcType">Сменить тип</a>
          <a class="calculator-service__expression__calculate-button"
             @click="calculateExpression">Посчитать</a>
        </div>
      </div>
      <span class="calculator-service__answer" v-if="answer !== '' && !error">{{ answer }}</span>
      <span class="calculator-service__error" v-if="error">Неверное выражение!</span>
      <div class="calculator-service__mini-header">Все доступные функции и операторы</div>
      <TokenContainer :tokens="tokens"/>
    </div>
  </div>
</template>

<style scoped>

</style>