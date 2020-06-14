package com.assignment.transaction.entity;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    private String sourceType;

    private BigDecimal sourceDebit;

    private String targetType;

    private BigDecimal targetAmount;




}