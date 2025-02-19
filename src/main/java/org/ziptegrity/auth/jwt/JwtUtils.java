package org.ziptegrity.auth.jwt;

public class JwtUtils {
    public static String getEnvSecretKey() {
        if(!System.getenv().containsKey(JwtConstants.JWT_SECRET_KEY_ENV)) throw new IllegalArgumentException("Jwt secret key not found.");
        return System.getenv(JwtConstants.JWT_SECRET_KEY_ENV);
    }
}
