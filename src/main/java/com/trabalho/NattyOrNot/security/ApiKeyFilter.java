package com.trabalho.NattyOrNot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${security.api-key.header}")
    private String headerName;

    @Value("${security.api-key.value}")
    private String expectedApiKey;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final List<String> publicPaths;

    public ApiKeyFilter(@Value("${security.api-key.public-paths:}") String publicPathsCsv) {
        this.publicPaths = Arrays.stream(publicPathsCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (publicPaths.isEmpty()) return false;
        String uri = request.getRequestURI();
        return publicPaths.stream().anyMatch(p -> pathMatcher.match(p, uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String provided = req.getHeader(headerName);

        boolean ok = provided != null && expectedApiKey != null && MessageDigest.isEqual(
                provided.getBytes(StandardCharsets.UTF_8),
                expectedApiKey.getBytes(StandardCharsets.UTF_8)
        );

        if (!ok) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"Missing or invalid API key\"}");
            return;
        }

        // >>> AQUI: concedemos USER, API e ADMIN para não tomar 403 em @PreAuthorize/@Secured
        List<GrantedAuthority> roles = List.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_API"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
        );

        var auth = new UsernamePasswordAuthenticationToken("api-key-user", null, roles);
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(req, res);
    }
}
