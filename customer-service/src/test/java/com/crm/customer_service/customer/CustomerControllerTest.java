package com.crm.customer_service.customer;

import com.crm.customer_service.controller.CustomerController;
import com.crm.customer_service.dto.CustomerDto;
import com.crm.customer_service.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setFirstName("John");
        customerDto.setLastName("Doe");
        customerDto.setEmail("john.doe@example.com");
        customerDto.setPhone("1234567890");
        customerDto.setCompany("Test Company");
    }

    @Test
    void createCustomer_Success() throws Exception {
        when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(customerDto);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(customerDto.getId()))
                .andExpect(jsonPath("$.firstName").value(customerDto.getFirstName()))
                .andExpect(jsonPath("$.email").value(customerDto.getEmail()));

        verify(customerService).createCustomer(any(CustomerDto.class));
    }

    @Test
    void createCustomer_ValidationFailure() throws Exception {
        CustomerDto invalidCustomer = new CustomerDto();
        // Missing required fields

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCustomer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCustomer_Success() throws Exception {
        when(customerService.updateCustomer(eq(1L), any(CustomerDto.class))).thenReturn(customerDto);

        mockMvc.perform(put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerDto.getId()))
                .andExpect(jsonPath("$.firstName").value(customerDto.getFirstName()));

        verify(customerService).updateCustomer(eq(1L), any(CustomerDto.class));
    }

    @Test
    void getCustomerById_Success() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(customerDto);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerDto.getId()))
                .andExpect(jsonPath("$.firstName").value(customerDto.getFirstName()));

        verify(customerService).getCustomerById(1L);
    }

    @Test
    void getAllCustomers_Success() throws Exception {
        List<CustomerDto> customers = Arrays.asList(customerDto);
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(customerDto.getId()))
                .andExpect(jsonPath("$[0].firstName").value(customerDto.getFirstName()));

        verify(customerService).getAllCustomers();
    }

    @Test
    void deleteCustomer_Success() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(1L);
    }
}
