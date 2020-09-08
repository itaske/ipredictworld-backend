package com.ipredictworld.application.config;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTswhichistoosmalltousedsoihavetoaddwordstopadit";
    public static final long EXPIRATION_TIME = 90_800_000; // 15 minutes session in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/users";
    public static final String LOGIN_IN_URL = "/api/v1/users/login";
    public static final String GET_USERS = "/api/v1/users";
    public static final String RESET_PASSWORD = "/api/v1/user/reset_password";
}
