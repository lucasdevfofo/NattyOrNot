package com.trabalho.NattyOrNot.service;

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
            if (rat.getBomb() == null || rat.getBomb().getName() == null || rat.getBomb().getName().isBlank()) {
                throw new RuntimeException("Nome da bomba é obrigatório se usar bomba.");
            }
            Bomb bomb = bombRepository.findByName(rat.getBomb().getName())
                    .orElseThrow(() -> new RuntimeException("Bomba '" + rat.getBomb().getName() + "' não encontrada."));
            rat.setBomb(bomb);
        } else {
            rat.setBomb(null);
        }

        return ratRepository.save(rat);
    }

    private Supplement resolveSupplement(Supplement supplement) {
        if (supplement.getName() == null || supplement.getName().isBlank()) {
            throw new RuntimeException("Nome do suplemento é obrigatório.");
        }
        return supplementRepository.findByName(supplement.getName())
                .orElseThrow(() -> new RuntimeException("Suplemento '" + supplement.getName() + "' não encontrado."));
    }


}
