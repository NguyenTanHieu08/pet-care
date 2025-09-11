package com.hieusrpingboot.petcare.dto;

import com.hieusrpingboot.petcare.enums.HealthRecordType;
import java.time.LocalDateTime;
import java.util.List;

public class HealthRecordTimelineResponse {
    private String date; // Format: YYYY-MM-DD
    private List<HealthRecordTimelineItem> items;

    public HealthRecordTimelineResponse() {}

    public HealthRecordTimelineResponse(String date, List<HealthRecordTimelineItem> items) {
        this.date = date;
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<HealthRecordTimelineItem> getItems() {
        return items;
    }

    public void setItems(List<HealthRecordTimelineItem> items) {
        this.items = items;
    }

    public static class HealthRecordTimelineItem {
        private Long id;
        private LocalDateTime time;
        private HealthRecordType recordType;
        private String title;
        private String description;
        private String doctorName;
        private String clinicName;
        private String icon; // Emoji hoặc icon class

        public HealthRecordTimelineItem() {}

        public HealthRecordTimelineItem(Long id, LocalDateTime time, HealthRecordType recordType, 
                                      String title, String description, String doctorName, 
                                      String clinicName, String icon) {
            this.id = id;
            this.time = time;
            this.recordType = recordType;
            this.title = title;
            this.description = description;
            this.doctorName = doctorName;
            this.clinicName = clinicName;
            this.icon = icon;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

        public HealthRecordType getRecordType() {
            return recordType;
        }

        public void setRecordType(HealthRecordType recordType) {
            this.recordType = recordType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
