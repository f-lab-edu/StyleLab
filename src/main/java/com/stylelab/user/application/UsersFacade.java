package com.stylelab.user.application;

import com.stylelab.common.security.UserPrincipal;
import com.stylelab.common.security.jwt.JwtTokenProvider;
import com.stylelab.user.exception.UsersException;
import com.stylelab.user.presentation.request.SignInRequest;
import com.stylelab.user.presentation.request.SignupRequest;
import com.stylelab.user.presentation.response.ExistsByEmailResponse;
import com.stylelab.user.presentation.response.ExistsByNicknameResponse;
import com.stylelab.user.presentation.response.SignInResponse;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(final SignupRequest signupRequest) {
        if (!Objects.equals(signupRequest.getPassword(), signupRequest.getConfirmPassword())) {
            log.error(PASSWORD_VERIFICATION_NOT_MATCH.getMessage());
            throw new UsersException(PASSWORD_VERIFICATION_NOT_MATCH, PASSWORD_VERIFICATION_NOT_MATCH.getMessage());
        }

        String encodePassword = passwordEncoder.encode(signupRequest.getPassword());
        usersService.signup(SignupRequest.toEntity(signupRequest, encodePassword));
    }

    public ExistsByEmailResponse existsByEmail(final String email) {
        return new ExistsByEmailResponse(usersService.existsByEmail(email));
    }

    public ExistsByNicknameResponse existsByNickname(final String nickname) {
        return new ExistsByNicknameResponse(usersService.existsByNickname(nickname));
    }

    public SignInResponse signIn(final SignInRequest signInRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(signInRequest.getEmail(), signInRequest.getPassword());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        UserPrincipal principal = (UserPrincipal) authenticationResponse.getPrincipal();
        return new SignInResponse(jwtTokenProvider.createAuthToken(principal.getEmail(), principal.getUsersRole().name()));
    }
}
