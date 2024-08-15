package net.yixin.witty_vet.utils;

public class UrlMapping {
    public static final String API = "/api/v1";

    /* User API */
    public static final String USERS = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String GET_ALL_USERS = "/all-users";
    public static final String GET_USER_BY_ID = "/user/id/{userId}";
    public static final String UPDATE_USER = "/user/{userId}/update";
    public static final String DELETE_USER_BY_ID = "/user/{userId}/delete";

    /* Appointment API */
    public static final String APPOINTMENTS = API + "/appointments";
    public static final String BOOK_APPOINTMENT = "/book-appointment";
    public static final String GET_ALL_APPOINTMENTS = "/all-appointments";
    public static final String GET_APPOINTMENT_BY_ID = "/appointment/id/{appointmentId}";
    public static final String GET_APPOINTMENT_BY_NO = "/appointment/number/{appointmentNo}";
    public static final String UPDATE_APPOINTMENT = "/appointment/{appointmentId}/update";
    public static final String DELETE_APPOINTMENT = "/appointment/{appointmentId}/delete";

    /* Pet API */
    public static final String PETS = API + "/pets";
    public static final String SAVE_PETS_FOR_APPOINTMENT = "/save-pets";
    public static final String GET_PET_BY_ID = "/pet/id/{petId}";
    public static final String UPDATE_PET = "/pet/{petId}/update";
    public static final String DELETE_PET_BY_ID = "/pet/{petId}/delete";

    /* Photo API */
    public static final String PHOTOS = API + "/photos";
    public static final String UPLOAD_PHOTO = "/upload-photo";
    public static final String GET_PHOTO_BY_ID = "/photo/id/{photoId}";
    public static final String UPDATE_PHOTO = "/photo/{photoId}/update";
    public static final String DELETE_PHOTO = "/photo/{photoId}/user/{userId}/delete";

    /* Review API */
    public static final String REVIEWS = API + "/reviews";
    public static final String SUBMIT_REVIEW = "/submit-review";
    public static final String GET_USER_REVIEWS = "/user/{reviewerId}/reviews";
    public static final String GET_AVERAGE_RATING = "/veterinarian/{veterinarianId}/get-average-rating";
    public static final String UPDATE_REVIEW = "/review/{reviewId}/update";
    public static final String DELETE_REVIEW = "/review/{reviewId}/delete";
}
