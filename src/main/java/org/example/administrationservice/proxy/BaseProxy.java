package org.example.administrationservice.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class BaseProxy {

    private final RestTemplate restTemplate = new RestTemplate();

    protected HttpHeaders initHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        return headers;
    }

    protected HttpHeaders initHeader(String token, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.setBearerAuth(token);
        return headers;
    }

    protected HttpHeaders initHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.setBearerAuth(token);
        return headers;
    }

    protected HttpHeaders initHeaderFormData() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    protected HttpHeaders initHeaderFormUrlencoded() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    protected <T> Object get(String api, Class<T> clazz) {
        HttpEntity<String> entity = new HttpEntity<>(initHeader());
        ResponseEntity<T> response = restTemplate.exchange(api, HttpMethod.GET, entity, clazz);
        return response.getBody();
    }

    public <T> T get(String api, HttpHeaders headers, Class<T> clazz) {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<T> response = restTemplate.exchange(api, HttpMethod.GET, entity, clazz);
        return response.getBody();
    }

    protected <T> T post(String api, HttpHeaders headers, Object payload, Class<T> clazz) {
        final HttpEntity<Object> entity = new HttpEntity<>(payload, headers);
        ResponseEntity<T> response = restTemplate.exchange(api, HttpMethod.POST, entity, clazz);
        return response.getBody();
    }

//    protected <T> T post(String api, HttpHeaders headers, MultiValueMap<String, String> payload, Class<T> clazz) {
//        try {
//            final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(payload, headers);
//            ResponseEntity<T> response = restTemplate.exchange(api, HttpMethod.POST, entity, clazz);
//            return response.getBody();
//        } catch (Exception ex) {
//            log.info(ex.getMessage());
//        }
//        return null;
//    }

    protected <T> T post(String api, Object payload, Class<T> clazz) {
        HttpEntity<Object> entity = new HttpEntity<>(payload);
        ResponseEntity<T> response = restTemplate.exchange(api, HttpMethod.POST, entity, clazz);
        return response.getBody();
    }

    protected <T> T post(String api, HttpHeaders headers, MultiValueMap<Object, Object> multiValueMap, Class<T> clazz) {
        HttpEntity<?> entity = new HttpEntity<>(multiValueMap, headers);
        ResponseEntity<T> response = restTemplate.exchange(api, HttpMethod.POST, entity, clazz);
        return response.getBody();
    }
}
