package net.yixin.witty_vet.repository;

import net.yixin.witty_vet.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.patient.id = :reviewerId OR r.veterinarian.id = :reviewerId")
    Page<Review> findAllByReviewerId(@Param("reviewerId") Long reviewerId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.veterinarian.id = :veterinarianId")
    List<Review> findByVeterinarianId(Long veterinarianId);

    boolean existsByVeterinarianIdAndPatientId(Long veterinarianId, Long reviewerId);
}
