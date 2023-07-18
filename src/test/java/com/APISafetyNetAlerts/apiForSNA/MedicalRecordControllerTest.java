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

import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    MedicalRecords medicalRecordParam;
    MedicalRecords medicalRecordNoParam;
    MedicalRecords medicalRecordWrongBirthdate;

    public static String asJsonString(final Object obj) {
	try {
	    return new ObjectMapper().writeValueAsString(obj);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @BeforeEach
    public void setUp() {
	String[] medications = { "doliprane", "xanax" };
	String[] allergies = { "nuts" };
	medicalRecordParam = new MedicalRecords("Kevin", "Jovanovic", "06/12/1996", medications, allergies);
	medicalRecordNoParam = new MedicalRecords();
	medicalRecordWrongBirthdate = new MedicalRecords("Kevin", "Jovanovic", "0612", medications, allergies);
    }

    @Test
    public void testGetAllFirestations() throws Exception {
	mockMvc.perform(get("/medicalRecords")).andExpect(status().isOk());
    }

    @Test
    public void testAddMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(medicalRecordParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNoParamMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(medicalRecordNoParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNullMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testAddWrongBirthdateMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(medicalRecordWrongBirthdate))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(medicalRecordParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNoParamMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(medicalRecordNoParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNullMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateWrongBirthdateMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(medicalRecordWrongBirthdate))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(medicalRecordParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNoParamMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(medicalRecordNoParam))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNullMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteWrongBirthdateMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(medicalRecordWrongBirthdate))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
