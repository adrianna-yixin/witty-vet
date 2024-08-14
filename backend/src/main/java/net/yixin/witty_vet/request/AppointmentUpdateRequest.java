package net.yixin.witty_vet.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentUpdateRequest {
    private String appointmentDate;
    private String appointmentTime;
    private String reason;
}
