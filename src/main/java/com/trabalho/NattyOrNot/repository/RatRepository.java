package com.trabalho.NattyOrNot.repository;

import com.trabalho.NattyOrNot.model.Rat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface RatRepository extends JpaRepository<Rat,Integer> {
    List<Rat> findBySupplements_Id(Integer supplementId);
    List<Rat> findByBomb_Id(Integer bombId);
}
