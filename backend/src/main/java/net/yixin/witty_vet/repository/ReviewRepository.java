package net.yixin.witty_vet.repository;

import net.yixin.witty_vet.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.patient.id = :patientId OR r.veterinarian.id = :patientId")
    Page<Review> findAllByPatientId(@Param("patientId") Long patientId, Pageable pageable);

    List<Review> findByVeterinarianId(Long veterinarianId);

    boolean existsByVeterinarianIdAndPatientId(Long veterinarianId, Long patientId);
}
