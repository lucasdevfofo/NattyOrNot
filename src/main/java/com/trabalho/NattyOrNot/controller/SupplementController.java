package com.trabalho.NattyOrNot.controller;

import com.trabalho.NattyOrNot.model.Supplement;
import com.trabalho.NattyOrNot.service.SupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplements")
public class SupplementController {

    @Autowired
    public SupplementService supplementService;

    @PostMapping
    public ResponseEntity<Supplement> create(@RequestBody Supplement supplement) {
        Supplement supplementSaved = supplementService.create(supplement);
        return ResponseEntity.status(HttpStatus.CREATED).body(supplementSaved);
    }

    @GetMapping
    public ResponseEntity<List<Supplement>> getAll() {
        return ResponseEntity.ok(supplementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplement> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(supplementService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplement> update(@PathVariable Integer id, @RequestBody Supplement supplementDetails) {
        return ResponseEntity.ok(supplementService.update(id, supplementDetails));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Supplement> patch(@PathVariable Integer id, @RequestBody Supplement supplementDetails) {
        return ResponseEntity.ok(supplementService.patch(id, supplementDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        supplementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        supplementService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}