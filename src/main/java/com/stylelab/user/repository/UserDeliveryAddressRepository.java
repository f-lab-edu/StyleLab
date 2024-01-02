package com.stylelab.user.repository;

import com.stylelab.user.domain.UserDeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeliveryAddressRepository extends JpaRepository<UserDeliveryAddress, Long> {
}
