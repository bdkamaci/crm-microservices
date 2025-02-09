package com.crm.sales_service;

import com.crm.sales_service.controller.SaleController;
import com.crm.sales_service.dto.SaleDto;
import com.crm.sales_service.model.enums.Status;
import com.crm.sales_service.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class SaleControllerTest {

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleController saleController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private SaleDto saleDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(saleController).build();
        objectMapper = new ObjectMapper();
        saleDto = new SaleDto();
        saleDto.setCustomerId(1001L);
        saleDto.setStatus(Status.PENDING);
    }

    @Test
    void createSale_ShouldReturnCreatedSale() throws Exception {
        when(saleService.createSale(any(SaleDto.class))).thenReturn(saleDto);

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1001L))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(saleService, times(1)).createSale(any(SaleDto.class));
    }

    @Test
    void getSaleById_ShouldReturnSale() throws Exception {
        when(saleService.getSaleById(1L)).thenReturn(saleDto);

        mockMvc.perform(get("/sales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1001L))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(saleService, times(1)).getSaleById(1L);
    }

    @Test
    void updateSale_ShouldReturnUpdatedSale() throws Exception {
        SaleDto updatedSale = new SaleDto();
        updatedSale.setCustomerId(1001L);
        updatedSale.setStatus(Status.CLOSED);

        when(saleService.updateSale(eq(1L), any(SaleDto.class))).thenReturn(updatedSale);

        mockMvc.perform(put("/sales/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSale)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CLOSED"));

        verify(saleService, times(1)).updateSale(eq(1L), any(SaleDto.class));
    }

    @Test
    void deleteSale_ShouldReturnNoContent() throws Exception {
        doNothing().when(saleService).deleteSale(1L);

        mockMvc.perform(delete("/sales/1"))
                .andExpect(status().isNoContent());

        verify(saleService, times(1)).deleteSale(1L);
    }

    @Test
    void getAllSales_ShouldReturnListOfSales() throws Exception {
        List<SaleDto> sales = List.of(saleDto);
        when(saleService.getAllSales()).thenReturn(sales);

        mockMvc.perform(get("/sales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(saleService, times(1)).getAllSales();
    }
}

