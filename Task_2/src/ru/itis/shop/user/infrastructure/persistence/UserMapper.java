package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;

public class UserMapper {

    public User fromLine(String line) {
        String[] parts = line.split("\\|");

        return new User(parts[0], parts[1], parts[2], parts[3]);
    }

    public String toLine(User user) {
        return user.getId() + "|" +
                user.getEmail() + "|" +
                user.getPassword() + "|" +
                user.getProfileDescription();
    }
}
