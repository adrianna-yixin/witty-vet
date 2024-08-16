package net.yixin.witty_vet.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String email;
    private String userType;
    private boolean isEnabled;
    private String specialization;
    private LocalDate appointmentCreatedAt;
    private List<AppointmentDto> appointmentDtos;
    private List<ReviewDto> reviewDtos;
    private Long imageId;
    private byte[] image;
    private double averageRating;
}
