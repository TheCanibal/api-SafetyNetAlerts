package com.APISafetyNetAlerts.apiForSNA;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    FireStation firestationParam;
    FireStation firestationNoParam;
    FireStation firestationWrongParam;

    public static String asJsonString(final Object obj) {
	try {
	    return new ObjectMapper().writeValueAsString(obj);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @BeforeEach
    public void setUp() {
	firestationParam = new FireStation("19 rue des Coquelicots", 2);
	firestationNoParam = new FireStation();
	firestationWrongParam = new FireStation("sdqfqsdgdfqsg", -1);
    }

    @Test
    public void testGetAllFirestations() throws Exception {
	mockMvc.perform(get("/firestations")).andExpect(status().isOk());
    }

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

    @Test
    public void testAddFirestation() throws Exception {
	mockMvc.perform(post("/firestation").content(asJsonString(firestationParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNoParamFirestation() throws Exception {
	mockMvc.perform(post("/firestation").content(asJsonString(firestationNoParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddWrongParamFirestation() throws Exception {
	mockMvc.perform(post("/firestation").content(asJsonString(firestationWrongParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNullFirestation() throws Exception {
	mockMvc.perform(post("/firestation").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateFirestation() throws Exception {
	mockMvc.perform(put("/firestation").content(asJsonString(firestationParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNoParamFirestation() throws Exception {
	mockMvc.perform(put("/firestation").content(asJsonString(firestationNoParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNullFirestation() throws Exception {
	mockMvc.perform(put("/firestation").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWrongParamFirestation() throws Exception {
	mockMvc.perform(put("/firestation").content(asJsonString(firestationWrongParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteFirestation() throws Exception {
	mockMvc.perform(delete("/firestation").content(asJsonString(firestationParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNoParamFirestation() throws Exception {
	mockMvc.perform(delete("/firestation").content(asJsonString(firestationNoParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNullFirestation() throws Exception {
	mockMvc.perform(delete("/firestation").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteWrongParamFirestation() throws Exception {
	mockMvc.perform(delete("/firestation").content(asJsonString(firestationWrongParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
