package com.myorganisation.gcafe.model;

import com.myorganisation.gcafe.enums.Cuisine;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "chef")
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double experience;

    @Enumerated(EnumType.STRING)
    private Cuisine cuisine;

}
