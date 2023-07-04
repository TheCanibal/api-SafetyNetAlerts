package com.APISafetyNetAlerts.apiForSNA;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	mockMvc.perform(get("/firestation").param("stationNumber", "1")).andExpect(status().isOk());

    }

    @Test
    public void testGetPersonsLiveAtAddressDeservedByStation() throws Exception {
	mockMvc.perform(get("/fire").param("address", "1509 Culver St")).andExpect(status().isOk());

    }

    @Test
    public void testAllPersonsCoveredByFirestations() throws Exception {
	mockMvc.perform(get("/flood/stations").param("stations", "1,2")).andExpect(status().isOk());

    }
}
