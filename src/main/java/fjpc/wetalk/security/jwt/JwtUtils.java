package fjpc.wetalk.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JwtUtils {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-time}")
    private Long expirationTime;

    @Value("${security.jwt.token-prefix}")
    private String tokenPrefix;

    @Value("${security.jwt.header}")
    private String headerName;

    public JwtToken generateToken(Authentication auth) {
        String authorities = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String token = JWT
                .create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withClaim("roles", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(HMAC512(secret.getBytes()));
        return new JwtToken(token,tokenPrefix);
    }

    public Optional<String> getAuthHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(headerName));
    }

    public Optional<String> getAuthHeader(Message message) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        return Optional.of(accessor.getFirstNativeHeader(headerName));
    }

    public Authentication verify(String token) {
        DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(token.replace(tokenPrefix + " ", ""));
        String username = decodedJwt.getSubject();
        Set<GrantedAuthority> authorities = Arrays
                .stream(decodedJwt.getClaim("roles").asString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

}
