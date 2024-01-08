package com.stylelab.store.domain;

import com.stylelab.common.base.BaseEntity;
import com.stylelab.store.constant.ApproveType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "stores")
public class Store extends BaseEntity  {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String businessLicenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApproveType approveType;

    @OneToMany(mappedBy = "store", cascade = CascadeType.PERSIST)
    List<StoreStaff> storeStaffs = new ArrayList<>();

    private Store(Long storeId) {
        this.storeId = storeId;
    }

    @Builder
    public Store(
            String brand, String name, String address, String addressDetail, String postalCode,
            String businessLicenseNumber, ApproveType approveType) {
        this.brand = brand;
        this.name = name;
        this.address = address;
        this.addressDetail = addressDetail;
        this.postalCode = postalCode;
        this.businessLicenseNumber = businessLicenseNumber;
        this.approveType = approveType;
    }

    public void addStoreStaff(StoreStaff storeStaff) {
        storeStaff.addStore(this);
        this.storeStaffs.add(storeStaff);
    }

    public static Store createStore(Long storeId) {
        return new Store(storeId);
    }
}
