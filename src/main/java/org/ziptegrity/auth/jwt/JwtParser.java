package org.ziptegrity.auth.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ziptegrity.auth.jwt.objects.AccessTokenBody;
import io.jsonwebtoken.Jwts;

import javax.crypto.spec.SecretKeySpec;

public class JwtParser {
    private final static Logger logger = LoggerFactory.getLogger(JwtParser.class);

    public static AccessTokenBody parseAccessToken(String token) {
        try {
            return new AccessTokenBody((String) Jwts.parserBuilder()
                    .setSigningKey(new SecretKeySpec(JwtUtils.getEnvSecretKey().getBytes(), JwtConstants.CRYPT_ALG.getJcaName())).build()
                    .parseClaimsJws(token)
                    .getBody().get("username"));
        } catch (Exception e) {
            logger.error("Failed to parse access token: "+token);
            return null;
        }
    }
}
