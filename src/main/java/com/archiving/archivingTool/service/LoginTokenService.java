package com.archiving.archivingTool.service;

import com.archiving.archivingTool.dto.LoginTokenDTO;
import com.archiving.archivingTool.client.LoginToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginTokenService {

    @Autowired
    LoginToken loginToken;

    public String createLoginToken(LoginTokenDTO loginTokenDTO){
        return loginToken.createLoginToken(loginTokenDTO);
    }
}
