package com.stylelab.user.presentation.response;

import com.stylelab.user.exception.UsersException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SigInResponseTest {

    @Test
    @DisplayName("access token이 담긴 로그인 응답 객체 생성에 성공.")
    public void successCreateResponse() {
        // given
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGUiOiJST0xFX1VTRVIiLCJleHAiOjE3MDM2OTU0MzV9.FkF6Dq6sM-krPxm5CzYE4qknlBZsou7n1GPd3L9rTd";

        // when
        SignInResponse response = SignInResponse.createResponse(token);

        // then
        assertNotNull(response);
        assertEquals(token, response.token());
    }

    @Test
    @DisplayName("token이 null인 경우 로그인 응답 객체 생성에 실패한다.")
    public void failureCreateResponse() {
        // given
        String token = null;

        // when
        assertThrows(UsersException.class,
                () -> SignInResponse.createResponse(token));
    }
}
