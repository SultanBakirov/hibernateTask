package org.example.service;

import org.example.exception.BadCredentialException;
import org.example.exception.NotFoundException;
import org.example.models.User;
import org.example.repository.UserRepository;

import java.util.List;

public class UserService implements AutoCloseable{

    private UserRepository userRepository = new UserRepository();

    public void register(User user) {
        Boolean exists = userRepository.existsByEmail(user.getEmail());
        if (exists) {
            throw new IllegalStateException(
                String.format("Illegal State Exception")
            );
        }
        userRepository.save(user);
    }

    public Boolean login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new NotFoundException(String.format("Not found exception!")));
        if (password.equals(user.getPassword())) {
            return true;
        } else {
            throw new BadCredentialException(String.format("Bad credential exception!"));
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void close() throws Exception {
        userRepository.close();
    }
}
