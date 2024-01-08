package com.stylelab.store.service;

import com.stylelab.store.constant.ApproveType;
import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.exception.StoreError;
import com.stylelab.store.exception.StoreException;
import com.stylelab.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Nested
    @DisplayName("스토어, 스토어 스태프 등록 테스트")
    public class ApplyStoreTest {

        private Store store;
        private StoreStaff storeStaff;

        @BeforeEach
        public void init() {
            store = Store.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .approveType(ApproveType.APPROVE)
                    .build();

            storeStaff = StoreStaff.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .storeStaffRole(StoreStaffRole.ROLE_STORE_OWNER)
                    .withdrawal(false)
                    .build();

            store.addStoreStaff(storeStaff);
        }

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프의 중복된 이메일론 인하여 스토어와 스토어 스태프의 등록에 실패한다.")
        public void failureApplyStore_01(){
            //given
            given(storeRepository.save(any()))
                    .willThrow(new DataIntegrityViolationException("could not execute statement [Duplicate entry 'test@gmail.com' for key 'store_staff.idx_store_staff_email']"));

            //when
            StoreException storeException = assertThrows(StoreException.class,
                    () -> storeService.applyStore(store));

            //then
            verify(storeRepository, times(1))
                    .save(any());
            assertEquals(StoreError.STORE_AND_STORE_STAFF_SAVE_FAIL.getCode(), storeException.getServiceError().getCode());
        }
    }

}
