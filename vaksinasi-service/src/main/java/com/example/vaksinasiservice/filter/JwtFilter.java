package com.example.vaksinasiservice.filter;

import com.example.vaksinasiservice.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Cek apakah header Authorization tersedia dan formatnya benar
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);

            try {
                // Validasi token
                if (!jwtUtil.isTokenValid(token)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token tidak valid");
                    return;
                }

                // Ambil informasi dari token
                String name = jwtUtil.extractName(token);
                String role = jwtUtil.extractRole(token);

                // Simpan ke request agar bisa dipakai di controller
                request.setAttribute("name", name);
                request.setAttribute("role", role);

            } catch (ExpiredJwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token kadaluarsa");
                return;
            } catch (JwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token rusak atau tidak valid");
                return;
            }

        } else {
            // Jika tidak ada header, bisa abaikan atau blok akses tergantung kebutuhan
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token tidak ditemukan");
            // return;
        }

        // Lanjutkan filter jika valid
        filterChain.doFilter(request, response);
    }
}
