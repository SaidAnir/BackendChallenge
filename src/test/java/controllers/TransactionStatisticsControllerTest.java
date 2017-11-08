package controllers;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import controller.StatisticsController;
import model.Statistic;
import service.StatisticsService;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
@ContextConfiguration(classes={StatisticsController.class})

public class TransactionStatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticsService statisticsService;


    @Test
    public void shouldReturnAStatisticsSample() throws Exception {
        when(statisticsService.getStatistics()).thenReturn(
        		new Statistic(45.6, 15.2, 20.5, 10.3, 3)
        		);

        mvc.perform(get("/n26/api/statistics").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("sum", is(45.6)))
                .andExpect(jsonPath("avg", is(15.2)))
                .andExpect(jsonPath("max", is(20.5)))
                .andExpect(jsonPath("min", is(10.3)))
                .andExpect(jsonPath("count", is(3)));
    }

    @Test
    public void shouldReturnNoStatistics() throws Exception {
        when(statisticsService.getStatistics()).thenReturn(
        		new Statistic(0.0,Double.NaN, Double.NaN, Double.NaN, 0)
        		);

        mvc.perform(get("/n26/api/statistics").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("sum", is(0.0)))
                .andExpect(jsonPath("avg", is("NaN")))
                .andExpect(jsonPath("max", is("NaN")))
                .andExpect(jsonPath("min", is("NaN")))
                .andExpect(jsonPath("count", is(0)));
    }
}