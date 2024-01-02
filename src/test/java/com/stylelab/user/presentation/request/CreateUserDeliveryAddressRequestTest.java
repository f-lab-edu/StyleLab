package com.stylelab.user.presentation.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserDeliveryAddressRequestTest {
    private Validator validator;

    @BeforeEach
    public void init() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("배송지 등록 요청 객체의 배송지가 null 인 경우 검증 실패")
    public void failCreateUserDeliveryAddress_01() {
        CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                .addressDetail("addressDetail")
                .postalCode("12345")
                .addressAliases("집")
                .defaultDeliveryAddress(false)
                .build();

        Set<ConstraintViolation<CreateUserDeliveryAddressRequest>> validate = validator.validate(createUserDeliveryAddressRequest);
        assertEquals(1, validate.size());
    }

    @Test
    @DisplayName("배송지 등록 요청 객체의 상세 배송지가 null 인 경우 검증 실패")
    public void failCreateUserDeliveryAddress_02() {
        CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                .address("address")
                .postalCode("12345")
                .addressAliases("집")
                .defaultDeliveryAddress(false)
                .build();

        Set<ConstraintViolation<CreateUserDeliveryAddressRequest>> validate = validator.validate(createUserDeliveryAddressRequest);
        assertEquals(1, validate.size());
    }

    @Test
    @DisplayName("배송지 등록 요청 객체의 상세 우편번호가 null 인 경우 검증 실패")
    public void failCreateUserDeliveryAddress_03() {
        CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                .address("address")
                .addressDetail("addressDetail")
                .addressAliases("집")
                .defaultDeliveryAddress(false)
                .build();

        Set<ConstraintViolation<CreateUserDeliveryAddressRequest>> validate = validator.validate(createUserDeliveryAddressRequest);
        assertEquals(1, validate.size());
    }

    @Test
    @DisplayName("배송지 등록 요청 객체의 상세 주소 별칭 null 인 경우 검증 실패")
    public void failCreateUserDeliveryAddress_04() {
        CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                .address("address")
                .addressDetail("addressDetail")
                .postalCode("12345")
                .defaultDeliveryAddress(false)
                .build();

        Set<ConstraintViolation<CreateUserDeliveryAddressRequest>> validate = validator.validate(createUserDeliveryAddressRequest);
        assertEquals(1, validate.size());
    }

    @Test
    @DisplayName("배송지 등록 요청 객체의 상세 기본 주소지 여부가 null 인 경우 검증 실패")
    public void failCreateUserDeliveryAddress_05() {
        CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest = CreateUserDeliveryAddressRequest.builder()
                .address("address")
                .addressDetail("addressDetail")
                .postalCode("12345")
                .addressAliases("집")
                .build();

        Set<ConstraintViolation<CreateUserDeliveryAddressRequest>> validate = validator.validate(createUserDeliveryAddressRequest);
        assertEquals(1, validate.size());
    }
}
