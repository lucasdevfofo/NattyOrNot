package com.trabalho.NattyOrNot.service;

import com.trabalho.NattyOrNot.model.Bomb;
import com.trabalho.NattyOrNot.repository.BombRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BombService {


    @Autowired
    public BombRepository bombRepository;

    public Bomb create(@RequestBody Bomb bomb){
        return bombRepository.save(bomb);
    }

    public List<Bomb> findAll() {
        return bombRepository.findAll();
    }

    public Bomb findById(Integer id) {
        return bombRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bomb não encontrada com id " + id));
    }
    public void deleteById(Integer id){
        bombRepository.deleteById(id);
    }
    public void deleteAll(){
        bombRepository.deleteAll();
    }
}
