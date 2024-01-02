package com.stylelab.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_delivery_address")
public class UserDeliveryAddress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userAddressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String addressAliases;

    @Column(nullable = false)
    private Boolean defaultDeliveryAddress;

    @Builder
    public UserDeliveryAddress(
            Users users, String address, String addressDetail,
            String postalCode, String addressAliases, Boolean defaultDeliveryAddress) {
        this.users = users;
        this.address = address;
        this.addressDetail = addressDetail;
        this.postalCode = postalCode;
        this.addressAliases = addressAliases;
        this.defaultDeliveryAddress = defaultDeliveryAddress;
    }
}
