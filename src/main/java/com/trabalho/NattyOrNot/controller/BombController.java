package com.trabalho.NattyOrNot.controller;

import com.trabalho.NattyOrNot.model.Bomb;
import com.trabalho.NattyOrNot.service.BombService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bombs")
public class BombController {

    @Autowired
    public BombService bombService;

    // POST /bombs -> Cria um novo recurso
    @PostMapping
    public ResponseEntity<Bomb> create(@RequestBody Bomb bomb) {
        Bomb bombSaved = bombService.create(bomb);
        return ResponseEntity.status(HttpStatus.CREATED).body(bombSaved);
    }

    // GET /bombs -> Retorna a lista de todos os recursos
    @GetMapping
    public ResponseEntity<List<Bomb>> getAll() {
        return ResponseEntity.ok(bombService.findAll());
    }

    // GET /bombs/{id} -> Retorna um recurso específico
    @GetMapping("/{id}")
    public ResponseEntity<Bomb> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(bombService.findById(id));
    }

    // PUT /bombs/{id} -> Substitui completamente um recurso
    @PutMapping("/{id}")
    public ResponseEntity<Bomb> update(@PathVariable Integer id, @RequestBody Bomb bombDetails) {
        return ResponseEntity.ok(bombService.update(id, bombDetails));
    }

    // PATCH /bombs/{id} -> Atualiza parcialmente um recurso
    @PatchMapping("/{id}")
    public ResponseEntity<Bomb> patch(@PathVariable Integer id, @RequestBody Bomb bombDetails) {
        return ResponseEntity.ok(bombService.patch(id, bombDetails));
    }

    // DELETE /bombs/{id} -> Deleta um recurso específico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        bombService.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // DELETE /bombs -> Deleta todos os recursos
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        bombService.deleteAll();
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}