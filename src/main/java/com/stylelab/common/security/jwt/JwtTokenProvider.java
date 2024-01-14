package com.stylelab.common.security.jwt;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.common.security.constant.UserType;
import com.stylelab.common.security.filter.UserTypeRequestScope;
import com.stylelab.common.security.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.stylelab.common.exception.ServiceError.UNAUTHORIZED;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final UserTypeRequestScope userTypeRequestScope;
    private final CustomUserDetailsService customUserDetailsService;
    private static final String AUTHORITIES_KEY = "role";
    private static final String USER_TYPE = "type";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;

    public JwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            CustomUserDetailsService customUserDetailsService,
            UserTypeRequestScope userTypeRequestScope) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.customUserDetailsService = customUserDetailsService;
        this.userTypeRequestScope = userTypeRequestScope;
    }

    public String createAuthToken(final String email, final String role, UserType userType) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .subject(email)
                .claim(AUTHORITIES_KEY, role)
                .claim(USER_TYPE, userType)
                .signWith(secretKey)
                .expiration(expireDate)
                .compact();
    }

    public Authentication getAuthentication(final String token) {
        try {
            Claims claims = this.getTokenClaims(token)
                    .orElseThrow(() -> new ServiceException(UNAUTHORIZED, UNAUTHORIZED.getMessage()));

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            log.info("claims subject := [{}]", claims.getSubject());
            userTypeRequestScope.createUserType((String) claims.get(USER_TYPE));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        } catch (ServiceException e) {
            log.error("token authentication fail", e);
            throw e;
        }
    }

    public Optional<Claims> getTokenClaims(final String token) {
        try {
            return Optional.ofNullable(Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
            );
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature.", e);
        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException JWT token.", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.", e);
        } catch (Exception e) {
            log.error("get token claims fail.", e);
        }

        return Optional.empty();
    }
}
