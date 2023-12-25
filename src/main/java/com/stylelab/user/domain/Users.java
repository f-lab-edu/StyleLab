package com.stylelab.user.domain;

import com.stylelab.common.base.BaseEntity;
import com.stylelab.user.constant.UsersRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity(name = "users")
public class Users extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long userId;
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
    private UsersRole role;
    @Column(nullable = false)
    private boolean withdrawal;
    private LocalDateTime withdrawalAt;

    @Builder
    public Users(
            String email, String password, String name, String nickname,
            String phoneNumber, UsersRole role, boolean withdrawal, LocalDateTime withdrawalAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.withdrawal = withdrawal;
        this.withdrawalAt = withdrawalAt;
    }
}
