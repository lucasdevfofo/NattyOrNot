package com.trabalho.NattyOrNot.service;

import com.trabalho.NattyOrNot.exception.BadRequestException;
import com.trabalho.NattyOrNot.exception.NotFoundException;
import com.trabalho.NattyOrNot.model.Rat;
import com.trabalho.NattyOrNot.repository.RatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatService {

    @Autowired
    private RatRepository ratRepository;

    public Rat create(Rat rat) {
        if (rat.getName() == null || rat.getName().isBlank()) {
            throw new BadRequestException("Nome do rat é obrigatório.");
        }
        return ratRepository.save(rat);
    }

    public List<Rat> findAll() {
        return ratRepository.findAll();
    }

    public Rat findById(Integer id) {
        return ratRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ratinho(a) de academia com o id " + id + " não encontrado(a)"));
    }

    public Rat update(Integer id, Rat ratDetails) {
        Rat rat = findById(id);

        if (ratDetails.getName() == null || ratDetails.getName().isBlank()) {
            throw new BadRequestException("Nome do rato é obrigatório.");
        }
        rat.setName(ratDetails.getName());
        rat.setUseBomb(ratDetails.isUseBomb());
        rat.setUseSupplement(ratDetails.isUseSupplement());
        rat.setApLocation(ratDetails.getApLocation());

        return ratRepository.save(rat);
    }

    public Rat patch(Integer id, Rat ratDetails) {
        Rat rat = findById(id);

        if (ratDetails.getName() != null && !ratDetails.getName().isBlank()) {
            rat.setName(ratDetails.getName());
        }
        if (ratDetails.getApLocation() != null) {
            rat.setApLocation(ratDetails.getApLocation());
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