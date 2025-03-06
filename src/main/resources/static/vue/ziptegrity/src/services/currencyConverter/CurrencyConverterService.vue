<script setup>
import {nextTick, onMounted, ref, watch} from "vue";
import CurrencyTypeContainer from "@/services/currencyConverter/CurrencyTypeContainer.vue";

const currencies = Object.freeze({
  USD: "USD",
  RUR: "RUR",
  EUR: "EUR",
  GBP: "GBP"
});

const currenciesSupported = [
    currencies.RUR,
    currencies.USD,
    currencies.EUR,
    currencies.GBP
]

const fromValue = ref(1);
const toValue = ref(0);
const fromType = ref(currencies.USD);
const toType = ref(currencies.RUR);

const SELECTED_TYPE_TOKEN = "currency-converter__currency-type--selected";
const pathToSwitchIcon = "/images/switch_icon.png";

const changeCurrencyFromType = (event) => fromType.value = event.target.getAttribute("value");
const changeCurrencyToType = (event) => toType.value = event.target.getAttribute("value");

const swapCurrenciesType = () => {
  const cachedFT = fromType.value;
  fromType.value = toType.value;
  toType.value = cachedFT;
};

function updateCurrencyType(type, from) {
  const newTypeId = `#${from ? "from" : "to"}_${type}`;
  const newType = document.querySelector(newTypeId);
  const oldType = document.querySelectorAll(`.${SELECTED_TYPE_TOKEN}`).item(from ? 0 : 1);
  newType.classList.add(SELECTED_TYPE_TOKEN);
  oldType?.classList.remove(SELECTED_TYPE_TOKEN);
}

const updateCurrencyData = async () => {
  await fetch(`/api/v1/currency_converter?fromValue=${fromValue.value}&fromType=${fromType.value}&toType=${toType.value}`)
      .then(res =>
    res.text().then(value => {
      toValue.value = Number.parseFloat(value);
    }));
  if(isNaN(toValue.value)) { toValue.value = fromValue.value; }
}

watch(fromValue, () => {
  updateCurrencyData();
});

watch(fromType, () => {
  updateCurrencyType(fromType.value, true);
  updateCurrencyData();
});

watch(toType, () => {
  updateCurrencyType(toType.value, false);
  updateCurrencyData();
});

onMounted(() => {
  updateCurrencyType(fromType.value, true);
  updateCurrencyType(toType.value, false);
  updateCurrencyData();
});
</script>

<template>
  <link rel="stylesheet" href="/css/pages/currencyConverter.css" />
  <div class="currency-converter">
    <div class="currency-converter__left side">
      <div class="currency-converter__header">
        <span class="currency-converter__title">У меня есть</span>
        <a href="#" class="currency-converter__header-button" @click="swapCurrenciesType"><img :src="pathToSwitchIcon" alt="SWITCH"/></a>
      </div>
      <div class="currency-converter__content">
        <CurrencyTypeContainer :from="true" :onChange="changeCurrencyFromType" :currencies="currenciesSupported" />
        <input type="number" v-model="fromValue" class="currency-converter__value" />
        <span class="currency-converter__mini-header">Дополнительные функции</span>
        <div class="currency-converter__button-container">
          <a href="#" class="currency-converter__button">Выбрать банк</a>
          <a href="#" class="currency-converter__button">Выбрать дату</a>
        </div>
      </div>
    </div>
    <div class="currency-converter__right side">
      <div class="currency-converter__header">
        <span class="currency-converter__title">Хочу приобрести</span>
      </div>
      <div class="currency-converter__content">
        <CurrencyTypeContainer :from="false" :onChange="changeCurrencyToType" :currencies="currenciesSupported" />
        <input type="number" :value="toValue" class="currency-converter__value" readonly />
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>