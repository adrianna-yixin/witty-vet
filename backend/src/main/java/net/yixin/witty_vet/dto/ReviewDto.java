package net.yixin.witty_vet.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private int stars;
    private String feedback;
    private Long veterinarianId;
    private String veterinarianName;
    private byte[] veterinarianPhoto;
    private String veterinarianSpecialization;
    private Long patientId;
    private String patientName;
    private byte[] patientPhoto;
}
