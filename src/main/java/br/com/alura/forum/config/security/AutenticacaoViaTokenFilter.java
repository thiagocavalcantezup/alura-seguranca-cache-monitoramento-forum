package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public AutenticacaoViaTokenFilter(TokenService tokenService,
                                      UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authToken = recuperarToken(request);
        boolean tokenValido = tokenService.isValido(authToken);

        if (tokenValido) {
            autenticarCliente(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");

        if (authToken == null || authToken.isEmpty() || !authToken.startsWith("Bearer")
                || authToken.length() <= 7) {
            return null;
        }

        return authToken.substring(7);
    }

    private void autenticarCliente(String authToken) {
        Long usuarioId = tokenService.getUsuarioId(authToken);
        Usuario usuario = usuarioRepository.findById(usuarioId).get();
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
            usuario, null, usuario.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(loginToken);
    }

}
