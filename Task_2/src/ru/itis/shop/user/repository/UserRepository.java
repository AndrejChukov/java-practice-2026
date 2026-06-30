package ru.itis.shop.user.repository;

import ru.itis.shop.user.domain.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    void updateProfileDescriptionByEmail(String email, String profileDescription);
}
