package net.yixin.witty_vet.service.review;

import net.yixin.witty_vet.model.Review;
import net.yixin.witty_vet.request.ReviewUpdateRequest;
import org.springframework.data.domain.Page;

public interface ReviewService {
    Review saveReview(Review review);
    Page<Review> findAllReviewsByReviewerId(Long reviewerId, int page, int size);
    double getAverageRatingForVeterinarian(Long veterinarianId);
    Review updateReview(Long reviewerId, ReviewUpdateRequest updateRequest);
}
