package com.stylelab.user.presentation.request;

import lombok.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignupRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Nested
    @DisplayName("회원 가입 이메일 검증")
    public class EmailValidation {

        private SignupRequestDto signupRequestDto;

        @BeforeEach
        public void init() {
            signupRequestDto = SignupRequestDto.builder()
                    .password("test12341234!")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();
        }

        @Test
        @DisplayName("정삭적인 이메일 형식으로 검증 성공")
        public void successEmailValidation() {
            signupRequestDto.setEmail("coby@gmail.com");
            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("이메일 검증 실패 - . 이 두 개인 경우 검증 실패")
        public void failureEmailValidation_01() {
            signupRequestDto.setEmail("coby@..com");
            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이메일 검증 실패 -  @가 두 개인 경우 검증 실패")
        public void failureEmailValidation_02() {
            signupRequestDto.setEmail("coby@@gmail.com");
            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이메일 검증 실패 -  이메일이 null 인 경우 검증 실패")
        public void failureEmailValidation_03() {
            signupRequestDto.setEmail(null);
            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }
    }

    @Nested
    @DisplayName("회원 가입 비밀번호 검증")
    public class PasswordValidation {

        private SignupRequestDto signupRequestDto;

        @BeforeEach
        public void init() {
            signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gamil.com")
                    .name("한규빈")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();
        }

        @Test
        @DisplayName("정상적인 비밀번호 형식으로 검증 성공")
        public void successPasswordValidation() {
            signupRequestDto.setPassword("Test12345678!");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호가 8자리 미만 시 검증 실패")
        public void failurePasswordValidation_01() {
            signupRequestDto.setPassword("test123");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호가 16자리 이상 시 검증 실패")
        public void failurePasswordValidation_02() {
            signupRequestDto.setPassword("test123!12344543213153");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 숫자만 들어간 경우 검증 실패")
        public void failurePasswordValidation_03() {
            signupRequestDto.setPassword("123456789");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 문자만 들어간 경우 검증 실패")
        public void failurePasswordValidation_04() {
            signupRequestDto.setPassword("testteset");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 특수문자만 들어간 경우 검증 실패")
        public void failurePasswordValidation_05() {
            signupRequestDto.setPassword("!@#!@#!@#@#!");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 한글이 들어간 경우 검증 실패")
        public void failurePasswordValidation_06() {
            signupRequestDto.setPassword("테스트테스트테테");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 숫자, 문자만 포함된 경우 검증 실패")
        public void failurePasswordValidation_07() {
            signupRequestDto.setPassword("test123412");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호에 특수문자, 문자만 포함된 경우 검증 실패")
        public void failurePasswordValidation_08() {
            signupRequestDto.setPassword("!@#$!@#123412");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("비밀번호 검증 실패 - 비밀번호가 null 인 경우 검증 실패")
        public void failurePasswordValidation_09() {
            signupRequestDto.setPassword(null);

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }
    }

    @Nested
    @DisplayName("회원 가입 이름 검증")
    public class NameValidation {

        private SignupRequestDto signupRequestDto;

        @BeforeEach
        public void init() {
            signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .nickname("coby")
                    .phoneNumber("01011111111")
                    .build();
        }

        @Test
        @DisplayName("정상적인 이름 형식으로 검증 성공")
        public void successNameValidation() {
            signupRequestDto.setName("한규빈");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("이름 검증 실패 - 이름에 영문 포함 시 검증 실패")
        public void failureNameValidation_01() {
            signupRequestDto.setName("coby");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이름 검증 실패 - 이름에 특수문자 포함 시 검증 실패")
        public void failureNameValidation_02() {
            signupRequestDto.setName("한규빈~");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이름 검증 실패 - 이름에 숫자 포함 시 검증 실패")
        public void failureNameValidation_03() {
            signupRequestDto.setName("한규빈123");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("이름 검증 실패 - 이름이 null 인 경우 검증 실패")
        public void failureNameValidation_04() {
            signupRequestDto.setName(null);

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }
    }

    @Nested
    @DisplayName("회원 가입 닉네임 검증")
    public class NicknameValidation {

        private SignupRequestDto signupRequestDto;

        @BeforeEach
        public void init() {
            signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .name("한규빈")
                    .phoneNumber("01011111111")
                    .build();
        }

        @Test
        @DisplayName("정상적인 닉네임 형식으로 검증 성공")
        public void successNicknameValidation() {
            signupRequestDto.setNickname("coby12");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("닉네임 검증 실패 - 닉네임에 특수문자 포함된 경우 검증 실패")
        public void failureNicknameValidation_01() {
            signupRequestDto.setNickname("coby123!@#");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("닉네임 검증 실패 - 닉네임이 null 인 경우 검증 실패")
        public void failureNicknameValidation_02() {
            signupRequestDto.setNickname(null);

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }
    }

    @Nested
    @DisplayName("회원 가입 핸드폰 번호 검증")
    public class PhoneNumberValidation {

        private SignupRequestDto signupRequestDto;

        @BeforeEach
        public void init() {
            signupRequestDto = SignupRequestDto.builder()
                    .email("coby@gmail.com")
                    .password("test1234123!@")
                    .name("한규빈")
                    .nickname("coby")
                    .build();
        }

        @Test
        @DisplayName("정상적인 핸드폰 번호 형식으로 검증 성공")
        public void successPhoneNumberValidation() {
            signupRequestDto.setPhoneNumber("01011111111");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(0, validate.size());
        }

        @Test
        @DisplayName("핸드폰 번호 검증 실패 - 핸드폰 번호가 12자리 이상인 경우 검증 실패")
        public void failurePhoneNumberValidation_01() {
            signupRequestDto.setPhoneNumber("010123456789");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("핸드폰 번호 검증 실패 - 핸드폰 번호에 문자가 포함된 경우 검증 실패")
        public void failurePhoneNumberValidation_02() {
            signupRequestDto.setPhoneNumber("0101234coby");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("핸드폰 번호 검증 실패 - 핸드폰 번호에 특수문자가 포함된 경우 검증 실패")
        public void failurePhoneNumberValidation_03() {
            signupRequestDto.setPhoneNumber("0101234-1234");

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }

        @Test
        @DisplayName("핸드폰 번호 검증 실패 - 핸드폰 번호가 null 인 경우 검증 실패")
        public void failurePhoneNumberValidation_04() {
            signupRequestDto.setPhoneNumber(null);

            Set<ConstraintViolation<SignupRequestDto>> validate = validator.validate(signupRequestDto);
            assertEquals(1, validate.size());
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignupRequestDto {
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
