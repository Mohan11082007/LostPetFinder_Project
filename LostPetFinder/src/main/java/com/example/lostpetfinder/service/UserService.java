package com.example.lostpetfinder.service;

import com.example.lostpetfinder.dto.UserRequest;
import com.example.lostpetfinder.entity.User;
import com.example.lostpetfinder.exception.DuplicateResourceException;
import com.example.lostpetfinder.exception.ResourceNotFoundException;
import com.example.lostpetfinder.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository) { this.repository = repository; }

    public User create(UserRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("A user with this email already exists.");
        }
        return repository.save(new User(request.name().trim(), request.email().trim().toLowerCase(), request.phone().trim()));
    }
    public List<User> findAll() { return repository.findAll(); }
    public User findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found: " + id)); }
    public User update(Long id, UserRequest request) {
        User user = findById(id);
        if (!user.getEmail().equalsIgnoreCase(request.email()) && repository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("A user with this email already exists.");
        }
        user.setName(request.name().trim());
        user.setEmail(request.email().trim().toLowerCase());
        user.setPhone(request.phone().trim());
        return repository.save(user);
    }
    public void delete(Long id) { repository.delete(findById(id)); }
}
