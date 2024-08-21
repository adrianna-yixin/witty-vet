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
    public Review saveReview(Long patientId, Long veterinarianId, Review review) {
        validateReviewConditions(patientId, veterinarianId);
        return completeAndSaveReview(patientId, veterinarianId, review);
    }

    private void validateReviewConditions(Long patientId, Long veterinarianId) {
        validateNotSelfReview(patientId, veterinarianId);
        hasNotPatientSubmittedReviewForVeterinarian(patientId, veterinarianId);
        hasPatientCompletedAppointmentWithVeterinarian(patientId, veterinarianId);
    }

    private void validateNotSelfReview(Long patientId, Long veterinarianId) {
        if (veterinarianId.equals(patientId)) {
            throw new IllegalArgumentException(FeedbackMessage.CANNOT_SELF_REVIEW);
        }
    }

    private void hasNotPatientSubmittedReviewForVeterinarian(Long patientId, Long veterinarianId) {
        boolean hasReviewed = reviewRepository.existsByVeterinarianIdAndPatientId(veterinarianId, patientId);
        if (hasReviewed) {
            throw new AlreadyExistsException(FeedbackMessage.ALREADY_REVIEWED);
        }
    }

    private void hasPatientCompletedAppointmentWithVeterinarian(Long patientId, Long veterinarianId) {
        boolean hasCompletedAppointment = appointmentRepository.existsByPatientIdAndVeterinarianIdAndStatus(patientId, veterinarianId, AppointmentStatus.COMPLETED);
        if (!hasCompletedAppointment) {
            throw new IllegalStateException(FeedbackMessage.SHOULD_COMPLETE_APPOINTMENT);
        }
    }

    private Review completeAndSaveReview(Long patientId, Long veterinarianId, Review review) {
        User patient = userRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.VET_OR_PATIENT_NOT_FOUND));

        User veterinarian = userRepository.findById(veterinarianId).orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.VET_OR_PATIENT_NOT_FOUND));

        review.setPatient(patient);
        review.setVeterinarian(veterinarian);

        return reviewRepository.save(review);
    }

    @Override
    public Page<Review> findAllReviewsByPatientId(Long patientId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return reviewRepository.findAllByPatientId(patientId, pageRequest);
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
    public Review updateReview(Long patientId, ReviewUpdateRequest updateRequest) {
        Review existingReview = reviewRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND));
        updateReviewDetails(existingReview, updateRequest);
        return reviewRepository.save(existingReview);
    }

    private void updateReviewDetails(Review review, ReviewUpdateRequest updateRequest) {
        review.setStars(updateRequest.getStars());
        review.setFeedback(updateRequest.getFeedback());
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(FeedbackMessage.RESOURCE_NOT_FOUND));
        existingReview.removeRelationshipWithAllUsers();
        reviewRepository.deleteById(reviewId);
    }
}
