package ru.itis.shop.user.repository;

import ru.itis.shop.user.domain.User;

public interface UserRepository {

    void save(User user);

    User findById(String id);
}
