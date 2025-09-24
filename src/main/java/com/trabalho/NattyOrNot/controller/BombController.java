package com.trabalho.NattyOrNot.controller;


import com.trabalho.NattyOrNot.model.Bomb;
import com.trabalho.NattyOrNot.service.BombService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bomb")
public class BombController {
    @Autowired
    public BombService bombService;


    @PostMapping("/criar")
    public Bomb create(@RequestBody Bomb bomb){
        return bombService.create(bomb);
    }
}
