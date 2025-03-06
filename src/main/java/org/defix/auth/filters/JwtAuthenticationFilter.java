package org.defix.auth.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.defix.auth.jwt.JwtParser;
import org.defix.auth.jwt.JwtValidator;
import org.defix.auth.jwt.objects.AccessTokenBody;
import org.defix.services.user.UserService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userRepo;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(UserService userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        logger.debug("Start jwt providing...");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        logger.debug(token==null?"No jwt in header. Checking cookie": STR."Jwt provided from header: \{token}");
        if(token == null && request.getCookies() != null) {
            Optional<Cookie> ctoken = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("token")).findFirst();
            if(ctoken.isPresent()) token = ctoken.get().getValue();
        }

        AccessTokenBody accessToken;
        logger.debug(token==null?"No jwt in cookie. Fail authenticate.": STR."Jwt provided from cookie: \{token}");
        if(token != null && (accessToken = JwtParser.parseAccessToken(token)) != null && SecurityContextHolder.getContext().getAuthentication() == null && JwtValidator.validate(token)) {
            String username = accessToken.getUsername();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, "", userRepo.loadUserByUsername(username).getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
