package net.yixin.witty_vet.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties({"isEnabled", "appointmentCreatedAt", "photo"})
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String email;
    private boolean isEnabled;
    private String specialization;
    private List<AppointmentDto> appointments;
    private List<ReviewDto> reviews;
    private Long photoId;
    private byte[] photo;
    private double averageRating;
}
