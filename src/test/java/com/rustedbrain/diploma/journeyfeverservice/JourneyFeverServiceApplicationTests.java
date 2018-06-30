package com.rustedbrain.diploma.journeyfeverservice;

import com.rustedbrain.diploma.journeyfeverservice.model.ServiceStatusInfo;
import com.rustedbrain.diploma.journeyfeverservice.model.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JourneyFeverServiceApplicationTests {

    private RestTemplate template;

    @Before
    public void initialize() {
        this.template = new RestTemplate();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void status() {
        String url = "http://localhost:8080/rest/status";
        ServiceStatusInfo statusInfo = template.getForEntity(url, ServiceStatusInfo.class).getBody();
        Assert.assertEquals(Status.OK, statusInfo.getStatus());
    }

    @Test
    public void login() {
        String url = "http://localhost:8080/rest/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic QWxhZGRpbupvcRVuIHNlc2FtZQ=="); //here is some login and pass like this login:pass
        HttpEntity<String> request = new HttpEntity<>(headers);
        Boolean myclass = template.exchange(url, HttpMethod.POST, request, Boolean.class).getBody();
    }
}
