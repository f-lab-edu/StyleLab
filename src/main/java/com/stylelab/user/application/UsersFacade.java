package com.stylelab.user.application;

import com.stylelab.common.security.UserPrincipal;
import com.stylelab.common.security.jwt.JwtTokenProvider;
import com.stylelab.user.domain.UserDeliveryAddress;
import com.stylelab.user.exception.UsersException;
import com.stylelab.user.presentation.request.CreateUserDeliveryAddressRequest;
import com.stylelab.user.presentation.request.SignInRequest;
import com.stylelab.user.presentation.request.SignupRequest;
import com.stylelab.user.presentation.response.ExistsByEmailResponse;
import com.stylelab.user.presentation.response.ExistsByNicknameResponse;
import com.stylelab.user.presentation.response.SignInResponse;
import com.stylelab.user.service.UserDeliveryAddressService;
import com.stylelab.user.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.stylelab.user.exception.UsersError.PASSWORD_VERIFICATION_NOT_MATCH;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsersFacade {

    private final UsersService usersService;
    private final UserDeliveryAddressService userDeliveryAddressService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(final SignupRequest signupRequest) {
        if (!Objects.equals(signupRequest.password(), signupRequest.confirmPassword())) {
            log.error(PASSWORD_VERIFICATION_NOT_MATCH.getMessage());
            throw new UsersException(PASSWORD_VERIFICATION_NOT_MATCH, PASSWORD_VERIFICATION_NOT_MATCH.getMessage());
        }

        String encodePassword = passwordEncoder.encode(signupRequest.password());
        usersService.signup(SignupRequest.toEntity(signupRequest, encodePassword));
    }

    public ExistsByEmailResponse existsByEmail(final String email) {
        return ExistsByEmailResponse.createResponse(usersService.existsByEmail(email));
    }

    public ExistsByNicknameResponse existsByNickname(final String nickname) {
        return ExistsByNicknameResponse.createResponse(usersService.existsByNickname(nickname));
    }

    public SignInResponse signIn(final SignInRequest signInRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(signInRequest.email(), signInRequest.password());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        UserPrincipal principal = (UserPrincipal) authenticationResponse.getPrincipal();
        return SignInResponse.createResponse(jwtTokenProvider.createAuthToken(principal.getEmail(), principal.getUsersRole().name()));
    }

    public void createUserDeliveryAddress(UserPrincipal userPrincipal, CreateUserDeliveryAddressRequest createUserDeliveryAddressRequest) {
        UserDeliveryAddress userDeliveryAddress =
                CreateUserDeliveryAddressRequest.toEntity(userPrincipal.getUsers(), createUserDeliveryAddressRequest);
        userDeliveryAddressService.createUserDeliveryAddress(userDeliveryAddress);
    }
}
