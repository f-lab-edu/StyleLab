ㅈpackage com.stylelab.store.application;

import com.stylelab.store.exception.StoreException;
import com.stylelab.store.presentation.request.ApplyStoreRequest;
import com.stylelab.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StoreFacadeTest {

    @Mock
    private StoreService storeService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StoreFacade storeFacade;

    @Nested
    @DisplayName("스토어, 스토어 스태프 등록 테스트")
    public class ApplyStoreTest {

        @Test
        @DisplayName("스토어, 스토어 스태프 등록 실패 - 스토어 스태프의 password, confirmPassword가 다를 시 StoreException(StoreError.PASSWORD_IS_NOT_IN_THE_CORRECT_FORMAT)이 발생한다.")
        public void test(){
            //given
            ApplyStoreRequest.StoreRequest storeRequest = ApplyStoreRequest.StoreRequest.builder()
                    .brand("coby")
                    .name("coby")
                    .address("경기도 성남시 분당구 정자일로 95")
                    .addressDetail("안내데스크")
                    .postalCode("13561")
                    .businessLicenseNumber("1017777777")
                    .build();

           ApplyStoreRequest.StoreStaffRequest storeStaffRequest = ApplyStoreRequest.StoreStaffRequest.builder()
                    .name("한규빈")
                    .email("test@naver.com")
                    .password("test1234!@#!")
                    .confirmPassword("test1234!123")
                    .phoneNumber("01012341234")
                    .nickname("coby")
                    .build();
           ApplyStoreRequest applyStoreRequest = new ApplyStoreRequest(storeRequest, storeStaffRequest);

            //when
            assertThrows(StoreException.class,
                    () -> storeFacade.applyStore(applyStoreRequest));

            //then
            verify(passwordEncoder, times(0))
                    .encode(any());
            verify(storeService, times(0))
                    .applyStore(any());
        }
    }
}
