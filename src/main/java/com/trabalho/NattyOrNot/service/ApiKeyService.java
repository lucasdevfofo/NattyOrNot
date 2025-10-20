package com.trabalho.NattyOrNot.service;

import com.trabalho.NattyOrNot.repository.ApiKeyRepository;
import com.trabalho.NattyOrNot.model.ApiKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class ApiKeyService {

    private final ApiKeyRepository repo;

    @Value("${security.api-key.pepper}")
    private String pepper;

    public ApiKeyService(ApiKeyRepository repo) {
        this.repo = repo;
    }

    public Optional<ApiKey> validate(String presentedKey) {
        if (presentedKey == null || presentedKey.isBlank()) return Optional.empty();
        String hash = sha256(presentedKey + pepper);
        Optional<ApiKey> found = repo.findByKeyHashAndActiveTrue(hash);
        found.ifPresent(k -> {
            k.setLastUsedAt(OffsetDateTime.now());
            repo.save(k);
        });
        return found;
    }

    public String hashForStorage(String plainKey) {
        return sha256(plainKey + pepper);
    }

    public String generatePlainKey() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String sha256(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] out = md.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : out) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 unavailable", e);
        }
    }
}
