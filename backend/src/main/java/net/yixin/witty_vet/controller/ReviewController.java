package net.yixin.witty_vet.controller;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.exception.AlreadyExistsException;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.model.Review;
import net.yixin.witty_vet.request.ReviewUpdateRequest;
import net.yixin.witty_vet.response.ApiResponse;
import net.yixin.witty_vet.service.review.ReviewService;
import net.yixin.witty_vet.utils.FeedbackMessage;
import net.yixin.witty_vet.utils.UrlMapping;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlMapping.REVIEWS)
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping(UrlMapping.SUBMIT_REVIEW)
    public ResponseEntity<ApiResponse> createReview(@RequestBody Review review) {
        try {
            Review savedReview = reviewService.saveReview(review);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.CREATE_SUCCESS, savedReview), HttpStatus.CREATED);
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
    public ResponseEntity<ApiResponse> getReviewsByReviewerId(@PathVariable Long userId,
                                                              @RequestParam int page,
                                                              @RequestParam int size) {
        Page<Review> reviewPage = reviewService.findAllReviewsByReviewerId(userId, page, size);
        return new ResponseEntity<>(new ApiResponse(FeedbackMessage.RESOURCE_NOT_FOUND, reviewPage), HttpStatus.OK);
    }

    @PutMapping(UrlMapping.UPDATE_REVIEW)
    public ResponseEntity<ApiResponse> updateReview(@RequestBody ReviewUpdateRequest updateRequest,
                                                    @PathVariable Long reviewId) {
        try {
            Review updatedReview = reviewService.updateReview(reviewId, updateRequest);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.UPDATE_SUCCESS, updatedReview), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }
}
