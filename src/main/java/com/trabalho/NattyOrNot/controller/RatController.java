package com.trabalho.NattyOrNot.controller;

import com.trabalho.NattyOrNot.model.Rat;
import com.trabalho.NattyOrNot.service.RatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rat")
public class RatController {

    @Autowired
    public RatService ratService;

    @PostMapping("/criar")
    public Rat create(@RequestBody Rat rat){
        return ratService.create(rat);
    }
}
