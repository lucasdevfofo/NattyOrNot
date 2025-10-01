package com.trabalho.NattyOrNot.service;

import com.trabalho.NattyOrNot.exception.NotFoundException;
import com.trabalho.NattyOrNot.model.Supplement;
import com.trabalho.NattyOrNot.repository.SupplementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class SupplementService {

    @Autowired
    public SupplementRepository supplementRepository;

    public Supplement create(@RequestBody Supplement supplement){
        return supplementRepository.save(supplement);
    }

    public List<Supplement> findAll() {
        return supplementRepository.findAll();
    }

    public Supplement findById(Integer id) {
        return supplementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suplemento com o id "+ id + " não encontrado"));
    }
    public Supplement update(Integer id, Supplement supplementDetails) {
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suplemento com o id "+ id + " não encontrado"));

        if (supplementDetails.getName() == null || supplementDetails.getName().isBlank()) {
            throw new RuntimeException("Nome do suplemento é obrigatório.");
        }
        supplement.setName(supplementDetails.getName());

        return supplementRepository.save(supplement);
    }

    public Supplement patch(Integer id, Supplement supplementDetails) {
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suplemento com o id "+ id + " não encontrado"));

        if (supplementDetails.getName() != null && !supplementDetails.getName().isBlank()) {
            supplement.setName(supplementDetails.getName());
        }

        return supplementRepository.save(supplement);
    }

    public void deleteById(Integer id){
        supplementRepository.deleteById(id);
    }
    public void deleteAll(){
        supplementRepository.deleteAll();
    }
}
