package com.stylelab.common.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    // 등록일, 등록일은 한번 등록하면 수정하지 못 하게 updatable false로 지정
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 수정일
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
