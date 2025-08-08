package com.wefin.srm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void postRatesIsUnauthorizedForAnonymous() throws Exception {
        mockMvc.perform(post("/api/v1/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void postRatesIsForbiddenForNonAdminUser() throws Exception {
        mockMvc.perform(post("/api/v1/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void postRatesIsAccessibleForAdminUser( ) throws Exception {
        mockMvc.perform(post("/api/v1/rates")
                        .with(httpBasic("admin", "password" ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fromCurrencyCode\":\"GOL\",\"toCurrencyCode\":\"TIB\",\"rate\":2.8}"))
                .andExpect(status().isOk());
    }

    @Test
    void getRatesIsOkForAnonymous() throws Exception {
        mockMvc.perform(get("/api/v1/rates/GOL-TIB"))
                .andExpect(status().isNotFound());
    }
}
