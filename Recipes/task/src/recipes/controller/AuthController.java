package recipes.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.service.UserService;
import recipes.user.User;

import javax.validation.Valid;

@RestController
public class AuthController {
  @Autowired
    private UserService userService;
  @PostMapping("/api/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid User user) {
      userService.registerUser(user);
      return new ResponseEntity<>(HttpStatus.OK);
  }
}
