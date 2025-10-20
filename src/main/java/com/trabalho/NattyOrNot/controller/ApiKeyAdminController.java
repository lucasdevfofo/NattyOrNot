package com.trabalho.NattyOrNot.controller;

import com.trabalho.NattyOrNot.model.ApiKey;
import com.trabalho.NattyOrNot.repository.ApiKeyRepository;
import com.trabalho.NattyOrNot.service.ApiKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api-keys")
public class ApiKeyAdminController {

    private final ApiKeyService service;
    private final ApiKeyRepository repo;

    public ApiKeyAdminController(ApiKeyService service, ApiKeyRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<CreateKeyResponse> create(@RequestParam String owner,
                                                    @RequestParam(required = false) String scopes) {
        String plainKey = service.generatePlainKey();
        String hash = service.hashForStorage(plainKey);

        ApiKey k = new ApiKey();
        k.setKeyHash(hash);
        k.setOwner(owner);
        k.setScopes(scopes);
        k.setActive(true);
        repo.save(k);

        // ATENÇÃO: mostra a chave em claro UMA única vez
        return ResponseEntity.ok(new CreateKeyResponse(plainKey));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable java.util.UUID id) {
        var key = repo.findById(id).orElseThrow();
        key.setActive(false);
        repo.save(key);
        return ResponseEntity.noContent().build();
    }

    public record CreateKeyResponse(String apiKey) {}
}
