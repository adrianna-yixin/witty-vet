package net.yixin.witty_vet.controller;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.dto.ReviewDto;
import net.yixin.witty_vet.exception.AlreadyExistsException;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.model.Review;
import net.yixin.witty_vet.request.ReviewUpdateRequest;
import net.yixin.witty_vet.response.ApiResponse;
import net.yixin.witty_vet.service.review.ReviewService;
import net.yixin.witty_vet.utils.FeedbackMessage;
import net.yixin.witty_vet.utils.UrlMapping;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.REVIEWS)
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    @PostMapping(UrlMapping.SUBMIT_REVIEW)
    public ResponseEntity<ApiResponse> createReview(@RequestParam Long reviewerId, @RequestParam Long veterinarianId, @RequestBody Review review) {
        try {
            Review savedReview = reviewService.saveReview(reviewerId, veterinarianId, review);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.CREATE_SUCCESS, savedReview.getId()), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.CONFLICT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.FORBIDDEN);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(UrlMapping.GET_USER_REVIEWS)
    public ResponseEntity<ApiResponse> getReviewsByReviewerId(@PathVariable Long reviewerId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        Page<Review> reviewPage = reviewService.findAllReviewsByReviewerId(reviewerId, page, size);
        Page<ReviewDto> reviewDtos = reviewPage.map((singleReview) -> modelMapper.map(singleReview, ReviewDto.class));
        return new ResponseEntity<>(new ApiResponse(FeedbackMessage.RESOURCE_NOT_FOUND, reviewDtos), HttpStatus.OK);
    }

    @GetMapping(UrlMapping.GET_AVERAGE_RATING)
    public ResponseEntity<ApiResponse> getAverageRatingForVeterinarian(@PathVariable Long veterinarianId) {
        try {
            double averageRating = reviewService.getAverageRatingForVeterinarian(veterinarianId);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.RESOURCE_FOUND, averageRating), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.RESOURCE_NOT_FOUND, null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(UrlMapping.UPDATE_REVIEW)
    public ResponseEntity<ApiResponse> updateReview(@RequestBody ReviewUpdateRequest updateRequest,
                                                    @PathVariable Long reviewId) {
        try {
            Review updatedReview = reviewService.updateReview(reviewId, updateRequest);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.UPDATE_SUCCESS, reviewId), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(UrlMapping.DELETE_REVIEW)
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.DELETE_SUCCESS, null), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.RESOURCE_NOT_FOUND, null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
