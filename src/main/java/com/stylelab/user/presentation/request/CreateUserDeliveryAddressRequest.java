package com.stylelab.user.presentation.request;

import com.stylelab.user.domain.UserDeliveryAddress;
import com.stylelab.user.domain.Users;
import com.stylelab.user.exception.UsersError;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateUserDeliveryAddressRequest(
        @NotBlank(message = "DELIVERY_ADDRESS_REQUIRE", payload = UsersError.class)
        String address,
        @NotBlank(message = "DELIVERY_ADDRESS_DETAIL_REQUIRE", payload = UsersError.class)
        String addressDetail,
        @NotBlank(message = "DELIVERY_POSTAL_CODE_REQUIRE", payload = UsersError.class)
        String postalCode,
        @NotBlank(message = "DELIVERY_ADDRESS_ALIASES_REQUIRE", payload = UsersError.class)
        String addressAliases,
        @NotNull(message = "DELIVERY_DEFAULT_ADDRESS_REQUIRE", payload = UsersError.class)
        Boolean defaultDeliveryAddress
) {

    public static UserDeliveryAddress toEntity(
            Users users,
            CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest) {
        return UserDeliveryAddress.builder()
                .users(users)
                .address(createUserDeliveryAddressRequest.address())
                .addressDetail(createUserDeliveryAddressRequest.addressDetail())
                .postalCode(createUserDeliveryAddressRequest.postalCode())
                .addressAliases(createUserDeliveryAddressRequest.addressAliases())
                .defaultDeliveryAddress(createUserDeliveryAddressRequest.defaultDeliveryAddress())
                .build();
    }

    @Override
    public String toString() {
        return "CreateUserDeliveryAddressRequest{" +
                "address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", addressAliases='" + addressAliases + '\'' +
                ", defaultDeliveryAddress=" + defaultDeliveryAddress +
                '}';
    }
}
