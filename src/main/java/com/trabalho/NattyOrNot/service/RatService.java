package com.trabalho.NattyOrNot.service;

import com.trabalho.NattyOrNot.exception.NotFoundException;
import com.trabalho.NattyOrNot.model.Bomb;
import com.trabalho.NattyOrNot.model.Rat;
import com.trabalho.NattyOrNot.model.Supplement;
import com.trabalho.NattyOrNot.repository.BombRepository;
import com.trabalho.NattyOrNot.repository.RatRepository;
import com.trabalho.NattyOrNot.repository.SupplementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatService {

    @Autowired
    private RatRepository ratRepository;

    @Autowired
    private SupplementRepository supplementRepository;

    @Autowired
    private BombRepository bombRepository;

    public Rat create(Rat rat) {
        String ratName = rat.getName();
        if (ratName == null || ratName.isBlank()) {
            throw new RuntimeException("Nome do rat é obrigatório.");
        }

        List<Supplement> validatedSupplements = rat.getSupplements().stream()
                .map(this::resolveSupplement)
                .collect(Collectors.toList());
        rat.setSupplements(validatedSupplements);

        if (rat.isUseBomb()) {
            if (rat.getBomb() == null) {
                throw new RuntimeException("Bomba é obrigatória se usar bomba.");
            }
            Bomb bomb = resolveBomb(rat.getBomb());
            rat.setBomb(bomb);
        } else {
            rat.setBomb(null);
        }

        return ratRepository.save(rat);
    }

    private Supplement resolveSupplement(Supplement supplement) {
        if (supplement.getId() != null) {
            return supplementRepository.findById(supplement.getId())
                    .orElseThrow(() -> new RuntimeException("Suplemento com id '" + supplement.getId() + "' não encontrado."));
        } else if (supplement.getName() != null && !supplement.getName().isBlank()) {
            return supplementRepository.findByName(supplement.getName())
                    .orElseThrow(() -> new RuntimeException("Suplemento '" + supplement.getName() + "' não encontrado."));
        }
        throw new RuntimeException("É necessário fornecer id ou nome do suplemento.");
    }

    private Bomb resolveBomb(Bomb bombRequest) {
        if (bombRequest.getId() != null) {
            return bombRepository.findById(bombRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Bomba com id '" + bombRequest.getId() + "' não encontrada."));
        } else if (bombRequest.getName() != null && !bombRequest.getName().isBlank()) {
            return bombRepository.findByName(bombRequest.getName())
                    .orElseThrow(() -> new RuntimeException("Bomba '" + bombRequest.getName() + "' não encontrada."));
        }
        throw new RuntimeException("É necessário fornecer id ou nome da bomba.");
    }

    public List<Rat> findAll() {
        return ratRepository.findAll();
    }

    public Rat findById(Integer id) {
        return ratRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ratinho(a) de academia com o id " + id + " não encontrado(a)"));

    }

    public Rat update(Integer id, Rat ratDetails) {
        Rat rat = ratRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ratinho(a) de academia com o id " + id + " não encontrado(a)"));

        if (ratDetails.getName() == null || ratDetails.getName().isBlank()) {
            throw new RuntimeException("Nome do rat é obrigatório.");
        }
        rat.setName(ratDetails.getName());

        if (ratDetails.getSupplements() != null && !ratDetails.getSupplements().isEmpty()) {
            List<Supplement> validatedSupplements = ratDetails.getSupplements().stream()
                    .map(this::resolveSupplement)
                    .collect(Collectors.toList());
            rat.setSupplements(validatedSupplements);
        }

        if (ratDetails.isUseBomb()) {
            if (ratDetails.getBomb() == null) {
                throw new RuntimeException("Bomba é obrigatória se usar bomba.");
            }
            Bomb bomb = resolveBomb(ratDetails.getBomb());
            rat.setBomb(bomb);
            rat.setUseBomb(true);
        } else {
            rat.setBomb(null);
            rat.setUseBomb(false);
        }

        return ratRepository.save(rat);
    }

    public Rat patch(Integer id, Rat ratDetails) {
        Rat rat = ratRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ratinho(a) de academia com o id " + id + " não encontrado(a)"));

        if (ratDetails.getName() != null && !ratDetails.getName().isBlank()) {
            rat.setName(ratDetails.getName());
        }

        if (ratDetails.getSupplements() != null && !ratDetails.getSupplements().isEmpty()) {
            List<Supplement> validatedSupplements = ratDetails.getSupplements().stream()
                    .map(this::resolveSupplement)
                    .collect(Collectors.toList());
            rat.setSupplements(validatedSupplements);
        }

        if (ratDetails.isUseBomb()) {
            if (ratDetails.getBomb() == null) {
                throw new RuntimeException("Bomba é obrigatória se usar bomba.");
            }
            Bomb bomb = resolveBomb(ratDetails.getBomb());
            rat.setBomb(bomb);
            rat.setUseBomb(true);
        } else if (ratDetails.getBomb() == null) {
            rat.setBomb(null);
            rat.setUseBomb(false);
        }

        return ratRepository.save(rat);
    }

    public void deleteById(Integer id) {
        ratRepository.deleteById(id);
    }

    public void deleteAll() {
        ratRepository.deleteAll();
    }
}

