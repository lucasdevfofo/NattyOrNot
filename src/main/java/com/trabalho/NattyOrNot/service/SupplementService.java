package com.trabalho.NattyOrNot.service;

import com.trabalho.NattyOrNot.exception.BadRequestException;
import com.trabalho.NattyOrNot.exception.NotFoundException;
import com.trabalho.NattyOrNot.model.Rat;
import com.trabalho.NattyOrNot.model.Supplement;
import com.trabalho.NattyOrNot.repository.RatRepository;
import com.trabalho.NattyOrNot.repository.SupplementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplementService {

    @Autowired
    private SupplementRepository supplementRepository;

    @Autowired
    private RatRepository ratRepository;

    public Supplement create(Supplement supplement){
        return supplementRepository.save(supplement);
    }

    public List<Supplement> findAll() {
        return supplementRepository.findAll();
    }

    public Supplement findById(Integer id) {
        return supplementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suplemento com o id " + id + " não encontrado"));
    }

    public Supplement update(Integer id, Supplement supplementDetails) {
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suplemento com o id " + id + " não encontrado"));

        if (supplementDetails.getName() == null || supplementDetails.getName().isBlank()) {
            throw new BadRequestException("Nome do suplemento é obrigatório.");
        }
        supplement.setName(supplementDetails.getName());

        return supplementRepository.save(supplement);
    }

    public Supplement patch(Integer id, Supplement supplementDetails) {
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suplemento com o id " + id + " não encontrado"));

        if (supplementDetails.getName() != null && !supplementDetails.getName().isBlank()) {
            supplement.setName(supplementDetails.getName());
        }

        return supplementRepository.save(supplement);
    }

    @Transactional
    public void deleteById(Integer id){
        Supplement supplement = supplementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suplemento com o id " + id + " não encontrado"));

        // Desvincula o suplemento de todos os Rats antes de deletar
        List<Rat> rats = ratRepository.findBySupplements_Id(id);
        for (Rat r : rats) {
            if (r.getSupplements() != null) {
                r.getSupplements().removeIf(s -> s != null && id.equals(s.getId()));
            }
        }
        ratRepository.saveAll(rats);

        // Agora pode deletar o suplemento com segurança
        supplementRepository.delete(supplement);
    }

    @Transactional
    public void deleteAll(){
        // Limpa a join table antes de remover todos os suplementos
        List<Rat> rats = ratRepository.findAll();
        for (Rat r : rats) {
            if (r.getSupplements() != null) {
                r.getSupplements().clear();
            }
        }
        ratRepository.saveAll(rats);

        supplementRepository.deleteAll();
    }
}
