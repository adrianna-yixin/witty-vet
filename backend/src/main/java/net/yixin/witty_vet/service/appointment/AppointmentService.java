package net.yixin.witty_vet.service.appointment;

import net.yixin.witty_vet.dto.AppointmentDto;
import net.yixin.witty_vet.model.Appointment;
import net.yixin.witty_vet.request.AppointmentBookingRequest;
import net.yixin.witty_vet.request.AppointmentUpdateRequest;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(Long senderId, Long recipientId, AppointmentBookingRequest request);
    List<Appointment> getAllAppointments();
    List<AppointmentDto> getUserAppointments(Long userId);
    Appointment getAppointmentById(Long id);
    Appointment getAppointmentByNo(String appointmentNo);
    Appointment updateAppointment(Long id, AppointmentUpdateRequest request);
    void deleteAppointment(Long id);
}
