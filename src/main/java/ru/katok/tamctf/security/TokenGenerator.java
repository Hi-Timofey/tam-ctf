package ru.katok.tamctf.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.katok.tamctf.domain.entity.UserEntity;
import ru.katok.tamctf.domain.error.UserNotFoundException;
import ru.katok.tamctf.repository.UserRepository;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TokenGenerator{

    private final UserRepository userRepository;

    public String getToken(String username, String password) throws Exception {
        if (username == null || password == null)
            return null;
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        UserEntity user;
        if (optionalUser.isEmpty())
            throw new UserNotFoundException("There is no user with that username: {}".format(username));
        else
            user = optionalUser.get();
        Map<String, Object> tokenData = new HashMap<>();
        if (password.equals(user.getPassword())) {
            tokenData.put("clientType", "user");
            tokenData.put("userID", user.getId().toString());
            tokenData.put("username", user.getUsername());
            tokenData.put("token_create_date", new Date().getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 100);
            tokenData.put("token_expiration_date", calendar.getTime());
            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.expiration(calendar.getTime());
            jwtBuilder.claims(tokenData);
            String key = "abc123";
            return jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
        } else {
            throw new Exception("Authentication error");
        }
    }

}

