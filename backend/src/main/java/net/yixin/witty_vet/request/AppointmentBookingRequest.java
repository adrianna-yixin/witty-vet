package net.yixin.witty_vet.request;

import lombok.Data;
import net.yixin.witty_vet.model.Appointment;
import net.yixin.witty_vet.model.Pet;

import java.util.List;

@Data
public class AppointmentBookingRequest {
    private Appointment appointment;
    private List<Pet> pets;
}
