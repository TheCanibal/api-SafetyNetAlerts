package com.APISafetyNetAlerts.apiForSNA;

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
import org.springframework.test.web.servlet.MockMvc;

import com.APISafetyNetAlerts.apiForSNA.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static Person personParam;
    private static Person personNoParam;
    private static Person personNullFirstName;
    private static Person personNullLastName;
    private static Person personNullAddress;
    private static Person personNullCity;
    private static Person personNullZip;
    private static Person personNullPhone;
    private static Person personNullMail;
    private static Person personWrongFirstName;
    private static Person personWrongLastName;
    private static Person personAllNull;

    public static String asJsonString(final Object obj) {
	try {
	    return new ObjectMapper().writeValueAsString(obj);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @BeforeAll
    public static void setUp() {
	personParam = new Person("Kevin", "Jovanovic", "19 rue des Coquelicots", "Tronville-en-Barrois", 55310,
		"0607080910", "test@gmail.com");
	personNoParam = new Person();
	personNullFirstName = new Person(null, "Jovanovic", "19 rue des Coquelicots", "Tronville-en-Barrois", 55310,
		"0607080910", "test@gmail.com");
	personNullLastName = new Person("Kévin", null, "19 rue des Coquelicots", "Tronville-en-Barrois", 55310,
		"0607080910", "test@gmail.com");
	personNullAddress = new Person("Kévin", "Jovanovic", null, "Tronville-en-Barrois", 55310, "0607080910",
		"test@gmail.com");
	personNullCity = new Person("Kévin", "Jovanovic", "19 rue des Coquelicots", null, 55310, "0607080910",
		"test@gmail.com");
	personNullZip = new Person("Kévin", "Jovanovic", "19 rue des Coquelicots", "Tronville-en-Barrois", 0,
		"0607080910", "test@gmail.com");
	personNullPhone = new Person("Kévin", "Jovanovic", "19 rue des Coquelicots", "Tronville-en-Barrois", 55310,
		null, "test@gmail.com");
	personNullMail = new Person("Kévin", "Jovanovic", "19 rue des Coquelicots", "Tronville-en-Barrois", 55310,
		"0607080910", null);
	personWrongFirstName = new Person("Kekekekekekeke", "Jovanovic", "19 rue des Coquelicots",
		"Tronville-en-Barrois", 55310, "0607080910", "test@gmail.com");
	personWrongLastName = new Person("Kévin", "Jojojojojo", "19 rue des Coquelicots", "Tronville-en-Barrois", 55310,
		"0607080910", "test@gmail.com");
	personAllNull = new Person(null, null, null, null, 0, null, null);
    }

    @Test
    public void testGetAllPersons() throws Exception {
	mockMvc.perform(get("/persons")).andExpect(status().isOk());
    }

    @Test
    public void testGetChildByAddress() throws Exception {
	mockMvc.perform(get("/childAlert").param("address", "1509 Culver St")).andExpect(status().isOk());
    }

    @Test
    public void testGetPhoneNumberDeservedByFirestations() throws Exception {
	mockMvc.perform(get("/phoneAlert").param("firestation", "1")).andExpect(status().isOk());
    }

    @Test
    public void testgetPersonInfoByFirstNameAndLastName() throws Exception {
	mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Boyd"))
		.andExpect(status().isOk());
    }

    @Test
    public void testgetPersonsMailByCity() throws Exception {
	mockMvc.perform(get("/communityEmail").param("city", "Culver")).andExpect(status().isOk());
    }

    @Test
    public void testAddPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personParam)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNoParamPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personNoParam)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddWrongFirstNameParamPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personNullFirstName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddWrongLastNameParamPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personNullLastName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddWrongAddressParamPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personNullAddress)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddWrongCityParamPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personNullCity)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddWrongZipParamPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personNullZip)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddWrongPhoneParamPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personNullPhone)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddWrongEmailParamPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personNullMail)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddAllNullParamPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(personAllNull)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNullPerson() throws Exception {
	mockMvc.perform(post("/person").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personParam)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNoParamPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personNoParam)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongFirstNameParamPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personNullFirstName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongLastNameParamPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personNullLastName)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongAddressParamPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personNullAddress)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongCityParamPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personNullCity)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongZipParamPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personNullZip)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongPhoneParamPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personNullPhone)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongEmailParamPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personNullMail)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateAllNullParamPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(personAllNull)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNullPerson() throws Exception {
	mockMvc.perform(put("/person").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePerson() throws Exception {
	mockMvc.perform(delete("/person").content(asJsonString(personParam)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNoParamPerson() throws Exception {
	mockMvc.perform(delete("/person").content(asJsonString(personNoParam)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteWrongFirstNameParamPerson() throws Exception {
	mockMvc.perform(delete("/person").content(asJsonString(personWrongFirstName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteWrongLastNameParamPerson() throws Exception {
	mockMvc.perform(delete("/person").content(asJsonString(personWrongLastName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteAllNullParamPerson() throws Exception {
	mockMvc.perform(delete("/person").content(asJsonString(personAllNull)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNullPerson() throws Exception {
	mockMvc.perform(delete("/person").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
}
