package com.stylelab.user.presentation.request;

import com.stylelab.user.domain.UserDeliveryAddress;
import com.stylelab.user.domain.Users;
import com.stylelab.user.exception.UsersError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDeliveryAddressRequest {

    @NotBlank(message = "DELIVERY_ADDRESS_REQUIRE", payload = UsersError.class)
    private String address;
    @NotBlank(message = "DELIVERY_ADDRESS_DETAIL_REQUIRE", payload = UsersError.class)
    private String addressDetail;
    @NotBlank(message = "DELIVERY_POSTAL_CODE_REQUIRE", payload = UsersError.class)
    private String postalCode;
    @NotBlank(message = "DELIVERY_ADDRESS_ALIASES_REQUIRE", payload = UsersError.class)
    private String addressAliases;
    @NotNull(message = "DELIVERY_DEFAULT_ADDRESS_REQUIRE", payload = UsersError.class)
    private Boolean defaultDeliveryAddress;

    public static UserDeliveryAddress toEntity(
            Users users,
            CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest) {
        return UserDeliveryAddress.builder()
                .users(users)
                .address(createUserDeliveryAddressRequest.getAddress())
                .addressDetail(createUserDeliveryAddressRequest.getAddressDetail())
                .postalCode(createUserDeliveryAddressRequest.getPostalCode())
                .addressAliases(createUserDeliveryAddressRequest.getAddressAliases())
                .defaultDeliveryAddress(createUserDeliveryAddressRequest.getDefaultDeliveryAddress())
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
