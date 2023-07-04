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
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}
