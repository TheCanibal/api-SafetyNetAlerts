package com.APISafetyNetAlerts.apiForSNA;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPersonsCoveredByFireStation() throws Exception {
	mockMvc.perform(get("/firestation").param("stationNumber", "1")).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].firstName", is("Peter")));

    }
}
