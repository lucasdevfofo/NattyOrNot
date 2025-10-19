package com.trabalho.NattyOrNot.service;

import com.trabalho.NattyOrNot.exception.NotFoundException;
import com.trabalho.NattyOrNot.model.Bomb;
import com.trabalho.NattyOrNot.model.Rat;
import com.trabalho.NattyOrNot.repository.BombRepository;
import com.trabalho.NattyOrNot.repository.RatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BombService {

    @Autowired
    private BombRepository bombRepository;

    @Autowired
    private RatRepository ratRepository;

    public Bomb create(Bomb bomb) {
        return bombRepository.save(bomb);
    }

    public List<Bomb> findAll() {
        return bombRepository.findAll();
    }

    public Bomb findById(Integer id) {
        return bombRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bomba com o id " + id + " não encontrada"));
    }

    public Bomb update(Integer id, Bomb bombDetails) {
        Bomb bomb = bombRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bomba com o id " + id + " não encontrada"));

        bomb.setName(bombDetails.getName());
        bomb.setType(bombDetails.getType());
        bomb.setPrice(bombDetails.getPrice());
        bomb.setDescription(bombDetails.getDescription());

        return bombRepository.save(bomb);
    }

    public Bomb patch(Integer id, Bomb bombDetails) {
        Bomb bomb = bombRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bomba com o id " + id + " não encontrada"));

        if (bombDetails.getName() != null && !bombDetails.getName().isBlank()) {
            bomb.setName(bombDetails.getName());
        }
        if (bombDetails.getType() != null && !bombDetails.getType().isBlank()) {
            bomb.setType(bombDetails.getType());
        }
        if (bombDetails.getPrice() != null) {
            bomb.setPrice(bombDetails.getPrice());
        }
        if (bombDetails.getDescription() != null && !bombDetails.getDescription().isBlank()) {
            bomb.setDescription(bombDetails.getDescription());
        }

        return bombRepository.save(bomb);
    }

    @Transactional
    public void deleteById(Integer id) {
        Bomb bomb = bombRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bomba com o id " + id + " não encontrada"));

        // Solta a FK em todos os Rats que apontam para essa Bomb
        List<Rat> rats = ratRepository.findByBomb_Id(id);
        for (Rat r : rats) {
            r.setBomb(null);
        }
        ratRepository.saveAll(rats);

        bombRepository.delete(bomb);
    }

    @Transactional
    public void deleteAll() {
        // Zera a referência de bomb em todos os Rats antes de remover as Bombs
        List<Rat> rats = ratRepository.findAll();
        for (Rat r : rats) {
            r.setBomb(null);
        }
        ratRepository.saveAll(rats);

        bombRepository.deleteAll();
    }
}
