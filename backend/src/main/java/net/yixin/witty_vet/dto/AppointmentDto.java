package net.yixin.witty_vet.dto;

import lombok.Data;
import net.yixin.witty_vet.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class AppointmentDto {
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private LocalDate createdAt;
    private String reason;
    private AppointmentStatus status;
    private String appointmentNo;
    private UserDto patient;
    private UserDto veterinarian;
    private List<PetDto> pets;
}
