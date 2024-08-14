package net.yixin.witty_vet.controller;

import lombok.RequiredArgsConstructor;
import net.yixin.witty_vet.exception.ResourceNotFoundException;
import net.yixin.witty_vet.model.Photo;
import net.yixin.witty_vet.response.ApiResponse;
import net.yixin.witty_vet.service.photo.PhotoService;
import net.yixin.witty_vet.utils.FeedbackMessage;
import net.yixin.witty_vet.utils.UrlMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(UrlMapping.PHOTOS)
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping(UrlMapping.UPLOAD_PHOTO)
    public ResponseEntity<ApiResponse> uploadPhoto(@RequestParam MultipartFile file,
                                                   @RequestParam Long userId){
        try {
            Photo photo = photoService.savePhoto(file, userId);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.CREATE_SUCCESS, photo.getId()), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(UrlMapping.GET_PHOTO_BY_ID)
    public ResponseEntity<ApiResponse> getPhotoById(@PathVariable Long photoId) {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.RESOURCE_FOUND, photoId), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(FeedbackMessage.RESOURCE_NOT_FOUND, null), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(UrlMapping.UPDATE_PHOTO)
    public ResponseEntity<ApiResponse> updatePhoto(@PathVariable Long photoId, @RequestBody MultipartFile file) throws SQLException {
        try {
            Photo existingPhoto = photoService.getPhotoById(photoId);
            if (existingPhoto != null) {
                Photo updatedPhoto = photoService.updatePhoto(existingPhoto.getId(), file);
                return new ResponseEntity<>(new ApiResponse(FeedbackMessage.UPDATE_SUCCESS, updatedPhoto.getId()), HttpStatus.OK);
            }
        } catch (ResourceNotFoundException | IOException e) {
            return new ResponseEntity<>(new ApiResponse(null, NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(null, INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(UrlMapping.DELETE_PHOTO)
    public ResponseEntity<ApiResponse> deletePhoto(@PathVariable Long photoId, @PathVariable Long userId) {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            if (photo != null) {
                photoService.deletePhoto(photo.getId(), userId);
                return new ResponseEntity<>(new ApiResponse(FeedbackMessage.DELETE_SUCCESS, photo.getId()), HttpStatus.OK);
            }
        } catch (ResourceNotFoundException | SQLException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(null, INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }
}
