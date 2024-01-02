package com.stylelab.user.presentation.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignupRequestTest {

    private Validator validator;

    @BeforeEach
    public void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Nested
    @DisplayName("회원 가입 이메일 검증")
    public class EmailValidation {

        private SignupRequest signupRequest;

        @BeforeEach
        public void init() {
            signupRequest = SignupRequest.builder()
                    .password("test12341234!")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();
        }

        @Test
        @DisplayName("정삭적인 이메일 형식으로 검증 성공")
        public void successEmailValidation() {
            signupRequest.setEmail("coby@gmail.com");
            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("이메일 검증 실패 - . 이 두 개인 경우 검증 실패")
        public void failureEmailValidation_01() {
            signupRequest.setEmail("coby@..com");
            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이메일 검증 실패 -  @가 두 개인 경우 검증 실패")
        public void failureEmailValidation_02() {
            signupRequest.setEmail("coby@@gmail.com");
            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이메일 검증 실패 -  이메일이 null 인 경우 검증 실패")
        public void failureEmailValidation_03() {
            signupRequest.setEmail(null);
            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }
    }

    @Nested
    @DisplayName("회원 가입 비밀번호 검증")
    public class PasswordValidation {

        private SignupRequest signupRequest;

        @BeforeEach
        public void init() {
            signupRequest = SignupRequest.builder()
                    .email("coby@gamil.com")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();
        }

        @Test
        @DisplayName("정상적인 비밀번호 형식으로 검증 성공")
        public void successPasswordValidation() {
            signupRequest.setPassword("Test12345678!");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호가 8자리 미만 시 검증 실패")
        public void failurePasswordValidation_01() {
            signupRequest.setPassword("test123");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호가 16자리 이상 시 검증 실패")
        public void failurePasswordValidation_02() {
            signupRequest.setPassword("test123!12344543213153");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 숫자만 들어간 경우 검증 실패")
        public void failurePasswordValidation_03() {
            signupRequest.setPassword("123456789");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 문자만 들어간 경우 검증 실패")
        public void failurePasswordValidation_04() {
            signupRequest.setPassword("testteset");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 특수문자만 들어간 경우 검증 실패")
        public void failurePasswordValidation_05() {
            signupRequest.setPassword("!@#!@#!@#@#!");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 한글이 들어간 경우 검증 실패")
        public void failurePasswordValidation_06() {
            signupRequest.setPassword("테스트테스트테테");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 숫자, 문자만 포함된 경우 검증 실패")
        public void failurePasswordValidation_07() {
            signupRequest.setPassword("test123412");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 특수문자, 문자만 포함된 경우 검증 실패")
        public void failurePasswordValidation_08() {
            signupRequest.setPassword("!@#$!@#123412");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호가 null 인 경우 검증 실패")
        public void failurePasswordValidation_09() {
            signupRequest.setPassword(null);

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }
    }

    @Nested
    @DisplayName("회원 가입 이름 검증")
    public class NameValidation {

        private SignupRequest signupRequest;

        @BeforeEach
        public void init() {
            signupRequest = SignupRequest.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();
        }

        @Test
        @DisplayName("정상적인 이름 형식으로 검증 성공")
        public void successNameValidation() {
            signupRequest.setName("한규빈");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("이름 검증 실패 - 이름에 영문 포함 시 검증 실패")
        public void failureNameValidation_01() {
            signupRequest.setName("coby");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이름 검증 실패 - 이름에 특수문자 포함 시 검증 실패")
        public void failureNameValidation_02() {
            signupRequest.setName("한규빈~");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이름 검증 실패 - 이름에 숫자 포함 시 검증 실패")
        public void failureNameValidation_03() {
            signupRequest.setName("한규빈123");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이름 검증 실패 - 이름이 null 인 경우 검증 실패")
        public void failureNameValidation_04() {
            signupRequest.setName(null);

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }
    }

    @Nested
    @DisplayName("회원 가입 닉네임 검증")
    public class NicknameValidation {

        private SignupRequest signupRequest;

        @BeforeEach
        public void init() {
            signupRequest = SignupRequest.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .name("한규빈")
                    .phoneNumber("01011111111")
                    .build();
        }

        @Test
        @DisplayName("정상적인 닉네임 형식으로 검증 성공")
        public void successNicknameValidation() {
            signupRequest.setNickname("coby12");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("닉네임 검증 실패 - 닉네임에 특수문자 포함된 경우 검증 실패")
        public void failureNicknameValidation_01() {
            signupRequest.setNickname("coby123!@#");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("닉네임 검증 실패 - 닉네임이 null 인 경우 검증 실패")
        public void failureNicknameValidation_02() {
            signupRequest.setNickname(null);

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }
    }

    @Nested
    @DisplayName("회원 가입 핸드폰 번호 검증")
    public class PhoneNumberValidation {

        private SignupRequest signupRequest;

        @BeforeEach
        public void init() {
            signupRequest = SignupRequest.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .build();
        }

        @Test
        @DisplayName("정상적인 핸드폰 번호 형식으로 검증 성공")
        public void successPhoneNumberValidation() {
            signupRequest.setPhoneNumber("01011111111");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("핸드폰 번호 검증 실패 - 핸드폰 번호가 12자리 이상인 경우 검증 실패")
        public void failurePhoneNumberValidation_01() {
            signupRequest.setPhoneNumber("010123456789");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("핸드폰 번호 검증 실패 - 핸드폰 번호에 문자가 포함된 경우 검증 실패")
        public void failurePhoneNumberValidation_02() {
            signupRequest.setPhoneNumber("0101234coby");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("핸드폰 번호 검증 실패 - 핸드폰 번호에 특수문자가 포함된 경우 검증 실패")
        public void failurePhoneNumberValidation_03() {
            signupRequest.setPhoneNumber("0101234-1234");

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("핸드폰 번호 검증 실패 - 핸드폰 번호가 null 인 경우 검증 실패")
        public void failurePhoneNumberValidation_04() {
            signupRequest.setPhoneNumber(null);

            Set<ConstraintViolation<SignupRequest>> validate = validator.validate(signupRequest);
            assertEquals(1, validate.size());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignupRequest {
        @NotBlank(message = "이메일은 필수입니다.")
        @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}", message = "올바르지 않은 형식의 이메일입니다.")
        private String  email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "올바르지 않은 형식의 비밀번호입니다.")
        private String  password;

        @NotBlank(message = "이름은 필수입니다.")
        @Pattern(regexp = "^[가-힣]*$", message = "올바르지 않은 형식의 이름입니다.")
        private String name;

        @NotBlank(message = "닉네임은 필수입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "올바르지 않은 형식의 닉네임입니다.")
        private String nickname;

        @NotBlank(message = "핸드폰 번호는 필수입니다.")
        @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "올바르지 않은 형식의 핸드폰 번호입니다.")
        private String phoneNumber;
    }
}
