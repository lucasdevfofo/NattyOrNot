package com.trabalho.NattyOrNot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private boolean useBomb;
    private boolean useSupplement;

    @ManyToMany
    @JoinTable(
            name = "rat_supplements",
            joinColumns = @JoinColumn(name = "rat_id"),
            inverseJoinColumns = @JoinColumn(name = "supplement_id")
    )
    private List<Supplement> supplements;

    @ManyToOne
    @JoinColumn(name = "bomb_id")
    private Bomb bomb;

    private String apLocation;
}