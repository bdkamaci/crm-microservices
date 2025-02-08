package com.crm.sales_service.exception;

import com.crm.sales_service.exception.custom.BadRequestException;
import com.crm.sales_service.exception.custom.CustomerNotFoundException;
import com.crm.sales_service.exception.custom.ServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new BadRequestException("Bad Request");
            case 404:
                return new CustomerNotFoundException("Customer not found.");
            case 500:
                return new ServiceUnavailableException("Service unavailable.");
            default:
                return new Exception("Unknown error occurred.");
        }
    }
}
