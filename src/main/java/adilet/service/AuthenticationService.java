package adilet.service;

import adilet.dto.request.SignInRequest;
import adilet.dto.request.SignUpRequest;
import adilet.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse signUp(SignUpRequest signUpRequest);
    AuthenticationResponse signIn(SignInRequest signInRequest);
}
