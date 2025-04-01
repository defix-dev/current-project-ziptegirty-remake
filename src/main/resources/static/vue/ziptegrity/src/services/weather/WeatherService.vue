<script setup>
import {onMounted, ref} from "vue";
import WeatherFeatureContainer from "@/services/weather/containers/WeatherFeatureContainer.vue";
import WeatherDetailsForecastContainer from "@/services/weather/containers/WeatherDetailsForecastContainer.vue";
import WeatherForecastContainer from "@/services/weather/containers/WeatherForecastContainer.vue";
import {popupStore} from "@/popupStore.js";
import EnterCountryNamePopup from "@/popups/weather/EnterCountryNamePopup.vue";
import ErrorPopup from "@/popups/common/ErrorPopup.vue";
import {useRoute, useRouter} from "vue-router";
import {tooltipStore} from "@/tooltipStore.js";
import EnterDayLimitTooltip from "@/tooltips/weather/EnterDayLimitTooltip.vue";

const weatherIds = Object.fromEntries(
    [
      [[0], "Ясно"],
      [[1, 2, 3], "Облачно"],
      [[45, 48], "Туман"],
      [[51, 53, 55], "Морось"],
      [[56, 57], "Сильная морось"],
      [[61, 63, 65], "Слабый дождь"],
      [[66, 67], "Дождь"],
      [[71, 73, 75], "Слабый снегопад"],
      [[77], "Снегопад"],
      [[80, 81, 82], "Ливень"],
      [[85, 86], "Снежный ливень"],
      [[95], "Гроза"],
      [[96, 99], "Сильная гроза"],
    ].flatMap(([ids, description]) => ids.map(id => [id, description]))
);

const route = useRoute();
const router = useRouter();
const countryName = ref(route.params.name);
const dayLimit = ref(10);

const openErrorPopup = () => {
  popupStore.show(ErrorPopup, {
    message: "Во время запроса к станции произошла ошибка, проверьте корректность вводимых данных и повторите попытку.",
    onSubmit: () => {
      router.push(`/services/weather`).then(() => window.location.reload());
    }
  })
}

const loadCurrentWeather = () => {
  fetch(`/api/v1/weather/current?countryName=${countryName.value}`)
      .then(res => res.json())
      .then(json => {
        if(json.status != null) {
          openErrorPopup();
          return;
        }
        features.value = [];
        features.value.push({
          iconPath: "/images/temperature_icon.png",
          value: json.temperature + "°C"
        });
        features.value.push({
          iconPath: "/images/wind_speed_icon.png",
          value: json.windSpeed + " км/ч"
        });
        features.value.push({
          iconPath: "/images/humidity_icon.png",
          value: json.humidityIntensive + "%"
        });
        features.value.push({
          iconPath: "/images/pressure_icon.png",
          value: json.pressure + " рт.ст."
        });
      })
      .catch(_ => openErrorPopup());
}

const loadDailyWeather = () => {
  fetch(`/api/v1/weather/daily?countryName=${countryName.value}`)
      .then(res => res.json())
      .then(json => {
        if(json.status != null) {
          openErrorPopup();
          return;
        }
        detailForecasts.value = json.map(item => ({
          time: item.time,
          temperature: item.temperature,
          state: weatherIds[item.state]
        }));
      })
      .catch(() => openErrorPopup());
}

const loadForecastWeather = () => {
  fetch(`/api/v1/weather/forecast?countryName=${countryName.value}&dayLimit=${dayLimit.value}`)
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
                  value: weatherIds[item.state]
                },
                {
                  iconPath: "/images/day_temperature_icon.png",
                  value: item.dayTemperature + "°C"
                },
                {
                  iconPath: "/images/night_temperature_icon.png",
                  value: item.nightTemperature + "°C"
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
              value: weatherIds[item.state]
            },
            {
              iconPath: "/images/day_temperature_icon.png",
              value: item.dayTemperature + "°C"
            },
            {
              iconPath: "/images/night_temperature_icon.png",
              value: item.nightTemperature + "°C"
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
    state: "",
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

const onChangeDayLimit = (limit) => {
  dayLimit.value = limit;
  loadForecastWeather();
}

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
        <a class="weather-service__header-button" @click="(event) => tooltipStore.show(EnterDayLimitTooltip, event.target, {
          onChangeLimit: onChangeDayLimit
        })">
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