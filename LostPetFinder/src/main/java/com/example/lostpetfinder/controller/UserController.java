package com.example.lostpetfinder.controller;

import com.example.lostpetfinder.dto.UserRequest;
import com.example.lostpetfinder.entity.User;
import com.example.lostpetfinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    public UserController(UserService service) { this.service = service; }
    @PostMapping public User create(@Valid @RequestBody UserRequest request) { return service.create(request); }
    @GetMapping public List<User> all() { return service.findAll(); }
    @GetMapping("/{id}") public User one(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public User update(@PathVariable Long id, @Valid @RequestBody UserRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
