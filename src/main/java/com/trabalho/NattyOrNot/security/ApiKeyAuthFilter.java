package com.trabalho.NattyOrNot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final ApiKeyService apiKeyService;
    private final String headerName;
    private final List<String> publicPaths;
    private final AntPathMatcher matcher = new AntPathMatcher();

    public ApiKeyAuthFilter(ApiKeyService apiKeyService, String headerName, List<String> publicPaths) {
        this.apiKeyService = apiKeyService;
        this.headerName = headerName;
        this.publicPaths = publicPaths;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return publicPaths != null && publicPaths.stream().anyMatch(p -> matcher.match(p.trim(), path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String presented = req.getHeader(headerName);
        var ok = apiKeyService.validate(presented).isPresent();

        if (!ok) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"invalid_or_missing_api_key\"}");
            return;
        }
        chain.doFilter(req, res);
    }
}
