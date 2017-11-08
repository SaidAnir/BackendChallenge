package controllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import controller.TransactionController;
import service.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
@ContextConfiguration(classes={TransactionController.class})

public class TransactionsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void shouldAcceptValidRequest() throws Exception {
        mvc.perform(post("/n26/api/transactions")
                .contentType("application/json")
                .content("{\"amount\": 12.3,\"timestamp\": 1510089875000}"))
                .andExpect(status().isCreated())
                .andExpect(content().bytes(new byte[0]));

    }

    @Test
    public void shouldValidateRequest() throws Exception {
        mvc.perform(post("/n26/api/transactions")
                .contentType("application/json")
                .content("{\"amount\": 12.3,\"timestamp\": 0}"))
                .andExpect(status().isNoContent())
                .andExpect(content().bytes(new byte[0]));

    }

  
}
