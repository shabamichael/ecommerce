package com.michael.ecommerce.product;

import com.michael.ecommerce.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductClient {

   @Value("${application.config.product-url}")
    private String productServiceUrl ;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequest>> requestEntity =
                new HttpEntity<List<PurchaseRequest>>(purchaseRequestBody, headers);


        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
            new ParameterizedTypeReference<List<PurchaseResponse>>() {};

        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productServiceUrl + "/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType
        );
        if(responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occured while processing the products" +
                    " purchase " + responseEntity.getStatusCode());
        }
        return responseEntity.getBody();
    }
}
