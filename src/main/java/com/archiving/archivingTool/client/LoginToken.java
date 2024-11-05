package com.archiving.archivingTool.client;

import com.archiving.archivingTool.dto.LoginTokenDTO;
import com.archiving.archivingTool.model.LoginTokenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class LoginToken {
    private String bpmServerUrl = "https://bpmsrv:9443/";
    @Autowired
    private RestTemplate restTemplate;
    public String createLoginToken(LoginTokenDTO loginTokenDTO) {

        LoginTokenDetails loginTokenDetails = new LoginTokenDetails();
        String csrfToken;
        String bpmApiUrl = bpmServerUrl + "/bpm/system/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("wasadmin", "wasadminP@ssw0rd");
        HttpEntity<Object> requestEntity = new HttpEntity<>(loginTokenDTO,headers);

        ResponseEntity<LoginTokenDetails> response = restTemplate.exchange(
                bpmApiUrl,
                HttpMethod.POST,
                requestEntity,
//                LoginTokenDetails.class
                new ParameterizedTypeReference<LoginTokenDetails>() {}
        );
        loginTokenDetails = response.getBody();
        csrfToken = loginTokenDetails.getCsrfToken();
        return csrfToken;
    }

}
