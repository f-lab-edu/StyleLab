package com.stylelab.store.repository;

import com.stylelab.store.domain.StoreStaff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreStaffRepository extends JpaRepository<StoreStaff, Long> {

    Optional<StoreStaff> findByEmail(String email);
}
