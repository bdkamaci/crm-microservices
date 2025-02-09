package com.crm.sales_service;

import com.crm.sales_service.client.CustomerClient;
import com.crm.sales_service.dto.CustomerDto;
import com.crm.sales_service.dto.SaleDto;
import com.crm.sales_service.exception.custom.CustomerNotFoundException;
import com.crm.sales_service.model.Sale;
import com.crm.sales_service.model.enums.Status;
import com.crm.sales_service.repository.SaleRepository;
import com.crm.sales_service.service.impl.SaleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private CustomerClient customerClient;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SaleServiceImpl saleService;

    private Sale sale;
    private SaleDto saleDto;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        sale = new Sale();
        sale.setId(1L);
        sale.setCustomerId(1001L);
        sale.setStatus(Status.PENDING);
        sale.setDate(LocalDateTime.now());
        sale.setUpdatedAt(LocalDateTime.now());

        saleDto = new SaleDto();
        saleDto.setCustomerId(1001L);
        saleDto.setStatus(Status.PENDING);

        customerDto = new CustomerDto();
        customerDto.setId(1001L);
        customerDto.setName("John Doe");
    }

    @Test
    void createSale_ShouldReturnCreatedSaleDto_WhenCustomerExists() {
        when(customerClient.getCustomerById(1001L)).thenReturn(customerDto);
        when(modelMapper.map(saleDto, Sale.class)).thenReturn(sale);
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        when(modelMapper.map(sale, SaleDto.class)).thenReturn(saleDto);

        SaleDto result = saleService.createSale(saleDto);

        assertThat(result).isNotNull();
        assertThat(result.getCustomerId()).isEqualTo(1001L);
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    void createSale_ShouldThrowCustomerNotFoundException_WhenCustomerDoesNotExist() {
        when(customerClient.getCustomerById(1001L)).thenThrow(new CustomerNotFoundException("Invalid customer ID: 1001"));

        assertThatThrownBy(() -> saleService.createSale(saleDto))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Invalid customer ID: 1001");

        verify(saleRepository, never()).save(any(Sale.class));
    }

    @Test
    void getSaleById_ShouldReturnSaleDto_WhenSaleExists() {
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        when(modelMapper.map(sale, SaleDto.class)).thenReturn(saleDto);

        SaleDto result = saleService.getSaleById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getCustomerId()).isEqualTo(1001L);
        verify(saleRepository, times(1)).findById(1L);
    }

    @Test
    void updateSale_ShouldUpdateAndReturnUpdatedSaleDto() {
        SaleDto updatedSaleDto = new SaleDto();
        updatedSaleDto.setCustomerId(1001L);
        updatedSaleDto.setStatus(Status.CLOSED);

        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        when(modelMapper.map(sale, SaleDto.class)).thenReturn(updatedSaleDto);

        SaleDto result = saleService.updateSale(1L, updatedSaleDto);

        assertThat(result.getStatus()).isEqualTo(Status.CLOSED);
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    void deleteSale_ShouldDeleteSaleSuccessfully() {
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));
        doNothing().when(saleRepository).delete(sale);

        saleService.deleteSale(1L);

        verify(saleRepository, times(1)).delete(sale);
    }

    @Test
    void getAllSales_ShouldReturnListOfSaleDtos() {
        List<Sale> sales = List.of(sale);

        when(saleRepository.findAll()).thenReturn(sales);
        when(modelMapper.map(sale, SaleDto.class)).thenReturn(saleDto);

        List<SaleDto> result = saleService.getAllSales();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCustomerId()).isEqualTo(1001L);
        verify(saleRepository, times(1)).findAll();
    }
}