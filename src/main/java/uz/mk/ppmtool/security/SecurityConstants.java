package uz.mk.ppmtool.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
//    public static final long EXPIRATION_TIME = 60_000; //60 seconds
    public static final long EXPIRATION_TIME = 36_000_000; // 3600 seconds


}
