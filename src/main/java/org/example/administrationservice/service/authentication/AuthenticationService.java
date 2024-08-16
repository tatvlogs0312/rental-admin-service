package org.example.administrationservice.service.authentication;

import org.example.administrationservice.models.auth.login.LoginReqDTO;
import org.example.administrationservice.models.auth.login.LoginResDTO;
import org.example.administrationservice.models.auth.register.NewUserReqDTO;

public interface AuthenticationService {

    LoginResDTO login(LoginReqDTO req);

    void createNewUser(NewUserReqDTO req);
}
