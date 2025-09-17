package com.myorganisation.gcafe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myorganisation.gcafe.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount = 0D;
    private Boolean isPaid = true;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    @OneToOne
    @JoinColumn(name = "chef")
    @JsonIgnore
    private Chef chef;
}
