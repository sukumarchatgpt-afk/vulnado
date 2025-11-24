package com.scalesec.vulnado;

import org.springframework.boot.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.Serializable;
import java.util.Set;

@RestController
@EnableAutoConfiguration
public class LoginController {
  @Value("${app.secret}")
  private String secret;

  @CrossOrigin(origins = {"https://trusted-domain.com"})
  @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
  ResponseEntity<LoginResponse> login(@RequestBody LoginRequest input) {
    User user = User.fetch(input.username);
    if (user == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
    }

    if (Postgres.md5(input.password).equals(user.hashedPassword)) {
      return ResponseEntity.ok(new LoginResponse(user.token(secret)));
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access Denied");
    }
  }

  private boolean isTrustedDomain(String origin) {
    Set<String> trustedDomains = Set.of("https://trusted-domain.com");
    return trustedDomains.contains(origin);
  }
}

class LoginRequest implements Serializable {
  public String username;
  public String password;
}

class LoginResponse implements Serializable {
  public String token;
  public LoginResponse(String msg) { this.token = msg; }
}

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class Unauthorized extends RuntimeException {
  public Unauthorized(String exception) {
    super(exception);
  }
}
