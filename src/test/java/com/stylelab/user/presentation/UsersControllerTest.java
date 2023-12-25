package com.stylelab.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stylelab.common.exception.ServiceExceptionHandler;
import com.stylelab.user.presentation.request.SignupRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.stylelab.common.exception.ServiceError.OK;
import static com.stylelab.user.exception.UsersError.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private UsersController usersController;
    @Autowired
    private ServiceExceptionHandler serviceExceptionHandler;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(usersController)
                .setControllerAdvice(serviceExceptionHandler)
                .build();
    }

    @Nested
    @DisplayName("회원 가입 테스트")
    public class SignupTest {

        @Test
        @DisplayName("회원 가입 성공")
        public void successSignup() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$.code").value(OK.getCode()))
                    .andExpect(jsonPath("$.message").value(OK.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 유효하지 않은 이메일인 경우 회원 가입 실패")
        public void failureSignup_01() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail..com")
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 이메일인 null 인 경우 회원 가입 실패")
        public void failureSignup_02() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(EMAIL_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(EMAIL_IS_REQUIRED.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 유효하지 않은 비밀번호인 경우 회원 가입 실패")
        public void failureSignup_03() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test123")
                    .confirmPassword("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 비밀번호가 null 인 경우 회원 가입 실패")
        public void failureSignup_04() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .confirmPassword("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(PASSWORD_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(PASSWORD_IS_REQUIRED.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 비밀번호 확인 값이 null 인 경우 회원 가입 실패")
        public void failureSignup_05() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(CONFIRM_PASSWORD_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(CONFIRM_PASSWORD_IS_REQUIRED.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 유효하지 않은 이름인 경우 회원 가입 실패")
        public void failureSignup_06() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@")
                    .name("한규빈124")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(NAME_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(NAME_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 이름이 null 인 경우 회원 가입 실패")
        public void failureSignup_07() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(NAME_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(NAME_IS_REQUIRED.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 유효하지 않은 닉네임인 경우 회원 가입 실패")
        public void failureSignup_08() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@")
                    .name("한규빈")
                    .nickname("coby!$!@")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 닉네임이 null 인 경우 회원 가입 실패")
        public void failureSignup_09() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@")
                    .name("한규빈")
                    .phoneNumber("01011111111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(NICKNAME_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(NICKNAME_IS_REQUIRED.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 유효하지 않은 핸드폰 번호인 경우 회원 가입 실패")
        public void failureSignup_10() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("010-11111-111")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(PHONE_NUMBER_IS_NOT_IN_THE_CORRECT_FORMAT.getCode()))
                    .andExpect(jsonPath("$.message").value(PHONE_NUMBER_IS_NOT_IN_THE_CORRECT_FORMAT.getMessage()));
        }

        @Test
        @DisplayName("회원 가입 실패 - 핸드폰 번호가 null 인 경우 회원 가입 실패")
        public void failureSignup_11() throws Exception {
            SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .confirmPassword("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .build();

            mockMvc.perform(post("/v1/users/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signupRequestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(PHONE_NUMBER_IS_REQUIRED.getCode()))
                    .andExpect(jsonPath("$.message").value(PHONE_NUMBER_IS_REQUIRED.getMessage()));
        }
    }
}
