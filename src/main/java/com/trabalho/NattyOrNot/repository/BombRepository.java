package com.trabalho.NattyOrNot.repository;

import com.trabalho.NattyOrNot.model.Bomb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BombRepository extends JpaRepository<Bomb, Integer> {

    Optional<Bomb> findByName(String name);
}
