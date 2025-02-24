package org.ziptegrity.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.ziptegrity.services.weather.WeatherApiFacade;
import org.ziptegrity.services.weather.controllers.WeatherControllerV1;
import org.ziptegrity.services.weather.objects.CurrentWeatherApiDTO;

import static org.mockito.Mockito.when;

@WebMvcTest(WeatherControllerV1.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherApiFacade apiFacade;

    @Test
    @WithMockUser
    public void testGetWeather() throws Exception {
        when(apiFacade.getCurrentWeather("Vologda")).thenReturn(
                new CurrentWeatherApiDTO()
        );
        mockMvc.perform(get("/api/weather/get_current_weather?countryName=Vologda"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(4));
    }
}
