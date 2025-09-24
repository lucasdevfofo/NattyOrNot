package com.trabalho.NattyOrNot.repository;

import com.trabalho.NattyOrNot.model.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplementRepository extends JpaRepository<Supplement, Integer> {

    Optional<Supplement> findByName(String name);
}
