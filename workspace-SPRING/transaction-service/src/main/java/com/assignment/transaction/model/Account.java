package com.assignment.transaction.model;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Long id;


    private String accountType;


    private String userName;


    private BigDecimal debit;

}