package net.yixin.witty_vet.service.review;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.enums.AppointmentStatus;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.exception.AlreadyExistsException;
import net.yixin.witty_vet.model.Review;
import net.yixin.witty_vet.model.User;
import net.yixin.witty_vet.repository.AppointmentRepository;
import net.yixin.witty_vet.repository.ReviewRepository;
import net.yixin.witty_vet.repository.UserRepository;
import net.yixin.witty_vet.request.ReviewUpdateRequest;
import net.yixin.witty_vet.service.user.UserService;
import net.yixin.witty_vet.utils.FeedbackMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Override
    public Review saveReview(Review review) {
        Long reviewerId = review.getPatient().getId();
        Long veterinarianId = review.getVeterinarian().getId();

        validateReviewConditions(reviewerId, veterinarianId);
        return completeAndSaveReview(reviewerId, veterinarianId, review);
    }

    private void validateReviewConditions(Long reviewerId, Long veterinarianId) {
        validateNotSelfReview(reviewerId, veterinarianId);
        hasNotReviewerSubmittedReviewForVeterinarian(reviewerId, veterinarianId);
        hasReviewerCompletedAppointmentWithVeterinarian(reviewerId, veterinarianId);
    }

    private void validateNotSelfReview(Long reviewerId, Long veterinarianId) {
        if (veterinarianId.equals(reviewerId)) {
            throw new IllegalArgumentException(FeedbackMessage.CANNOT_SELF_REVIEW);
        }
    }

    private void hasNotReviewerSubmittedReviewForVeterinarian(Long reviewerId, Long veterinarianId) {
        boolean hasReviewed = reviewRepository.existsByVeterinarianIdAndPatientId(veterinarianId, reviewerId);
        if (hasReviewed) {
            throw new AlreadyExistsException(FeedbackMessage.ALREADY_REVIEWED);
        }
    }

    private void hasReviewerCompletedAppointmentWithVeterinarian(Long reviewerId, Long veterinarianId) {
        boolean hasCompletedAppointment = appointmentRepository.existsByPatientIdAndVeterinarianIdAndStatus(reviewerId, veterinarianId, AppointmentStatus.COMPLETED);
        if (!hasCompletedAppointment) {
            throw new IllegalStateException(FeedbackMessage.SHOULD_COMPLETE_APPOINTMENT);
        }
    }

    private Review completeAndSaveReview(Long reviewerId, Long veterinarianId, Review review) {
        User reviewer = userRepository.findById(reviewerId).orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.VET_OR_PATIENT_NOT_FOUND));

        User veterinarian = userRepository.findById(veterinarianId).orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.VET_OR_PATIENT_NOT_FOUND));

        review.setPatient(reviewer);
        review.setVeterinarian(veterinarian);

        return reviewRepository.save(review);
    }

    @Override
    public Page<Review> findAllReviewsByReviewerId(Long reviewerId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return reviewRepository.findAllByReviewerId(reviewerId, pageRequest);
    }

    @Override
    public double getAverageRatingForVeterinarian(Long veterinarianId) {
        List<Review> reviews = reviewRepository.findByVeterinarianId(veterinarianId);
        return calculateAverageRating(reviews);
    }

    private double calculateAverageRating(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream().mapToInt(Review::getStars).average().orElse(0.0);
    }

    @Override
    public Review updateReview(Long reviewerId, ReviewUpdateRequest updateRequest) {
        Review existingReview = reviewRepository.findById(reviewerId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND));
        updateReviewDetails(existingReview, updateRequest);
        return reviewRepository.save(existingReview);
    }
    private void updateReviewDetails(Review review, ReviewUpdateRequest updateRequest) {
        review.setStars(updateRequest.getStars());
        review.setFeedback(updateRequest.getFeedback());
    }
}
