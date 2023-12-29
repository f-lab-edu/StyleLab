package com.stylelab.common.security.jwt;

import com.stylelab.common.exception.ServiceException;
import com.stylelab.user.constant.UsersRole;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("토큰 생성 테스트")
    public void createAuthToken() {
        // given
        final String email = "test@gmail.com";
        final String role = UsersRole.ROLE_USER.name();

        // when
        String token = jwtTokenProvider.createAuthToken(email, role);

        // then
        assertNotNull(token);
    }

    @Test
    @DisplayName("유효하지 않은 토큰이거나 만료된 토큰인 경우 예외가 발생하고 Optional empty를 반환")
    public void failureGetTokenClaims() {
        // given
        final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbS";

        // when
        Optional<Claims> tokenClaims = jwtTokenProvider.getTokenClaims(token);

        // then
        assertTrue(tokenClaims.isEmpty());
    }

    @Test
    @DisplayName("유효하지 않은 토큰이거나 만료된 토큰인 경우 토큰 인증 실패")
    public void failureGetAuthentication_01() {
        // given
        final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbS";

        // when
        assertThrows(ServiceException.class,
                () -> jwtTokenProvider.getAuthentication(token));
    }

    @Test
    @DisplayName("유효하지 않은 사용자 인 경우 토큰 인증 실패")
    public void failureGetAuthentication_02() {
        // given
        final String email = "test12@gmail.com";
        final String role = UsersRole.ROLE_USER.name();
        final String token = jwtTokenProvider.createAuthToken(email, role);

        // when
        assertThrows(ServiceException.class,
                () -> jwtTokenProvider.getAuthentication(token));
    }
}
