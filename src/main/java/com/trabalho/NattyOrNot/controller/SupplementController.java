package com.trabalho.NattyOrNot.controller;

import com.trabalho.NattyOrNot.model.Supplement;
import com.trabalho.NattyOrNot.service.SupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplement")
public class SupplementController {

    @Autowired
    public SupplementService supplementService;

    @PostMapping("/criar")
    public Supplement create(@RequestBody Supplement supplement){
        return supplementService.create(supplement);
    }
}
