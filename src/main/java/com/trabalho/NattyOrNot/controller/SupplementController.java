package com.trabalho.NattyOrNot.controller;

import com.trabalho.NattyOrNot.model.Bomb;
import com.trabalho.NattyOrNot.model.Supplement;
import com.trabalho.NattyOrNot.service.SupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplement")
public class SupplementController {

    @Autowired
    public SupplementService supplementService;

    @PostMapping("/criar")
    public ResponseEntity<Supplement> create(@RequestBody Supplement supplement) {
        Supplement supplementSaved = supplementService.create(supplement);
        return ResponseEntity.status(HttpStatus.CREATED).body(supplementSaved);
    }
    @GetMapping
    public List<Supplement> getAll() {
        return supplementService.findAll();
    }

    @GetMapping("/{id}")
    public Supplement getById(@PathVariable Integer id) {
        return supplementService.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        supplementService.deleteById(id);
    }
    @DeleteMapping
    public void deleteAll(){
        supplementService.deleteAll();
    }

}
