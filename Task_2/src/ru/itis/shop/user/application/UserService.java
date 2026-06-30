package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String password, String profileDescription) {
        User user = new User(email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }

    public void findById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Юзер с айди: " + id + " не найден"));
        System.out.println("Найденный пользователь: " + user.getEmail());
    }

    public void updateUser(String email, String profileDescription) {
        userRepository.updateProfileDescriptionByEmail(email, profileDescription);
    }
}
