package com.trabalho.NattyOrNot.service;

import com.trabalho.NattyOrNot.model.Supplement;
import com.trabalho.NattyOrNot.repository.SupplementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class SupplementService {

    @Autowired
    public SupplementRepository supplementRepository;

    public Supplement create(@RequestBody Supplement supplement){
        return supplementRepository.save(supplement);
    }
}
