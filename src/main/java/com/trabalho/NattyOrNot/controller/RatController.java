package com.trabalho.NattyOrNot.controller;

import com.trabalho.NattyOrNot.model.Bomb;
import com.trabalho.NattyOrNot.model.Rat;
import com.trabalho.NattyOrNot.service.RatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rat")
public class RatController {

    @Autowired
    public RatService ratService;

    @PostMapping("/criar")
    public ResponseEntity<Rat> create(@RequestBody Rat rat) {
        Rat ratSaved = ratService.create(rat);
        return ResponseEntity.status(HttpStatus.CREATED).body(ratSaved);
    }

    @GetMapping
    public List<Rat> getAll() {
        return ratService.findAll();
    }

    @GetMapping("/{id}")
    public Rat getById(@PathVariable Integer id) {
        return ratService.findById(id);
    }

    @PutMapping("/{id}")
    public Rat update(@PathVariable Integer id, @RequestBody Rat ratDetails) {
        return ratService.update(id, ratDetails);
    }

    @PatchMapping("{id}")
    public Rat patch(@PathVariable Integer id, @RequestBody Rat ratDetails){
        return ratService.patch(id, ratDetails);

    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id){
        ratService.deleteById(id);
    }
    @DeleteMapping
    public void deleteAll(){
        ratService.deleteAll();
    }
}
