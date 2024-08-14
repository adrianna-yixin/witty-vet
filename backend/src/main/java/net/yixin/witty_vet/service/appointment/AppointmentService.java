package net.yixin.witty_vet.service.appointment;

import net.yixin.witty_vet.model.Appointment;
import net.yixin.witty_vet.request.AppointmentUpdateRequest;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment, Long senderId, Long recipientId);
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Long id);
    Appointment getAppointmentByNo(String appointmentNo);
    Appointment updateAppointment(Long id, AppointmentUpdateRequest request);
    void deleteAppointment(Long id);
}
