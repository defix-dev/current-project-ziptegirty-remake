<script setup>
import {nextTick, ref, watch} from "vue";

const currencies = Object.freeze({
  USD: "USD",
  RUR: "RUR",
  EUR: "EUR",
  GBP: "GBP"
});

const fromValue = ref(0);
const toValue = ref(0);

const fromType = ref(currencies.RUR);
const toType = ref(currencies.USD);

const SELECTED_TYPE_TOKEN = "currency-converter__currency-type--selected";

const changeCurrencyFromType = (event) => fromType.value = event.target.getAttribute("value");
const changeCurrencyToType = (event) => toType.value = event.target.getAttribute("value");

const swapCurrenciesType = () => {
  const cachedFT = fromType.value;
  fromType.value = toType.value;
  toType.value = cachedFT;
};

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

function updateCurrencyType(type, from) {
  const newTypeId = `#${from ? "from" : "to"}_${type}`;
  const newType = document.querySelector(newTypeId);
  const oldType = document.querySelectorAll(`.${SELECTED_TYPE_TOKEN}`).item(from ? 0 : 1);
  newType.classList.add(SELECTED_TYPE_TOKEN);
  oldType.classList.remove(SELECTED_TYPE_TOKEN);
}

async function updateCurrencyData() {
  const currencyData = {
    fromValue: fromValue.value,
    fromType: fromType.value,
    toType: toType.value
  };
  await fetch(`/api/v1/currency_converter?fromValue=${currencyData.fromValue}&fromType=${currencyData.fromType}&toType=${currencyData.toType}`)
      .then(res =>
    res.text().then(value => {
      toValue.value = Number.parseFloat(value);
    }));
  if(isNaN(toValue.value)) { toValue.value = fromValue.value; }
}

const pathToSwitchIcon = "/images/switch_icon.png";
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
        <div class="currency-converter__currency-container">
          <input type="button" :value="currencies.RUR" :id="'from_'+currencies.RUR" class="currency-converter__currency-type currency-converter__currency-type--selected"
                 @click="changeCurrencyFromType"/>
          <input type="button" :value="currencies.USD" :id="'from_'+currencies.USD" class="currency-converter__currency-type"
                 @click="changeCurrencyFromType"/>
          <input type="button" :value="currencies.EUR" :id="'from_'+currencies.EUR" class="currency-converter__currency-type"
                 @click="changeCurrencyFromType"/>
          <input type="button" :value="currencies.GBP" :id="'from_'+currencies.GBP" class="currency-converter__currency-type"
                 @click="changeCurrencyFromType"/>
        </div>
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
        <div class="currency-converter__currency-container">
          <input type="button" :value="currencies.RUR" :id="'to_'+currencies.RUR" class="currency-converter__currency-type"
                 @click="changeCurrencyToType"/>
          <input type="button" :value="currencies.USD" :id="'to_'+currencies.USD" class="currency-converter__currency-type currency-converter__currency-type--selected"
                 @click="changeCurrencyToType"/>
          <input type="button" :value="currencies.EUR" :id="'to_'+currencies.EUR" class="currency-converter__currency-type"
                 @click="changeCurrencyToType"/>
          <input type="button" :value="currencies.GBP" :id="'to_'+currencies.GBP" class="currency-converter__currency-type"
                 @click="changeCurrencyToType"/>
        </div>
        <input type="number" :value="toValue" class="currency-converter__value" readonly />
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>