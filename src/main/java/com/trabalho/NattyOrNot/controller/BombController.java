package com.trabalho.NattyOrNot.controller;


import com.trabalho.NattyOrNot.model.Bomb;
import com.trabalho.NattyOrNot.service.BombService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bomb")
public class BombController {
    @Autowired
    public BombService bombService;


    @PostMapping("/criar")
    public Bomb create(@RequestBody Bomb bomb){
        return bombService.create(bomb);
    }

    @GetMapping
    public List<Bomb> getAll() {
        return bombService.findAll();
    }

    @GetMapping("/{id}")
    public Bomb getById(@PathVariable Integer id) {
        return bombService.findById(id);
    }

    @PutMapping("/{id}")
    public Bomb update(@PathVariable Integer id, @RequestBody Bomb bombDetails) {
        return bombService.update(id, bombDetails);
    }

    @PatchMapping("/{id}")
    public Bomb patch(@PathVariable Integer id, @RequestBody Bomb bombDetails) {
        return bombService.patch(id, bombDetails);
    }
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        bombService.deleteById(id);
    }
    @DeleteMapping
    public void deleteAll(){
        bombService.deleteAll();
    }

}
