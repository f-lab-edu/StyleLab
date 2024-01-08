package com.stylelab.store.application;

import com.stylelab.common.security.principal.StorePrincipal;
import com.stylelab.file.application.FileFacade;
import com.stylelab.file.constant.ImageType;
import com.stylelab.file.exception.FileException;
import com.stylelab.store.constant.StoreStaffRole;
import com.stylelab.store.domain.Store;
import com.stylelab.store.domain.StoreStaff;
import com.stylelab.store.exception.StoreError;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StoreFacadeTest {

    @Mock
    private StoreService storeService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private FileFacade fileFacade;

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

    @Nested
    @DisplayName("이미지 업로드 테스트")
    public class UploadMultipartFilesTest {

        @Test
        @DisplayName("이미지 업로드 테스트 실패 - StorePrincipal의 storeId와 parameter의 storeId가 다른 경우 StoreException(FORBIDDEN_STORE)이 발생한다.")
        public void test() throws Exception {
            //given
            Long storeId = 1L;
            Store store = Store.createStore(2L);
            StoreStaff storeStaff = StoreStaff.builder()
                    .email("test@gmail.com")
                    .password("tes1234!@#$!")
                    .storeStaffRole(StoreStaffRole.ROLE_STORE_OWNER)
                    .build();
            StorePrincipal storePrincipal = StorePrincipal.create(store, storeStaff);
            ImageType imageType = ImageType.PRODUCT_ENTRY_SUB;
            List<MultipartFile> multipartFiles = Arrays.asList(
                    new MockMultipartFile("file", "mock_file1.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file2.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'})
            );

            //when
            StoreException storeException = assertThrows(StoreException.class,
                    () -> storeFacade.uploadMultipartFiles(storePrincipal, storeId, imageType, multipartFiles));

            //then
            verify(fileFacade, times(0))
                    .uploadMultipartFiles(any(), anyList());
            assertEquals(StoreError.FORBIDDEN_STORE.getCode(), storeException.getServiceError().getCode());
        }
    }
}
