package com.trabalho.NattyOrNot.service;

import com.trabalho.NattyOrNot.model.Bomb;
import com.trabalho.NattyOrNot.repository.BombRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BombService {


    @Autowired
    public BombRepository bombRepository;

    public Bomb create(@RequestBody Bomb bomb){
        return bombRepository.save(bomb);
    }
}
