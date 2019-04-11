package fjpc.wetalk.security.auth;

import fjpc.wetalk.security.jwt.JwtToken;
import fjpc.wetalk.security.jwt.JwtUtils;
import fjpc.wetalk.security.users.ApplicationUser;
import fjpc.wetalk.security.users.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwt;

    @Autowired
    private ApplicationUserRepository userRepository;

    @PostMapping("/login")
    public JwtToken login(@RequestBody Credentials credentials) {
        Authentication authentication = authService.authenticate(credentials);
        return jwt.generateToken(authentication);
    }

    @GetMapping("/me")
    public ApplicationUser whoAmI(Principal principal) {
        return userRepository.findByUsername(principal.getName());
    }

}
