package com.stylelab.store.repository;

import com.stylelab.store.domain.StoreStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreStaffRepository extends JpaRepository<StoreStaff, Long> {
}
