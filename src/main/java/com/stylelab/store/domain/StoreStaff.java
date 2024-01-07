package com.stylelab.store.domain;

import com.stylelab.common.base.BaseEntity;
import com.stylelab.store.constant.StoreStaffRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "store_staff")
public class StoreStaff extends BaseEntity  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long storeStaffId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "store_id")
    private Store store;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreStaffRole storeStaffRole;

    @Column(nullable = false)
    private boolean withdrawal;

    private LocalDateTime withdrawalAt;

    @Builder
    public StoreStaff(
            Store store, String email, String password, String name,
            String nickname, String phoneNumber, StoreStaffRole storeStaffRole, boolean withdrawal, LocalDateTime withdrawalAt) {
        this.store = store;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.storeStaffRole = storeStaffRole;
        this.withdrawal = withdrawal;
        this.withdrawalAt = withdrawalAt;
    }

    public void addStore(Store store) {
        this.store = store;
    }
}
