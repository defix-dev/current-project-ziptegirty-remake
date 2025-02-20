<script setup>
import {onMounted, ref} from "vue";
import WeatherFeatureContainer from "@/services/weather/containers/WeatherFeatureContainer.vue";
import WeatherDetailsForecastContainer from "@/services/weather/containers/WeatherDetailsForecastContainer.vue";
import WeatherForecastContainer from "@/services/weather/containers/WeatherForecastContainer.vue";
import {popupStore} from "@/popupStore.js";
import EnterCountryNamePopup from "@/popups/header/EnterCountryNamePopup.vue";
import ErrorPopup from "@/popups/header/ErrorPopup.vue";
import {useRoute, useRouter} from "vue-router";

const route = useRoute();
const router = useRouter();
const countryName = ref(route.params.name);
const dayLimit = ref(10);

function openErrorPopup() {
  popupStore.show(ErrorPopup, {
    message: "Введите корректное название города",
    onSubmit: () => {
      router.push(`/services/weather`).then(() => window.location.reload());
    }
  })
}

function loadCurrentWeather() {
  fetch(`/api/weather/get_current_weather?countryName=${countryName.value}`)
      .then(res => res.json())
      .then(json => {
        if(json.status != null) {
          openErrorPopup();
          return;
        }
        features.value = [];
        features.value.push({
          iconPath: "/images/temperature_icon.png",
          value: json.temperature
        });
        features.value.push({
          iconPath: "/images/wind_speed_icon.png",
          value: json.windSpeed
        });
        features.value.push({
          iconPath: "/images/humidity_icon.png",
          value: json.humidityIntensive
        });
        features.value.push({
          iconPath: "/images/pressure_icon.png",
          value: json.pressure
        });
      })
      .catch(_ => openErrorPopup());
}

function loadDailyWeather() {
  fetch(`/api/weather/get_daily_weather?countryName=${countryName.value}`)
      .then(res => res.json())
      .then(json => {
        if(json.status != null) {
          openErrorPopup();
          return;
        }
        detailForecasts.value = json.map(item => ({
          time: item.time,
          temperature: item.temperature,
          state: item.state
        }));
      })
      .catch(() => openErrorPopup());
}

function loadForecastWeather() {
  fetch(`/api/weather/get_forecast_weather?countryName=${countryName.value}&dayLimit=${dayLimit.value}`)
      .then(res => res.json())
      .then(json => {
        if(json.status != null) {
          openErrorPopup();
          return;
        }
        const curDate = new Date();
        curDate.setDate(curDate.getDate() + 1);
        const formattedDate = curDate.toISOString().split('T')[0];
        otherForecasts.value = json.filter(item => {
          if(item.date === formattedDate) {
            forecast.value = [{
              date: item.date,
              forecastFeatures: [
                {
                  iconPath: "/images/state_icon.png",
                  value: item.state
                },
                {
                  iconPath: "/images/day_temperature_icon.png",
                  value: item.dayTemperature
                },
                {
                  iconPath: "/images/night_temperature_icon.png",
                  value: item.nightTemperature
                }
              ]
            }];
            return false;
          }
          return true;
        }).map(item => ({
          date: item.date,
          forecastFeatures: [
            {
              iconPath: "/images/state_icon.png",
              value: item.state
            },
            {
              iconPath: "/images/day_temperature_icon.png",
              value: item.dayTemperature
            },
            {
              iconPath: "/images/night_temperature_icon.png",
              value: item.nightTemperature
            }
          ]
        }));
      })
      .catch(() => openErrorPopup());
}

//  __ STRUCTURE EXAMPLE __
const features = ref([
  {
    iconPath: "",
    value: "NONE"
  }
]);

const detailForecasts = ref([
  {
    time: "NONE",
    state: 0,
    temperature: 0
  }
]);

const otherForecasts = ref([
  {
    date: "NONE",
    forecastFeatures: [
      {
        iconPath: "",
        value: "NONE"
      }
    ]
  }
]);

const forecast = ref([{
  date: "NONE",
  forecastFeatures: [
    {
      iconPath: "",
      value: "NONE"
    }
  ]
}]);
//  __ STRUCTURE EXAMPLE __

const pathToEditIcon = "/images/edit_name_icon.png";
const pathToDateIcon = "/images/change_data_icon.png";

onMounted(() => {
  if (countryName == null || countryName.value === "") {
    popupStore.show(EnterCountryNamePopup);
    return;
  }
  loadCurrentWeather();
  loadDailyWeather();
  loadForecastWeather();
});
</script>

<template>
  <link rel="stylesheet" href="/css/pages/weather.css"/>
  <div class="weather-service">
    <div class="weather-service__left side">
      <div class="weather-service__header">
        <span class="weather-service__title">Прогноз на сегодня ({{ countryName }})</span>
        <a href="#" class="weather-service__header-button" @click="popupStore.show(EnterCountryNamePopup)">
          <img :src="pathToEditIcon" alt="EDIT"/>
        </a>
      </div>
      <div class="weather-service__content">
        <span class="weather-service__mini-header">Последнее обновление: сегодня</span>
        <WeatherFeatureContainer :features="features"/>
        <span class="weather-service__mini-header">Подробный прогноз</span>
        <WeatherDetailsForecastContainer :detail-forecasts="detailForecasts"/>
      </div>
    </div>
    <div class="weather-service__right side">
      <div class="weather-service__header">
        <span class="weather-service__title">Прогноз на {{ dayLimit }} дней вперед</span>
        <a href="#" class="weather-service__header-button" @click="popupStore.show(ErrorPopup, {message:'Test error message'})">
          <img :src="pathToDateIcon" alt="DATE"/>
        </a>
      </div>
      <div class="weather-service__content">
        <span class="weather-service__mini-header">Завтра</span>
        <WeatherForecastContainer :forecasts="forecast"/>
        <span class="weather-service__mini-header">Остальные дни</span>
        <WeatherForecastContainer :forecasts="otherForecasts"/>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>