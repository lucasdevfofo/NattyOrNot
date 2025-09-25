package com.trabalho.NattyOrNot.controller;

import com.trabalho.NattyOrNot.model.Rat;
import com.trabalho.NattyOrNot.service.RatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rat")
public class RatController {

    @Autowired
    public RatService ratService;

    @PostMapping("/criar")
    public Rat create(@RequestBody Rat rat){
        return ratService.create(rat);
    }

    @GetMapping
    public List<Rat> getAll() {
        return ratService.findAll();
    }

    @GetMapping("/{id}")
    public Rat getById(@PathVariable Integer id) {
        return ratService.findById(id);
    }

}
