package com.crm.sales_service.model;

import com.crm.sales_service.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Getter
@Setter
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Status status;

    private LocalDateTime date;

    private LocalDateTime updatedAt;
}
