package com.hieusrpingboot.petcare.dto;

import com.hieusrpingboot.petcare.enums.HealthRecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class HealthRecordRequest {
    @NotNull(message = "Pet ID is required")
    private Long petId;

    @NotNull(message = "Vet ID is required")
    private Long vetId;

    @NotNull(message = "Visit date is required")
    private LocalDateTime visitDate;

    @NotNull(message = "Record type is required")
    private HealthRecordType recordType;

    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    private String treatment;

    // Thông tin tiêm chủng
    private String vaccineType;
    private String vaccineBatch;
    private LocalDateTime nextVaccinationDate;

    // Thông tin dị ứng
    private String allergyType; // THUOC, THUC_AN, KHAC
    private String allergyDescription;

    // Thông tin thuốc men
    private String medicationName;
    private String medicationDosage;
    private String medicationFrequency;
    private String medicationDuration;

    // Thông tin bác sĩ
    private String doctorName;
    private String clinicName;

    private String notes;
    private List<String> attachments;

    // Constructors
    public HealthRecordRequest() {}

    // Getters and Setters
    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getVetId() {
        return vetId;
    }

    public void setVetId(Long vetId) {
        this.vetId = vetId;
    }

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }

    public HealthRecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(HealthRecordType recordType) {
        this.recordType = recordType;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public String getVaccineBatch() {
        return vaccineBatch;
    }

    public void setVaccineBatch(String vaccineBatch) {
        this.vaccineBatch = vaccineBatch;
    }

    public LocalDateTime getNextVaccinationDate() {
        return nextVaccinationDate;
    }

    public void setNextVaccinationDate(LocalDateTime nextVaccinationDate) {
        this.nextVaccinationDate = nextVaccinationDate;
    }

    public String getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
    }

    public String getAllergyDescription() {
        return allergyDescription;
    }

    public void setAllergyDescription(String allergyDescription) {
        this.allergyDescription = allergyDescription;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getMedicationDosage() {
        return medicationDosage;
    }

    public void setMedicationDosage(String medicationDosage) {
        this.medicationDosage = medicationDosage;
    }

    public String getMedicationFrequency() {
        return medicationFrequency;
    }

    public void setMedicationFrequency(String medicationFrequency) {
        this.medicationFrequency = medicationFrequency;
    }

    public String getMedicationDuration() {
        return medicationDuration;
    }

    public void setMedicationDuration(String medicationDuration) {
        this.medicationDuration = medicationDuration;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
}
