package net.yixin.witty_vet.repository;

import net.yixin.witty_vet.enums.AppointmentStatus;
import net.yixin.witty_vet.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentNo(String appointmentNo);
    List<Appointment> findAllByUserId(Long userId);
    boolean existsByPatientIdAndVeterinarianIdAndStatus(Long reviewerId, Long veterinarianId, AppointmentStatus appointmentStatus);
}
