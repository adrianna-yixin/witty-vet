package net.yixin.witty_vet.utils;

public class UrlMapping {
    public static final String API = "/api/v1";

    /* User API */
    public static final String USERS = API + "/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/update/{userId}/user";
    public static final String GET_USER_BY_ID = "/user/{userId}";
    public static final String DELETE_USER_BY_ID = "/delete/{userId}/user";
    public static final String GET_ALL_USERS = "/all-users";

    /* Appointment API */
    public static final String APPOINTMENTS = API + "/appointments";
    public static final String GET_ALL_APPOINTMENTS = "/all-appointments";
    public static final String BOOK_APPOINTMENT = "/book-appointment";
    public static final String GET_APPOINTMENT_BY_ID = "/appointment/id/{appointmentId}";
    public static final String GET_APPOINTMENT_BY_NO = "/appointment/number/{appointmentNo}";
    public static final String UPDATE_APPOINTMENT = "/update/{appointmentId}/appointment";
    public static final String DELETE_APPOINTMENT = "/delete/{appointmentId}/appointment";
}
