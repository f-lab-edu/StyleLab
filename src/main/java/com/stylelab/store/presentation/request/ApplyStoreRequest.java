package com.stylelab.store.presentation.request;


import com.stylelab.store.constant.ApproveType;
import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.exception.StoreError;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record ApplyStoreRequest(
        @Valid
        @NotNull(message = "STORE_REQUIRE", payload = StoreError.class)
        StoreRequest store,

        @Valid
        @NotNull(message = "STORE_STAFF_REQUIRE", payload = StoreError.class)
        StoreStaffRequest storeStaff) {

    @Builder
    public record StoreRequest(
            @NotBlank(message = "BRAND_NAME_REQUIRE", payload = StoreError.class)
            @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "BRAND_IS_NOT_IN_THE_CORRECT_FORMAT", payload = StoreError.class)
            String brand,
            @NotBlank(message = "BUSINESS_NAME_REQUIRE", payload = StoreError.class)
            @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "BUSINESS_NAME_IS_NOT_IN_THE_CORRECT_FORMAT", payload = StoreError.class)
            String name,
            @NotBlank(message = "BUSINESS_ADDRESS_REQUIRE", payload = StoreError.class)
            String address,
            @NotBlank(message = "BUSINESS_ADDRESS_DETAIL_REQUIRE", payload = StoreError.class)
            String addressDetail,
            @NotBlank(message = "BUSINESS_POSTAL_CODE_REQUIRE", payload = StoreError.class)
            String postalCode,
            @NotBlank(message = "BUSINESS_LICENSE_NUMBER_REQUIRE", payload = StoreError.class)
            String businessLicenseNumber) {

        public static Store toEntity(StoreRequest storeRequest) {
            return Store.builder()
                    .brand(storeRequest.brand)
                    .name(storeRequest.name)
                    .address(storeRequest.address)
                    .addressDetail(storeRequest.addressDetail)
                    .postalCode(storeRequest.postalCode)
                    .businessLicenseNumber(storeRequest.businessLicenseNumber)
                    .approveType(ApproveType.APPROVE)
                    .build();
        }

        @Override
        public String toString() {
            return  """
               Store{
                   brand='%s',
                   name='%s',
                   address='%s',
                   addressDetail='%s',
                   postalCode='%s',
                   businessLicenseNumber='%s'
               }
           """.formatted(brand, name, address, addressDetail, postalCode, businessLicenseNumber);
        }
    }

    @Builder
    public record StoreStaffRequest(
            @NotBlank(message = "STORE_STAFF_EMAIL_REQUIRE", payload = StoreError.class)
            @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}", message = "EMAIL_IS_NOT_IN_THE_CORRECT_FORMAT", payload = StoreError.class)
            String email,
            @NotBlank(message = "STORE_STAFF_PASSWORD_REQUIRE", payload = StoreError.class)
            @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT", payload = StoreError.class)
            String password,
            @NotBlank(message = "STORE_STAFF_CONFIRM_PASSWORD_REQUIRE", payload = StoreError.class)
            @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$", message = "PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT", payload = StoreError.class)
            String confirmPassword,
            @NotBlank(message = "STORE_STAFF_NAME_REQUIRE", payload = StoreError.class)
            @Pattern(regexp = "^[가-힣]*$", message = "NAME_IS_NOT_IN_THE_CORRECT_FORMAT", payload = StoreError.class)
            String name,
            @NotBlank(message = "STORE_STAFF_NICKNAME_REQUIRE", payload = StoreError.class)
            @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "NICKNAME_IS_NOT_IN_THE_CORRECT_FORMAT", payload = StoreError.class)
            String nickname,
            @NotBlank(message = "STORE_STAFF_PHONE_NUMBER_REQUIRE", payload = StoreError.class)
            @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", message = "PHONE_NUMBER_IS_NOT_IN_THE_CORRECT_FORMAT", payload = StoreError.class)
            String phoneNumber) {

        public static StoreStaff toEntity(StoreStaffRequest storeStaffRequest, String encodePassword) {
            return StoreStaff.builder()
                    .email(storeStaffRequest.email)
                    .password(encodePassword)
                    .name(storeStaffRequest.name)
                    .nickname(storeStaffRequest.nickname)
                    .phoneNumber(storeStaffRequest.nickname)
                    .storeStaffRole(StoreStaffRole.STORE_OWNER)
                    .build();
        }

        @Override
        public String toString() {
            return """
               StoreStaff{
                   email='%s',
                   password='%s',
                   confirmPassword='%s',
                   name='%s',
                   nickname='%s',
                   phoneNumber='%s'
               }
           """.formatted(email, password, confirmPassword, name, nickname, phoneNumber);
        }
    }

    @Override
    public String toString() {
        return """
            ApplyStoreRequest {
                 %s,
                 %s
            }
            """.formatted(store, storeStaff);
    }
}
