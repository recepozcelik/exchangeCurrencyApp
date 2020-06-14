package com.assignment.account;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "debit")
    private BigDecimal debit;

}