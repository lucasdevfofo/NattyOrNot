package com.trabalho.NattyOrNot.controller;

import com.trabalho.NattyOrNot.model.Rat;
import com.trabalho.NattyOrNot.service.RatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rats")
public class RatController {

    @Autowired
    public RatService ratService;

    @PostMapping
    public ResponseEntity<Rat> create(@RequestBody Rat rat) {
        Rat ratSaved = ratService.create(rat);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratSaved);
    }

    @GetMapping
    public ResponseEntity<List<Rat>> getAll() {
        return ResponseEntity.ok(ratService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rat> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ratService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rat> update(@PathVariable Integer id, @RequestBody Rat ratDetails) {
        return ResponseEntity.ok(ratService.update(id, ratDetails));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Rat> patch(@PathVariable Integer id, @RequestBody Rat ratDetails){
        return ResponseEntity.ok(ratService.patch(id, ratDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id){
        ratService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        ratService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}