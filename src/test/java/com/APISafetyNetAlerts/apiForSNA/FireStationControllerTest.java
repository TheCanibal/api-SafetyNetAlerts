package com.APISafetyNetAlerts.apiForSNA;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.web.servlet.MockMvc;

import com.APISafetyNetAlerts.apiForSNA.controller.FireStationController;
import com.APISafetyNetAlerts.apiForSNA.model.FireStation;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FireStationController fireStationController;

    @Autowired
    private static MappingJacksonValue mappingJacksonValue;

    public static FireStation firestationParam;
    public static FireStation firestationNoParam;
    public static FireStation firestationWrongAddress;
    public static FireStation firestationWrongStation;
    public static FireStation firestationNullAddress;

    public static String asJsonString(final Object obj) {
	try {
	    return new ObjectMapper().writeValueAsString(obj);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @BeforeAll
    public static void setUp() {
	firestationParam = new FireStation("19 rue des Coquelicots", 2);
	firestationNoParam = new FireStation();
	firestationWrongAddress = new FireStation("sdqfqsdgdfqsg", 2);
	firestationWrongStation = new FireStation("19 rue des Coquelicots", 0);
	firestationNullAddress = new FireStation(null, 2);
	mappingJacksonValue = new MappingJacksonValue(null);
    }

    @Test
    public void testGetAllFirestations() throws Exception {
	mockMvc.perform(get("/firestations")).andExpect(status().isOk());
    }

    @Test
    public void testGetPersonsCoveredByFireStation() throws Exception {
	mockMvc.perform(get("/firestation").param("stationNumber", "1")).andExpect(status().isOk());

	ObjectMapper objectMapper = new ObjectMapper();

	// Je récupère mon objet en l'appelant avec le contrôleur
	mappingJacksonValue = fireStationController.getPersonsCoveredByFireStation(1);

	// Je transforme mon objet MappingJacksonValue en String pour pouvoir le
	// comparer à l'objet que je veux tester
	String expect = objectMapper.setFilterProvider(mappingJacksonValue.getFilters())
		.writeValueAsString(mappingJacksonValue.getValue());

	// L'objet que je veux comparer à celui attendu
	String object = "{\"personnesMajeures\":5,\"personnesMineures\":1,\"persons\":[{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"address\":\"644 Gershwin Cir\",\"phone\":\"841-874-6512\"},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"address\":\"908 73rd St\",\"phone\":\"841-874-8547\"},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"address\":\"908 73rd St\",\"phone\":\"841-874-7462\"},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"phone\":\"841-874-7784\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"phone\":\"841-874-7784\"},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"phone\":\"841-874-7784\"}]}";

	assertEquals(expect, object);

    }

    @Test
    public void testGetPersonsLiveAtAddressDeservedByStation() throws Exception {
	mockMvc.perform(get("/fire").param("address", "1509 Culver St")).andExpect(status().isOk());

	ObjectMapper objectMapper = new ObjectMapper();

	// Je récupère mon objet en l'appelant avec le contrôleur
	mappingJacksonValue = fireStationController.getPersonsLiveAtAddressDeservedByStation("1509 Culver St");

	// Je transforme mon objet MappingJacksonValue en String pour pouvoir le
	// comparer à l'objet que je veux tester
	String expect = objectMapper.setFilterProvider(mappingJacksonValue.getFilters())
		.writeValueAsString(mappingJacksonValue.getValue());

	// L'objet que je veux comparer à celui attendu
	String object = "[{\"lastName\":\"Boyd\",\"phone\":\"841-874-6512\",\"age\":39,\"firestationNumber\":3,\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]},{\"lastName\":\"Boyd\",\"phone\":\"841-874-6513\",\"age\":34,\"firestationNumber\":3,\"medications\":[\"pharmacol:5000mg\",\"terazine:10mg\",\"noznazol:250mg\"],\"allergies\":[]},{\"lastName\":\"Boyd\",\"phone\":\"841-874-6512\",\"age\":11,\"firestationNumber\":3,\"medications\":[],\"allergies\":[\"peanut\"]},{\"lastName\":\"Boyd\",\"phone\":\"841-874-6512\",\"age\":6,\"firestationNumber\":3,\"medications\":[],\"allergies\":[]},{\"lastName\":\"Boyd\",\"phone\":\"841-874-6544\",\"age\":37,\"firestationNumber\":3,\"medications\":[\"tetracyclaz:650mg\"],\"allergies\":[\"xilliathal\"]}]";
	assertEquals(expect, object);

    }

    @Test
    public void testGetPersonsLiveAtAddressDeservedByStationWithWrongAddress() throws Exception {
	mockMvc.perform(get("/fire").param("address", "gfdqghsdfh")).andExpect(status().isOk());

    }

    @Test
    public void testGetPersonsLiveAtAddressDeservedByStationWithEmptyAddress() throws Exception {
	mockMvc.perform(get("/fire").param("address", "")).andExpect(status().isOk());

    }

    @Test
    public void testAllPersonsCoveredByFirestations() throws Exception {
	mockMvc.perform(get("/flood/stations").param("stations", "1,2")).andExpect(status().isOk());

    }

    @Test
    public void testAllPersonsCoveredByFirestationsWithWrongStationNumber() throws Exception {
	mockMvc.perform(get("/flood/stations").param("stations", "0,-10")).andExpect(status().isOk());

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
    public void testAddWrongAddressFirestation() throws Exception {
	mockMvc.perform(post("/firestation").content(asJsonString(firestationWrongAddress))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddWrongStationFirestation() throws Exception {
	mockMvc.perform(post("/firestation").content(asJsonString(firestationWrongStation))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNullAddressFirestation() throws Exception {
	mockMvc.perform(post("/firestation").content(asJsonString(firestationNullAddress))
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
    public void testUpdateWrongAddressFirestation() throws Exception {
	mockMvc.perform(put("/firestation").content(asJsonString(firestationWrongAddress))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongStationFirestation() throws Exception {
	mockMvc.perform(put("/firestation").content(asJsonString(firestationWrongStation))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNullAddressFirestation() throws Exception {
	mockMvc.perform(put("/firestation").content(asJsonString(firestationNullAddress))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNullFirestation() throws Exception {
	mockMvc.perform(put("/firestation").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
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
    public void testDeleteWrongAddressFirestation() throws Exception {
	mockMvc.perform(delete("/firestation").content(asJsonString(firestationWrongAddress))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteWrongStationFirestation() throws Exception {
	mockMvc.perform(delete("/firestation").content(asJsonString(firestationWrongStation))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNullAddressFirestation() throws Exception {
	mockMvc.perform(delete("/firestation").content(asJsonString(firestationNullAddress))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNullFirestation() throws Exception {
	mockMvc.perform(delete("/firestation").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

}
