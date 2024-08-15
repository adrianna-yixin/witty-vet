package net.yixin.witty_vet.service.appointment;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.enums.AppointmentStatus;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.model.Appointment;
import net.yixin.witty_vet.model.Pet;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.AppointmentRepository;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.request.AppointmentBookingRequest;
import net.yixin.witty_vet.request.AppointmentUpdateRequest;
import net.yixin.witty_vet.service.pet.PetService;
import net.yixin.witty_vet.utils.FeedbackMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PetService petService;

    @Transactional
    @Override
    public Appointment createAppointment(Long senderId, Long recipientId, AppointmentBookingRequest request) {
        User sender = findSenderOrRecipientById(senderId);
        User recipient = findSenderOrRecipientById(recipientId);
        Appointment appointment = configureAppointment(request, sender, recipient);
        savePetsForAppointment(request.getPets(), appointment);
        return appointmentRepository.save(appointment);
    }
    private User findSenderOrRecipientById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.SENDER_OR_RECIPIENT_NOT_FOUNT));
    }
    private Appointment configureAppointment(AppointmentBookingRequest request, User sender, User recipient) {
        Appointment appointment = request.getAppointment();
        appointment.setPatient(sender);
        appointment.setVeterinarian(recipient);
        appointment.setAppointmentNo();
        appointment.setStatus(AppointmentStatus.WAITING_FOR_APPROVAL);
        return appointment;
    }
    private void savePetsForAppointment(List<Pet> pets, Appointment appointment) {
        pets.forEach(pet -> pet.setAppointment(appointment));
        List<Pet> savedPets = petService.savePetsForAppointment(pets);
        appointment.setPets(savedPets);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND));
    }

    @Override
    public Appointment getAppointmentByNo(String appointmentNo) {
        return appointmentRepository.findByAppointmentNo(appointmentNo);
    }

    @Override
    public Appointment updateAppointment(Long appointmentId, AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment existingAppointment = getAppointmentById(appointmentId);
        validateAppointmentStatus(existingAppointment);
        updateAppointmentDetails(existingAppointment, appointmentUpdateRequest);
        return appointmentRepository.save(existingAppointment);
    }
    private void validateAppointmentStatus(Appointment appointment) {
        if (!AppointmentStatus.WAITING_FOR_APPROVAL.equals(appointment.getStatus())) {
            throw new IllegalStateException(FeedbackMessage.ALREADY_APPROVED);
        }
    }
    private void updateAppointmentDetails(Appointment appointment, AppointmentUpdateRequest appointmentUpdateRequest) {
        appointment.setAppointmentDate(LocalDate.parse(appointmentUpdateRequest.getAppointmentDate()));
        appointment.setAppointmentTime(LocalTime.parse(appointmentUpdateRequest.getAppointmentTime()));
        appointment.setReason(appointmentUpdateRequest.getReason());
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND));
        appointmentRepository.delete(appointment);
    }
}
