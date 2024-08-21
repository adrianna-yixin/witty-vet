package net.yixin.witty_vet.service.review;

import net.yixin.witty_vet.model.Review;
import net.yixin.witty_vet.request.ReviewUpdateRequest;
import org.springframework.data.domain.Page;

public interface ReviewService {
    Review saveReview(Long patientId, Long veterinarianId, Review review);
    Page<Review> findAllReviewsByPatientId(Long patientId, int page, int size);
    double getAverageRatingForVeterinarian(Long veterinarianId);
    Review updateReview(Long patientId, ReviewUpdateRequest updateRequest);
    void deleteReview(Long reviewId);
}
