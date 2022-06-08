package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration-milliseconds}")
    private Long expirationMilliseconds;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + expirationMilliseconds);

        return Jwts.builder()
                   .setIssuer("API do Fórum da Alura")
                   .setSubject(usuario.getId().toString())
                   .setIssuedAt(hoje)
                   .setExpiration(dataExpiracao)
                   .signWith(SignatureAlgorithm.HS256, secret)
                   .compact();
    }

}
