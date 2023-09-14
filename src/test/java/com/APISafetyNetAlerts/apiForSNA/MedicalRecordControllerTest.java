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

import com.APISafetyNetAlerts.apiForSNA.model.MedicalRecords;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static MedicalRecords medicalRecordParam;
    private static MedicalRecords medicalRecordNoParam;
    private static MedicalRecords medicalRecordNullFirstName;
    private static MedicalRecords medicalRecordNullLastName;
    private static MedicalRecords medicalRecordNullBirthdate;
    private static MedicalRecords medicalRecordNullMedications;
    private static MedicalRecords medicalRecordNullAllergies;
    private static MedicalRecords medicalRecordWrongFirstName;
    private static MedicalRecords medicalRecordWrongLastName;
    private static MedicalRecords medicalRecordWrongBirthdate;

    public static String asJsonString(final Object obj) {
	try {
	    return new ObjectMapper().writeValueAsString(obj);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @BeforeAll
    public static void setUp() {
	String[] medications = { "doliprane", "xanax" };
	String[] allergies = { "nuts" };
	medicalRecordParam = new MedicalRecords("Kevin", "Jovanovic", "06/12/1996", medications, allergies);
	medicalRecordNoParam = new MedicalRecords();
	medicalRecordNullFirstName = new MedicalRecords(null, "Jovanovic", "06/12/1996", medications, allergies);
	medicalRecordNullLastName = new MedicalRecords("Kevin", null, "06/12/1996", medications, allergies);
	medicalRecordNullBirthdate = new MedicalRecords("Kevin", "Jovanovic", null, medications, allergies);
	medicalRecordNullMedications = new MedicalRecords("Kevin", "Jovanovic", "06/12/1996", null, allergies);
	medicalRecordNullAllergies = new MedicalRecords("Kevin", "Jovanovic", "06/12/1996", medications, null);
	medicalRecordWrongFirstName = new MedicalRecords("fjhbqsdlkfsd", "Jovanovic", "06/12/1996", medications,
		allergies);
	medicalRecordWrongLastName = new MedicalRecords("Kevin", "gsdgdfshdfhqz", "06/12/1996", medications, allergies);
	medicalRecordWrongBirthdate = new MedicalRecords("Kevin", "Jovanovic", "0612", medications, allergies);
    }

    @Test
    public void testGetAllMedicalRecords() throws Exception {
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
    public void testAddNullFirstNameMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(medicalRecordNullFirstName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNullLastNameMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(medicalRecordNullLastName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNullBirthdateMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(medicalRecordNullBirthdate))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNullMedicationsMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(medicalRecordNullMedications))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNullAllergiesMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(medicalRecordNullAllergies))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddNullMedicalRecord() throws Exception {
	mockMvc.perform(post("/medicalRecord").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
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
    public void testUpdateNullBirthdateMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(medicalRecordNullBirthdate))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNullMedicationsMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(medicalRecordNullMedications))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNullAllergiesMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(medicalRecordNullAllergies))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongBirthdateMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(medicalRecordWrongBirthdate))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateWrongLastNameMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(medicalRecordWrongLastName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateNullMedicalRecord() throws Exception {
	mockMvc.perform(put("/medicalRecord").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
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
    public void testDeleteNullFirstNameMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(medicalRecordNullFirstName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNullLastNameMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(medicalRecordNullLastName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteWrongFirstNameMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(medicalRecordWrongFirstName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteWrongLastNameMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(medicalRecordWrongLastName))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteWrongBirthdateMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(medicalRecordWrongBirthdate))
		.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testDeleteNullMedicalRecord() throws Exception {
	mockMvc.perform(delete("/medicalRecord").content(asJsonString(null)).contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

}
