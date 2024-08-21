package net.yixin.witty_vet.repository;

import net.yixin.witty_vet.enums.AppointmentStatus;
import net.yixin.witty_vet.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentNo(String appointmentNo);
    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :userId OR a.veterinarian.id =:userId")
    List<Appointment> findAllByUserId(@Param("userId") Long userId);
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.patient.id = :patientId AND a.veterinarian.id = :veterinarianId AND a.status = :status")
    boolean existsByPatientIdAndVeterinarianIdAndStatus(@Param("patientId") Long patientId,
                                                        @Param("veterinarianId") Long veterinarianId,
                                                        @Param("status") AppointmentStatus appointmentStatus);
}
