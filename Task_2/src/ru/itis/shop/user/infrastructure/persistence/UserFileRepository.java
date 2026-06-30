package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(userMapper.toLine(user));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line = reader.readLine();

            while (line != null) {

                User user = userMapper.fromLine(line);

                if (user.getEmail().equals(email)) {
                    return Optional.of(user);
                }

                line = reader.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.trim().split("\\|");
                if (data[0].equals(id)) {
                    return Optional.of(new User(data[0], data[1], data[2], data[3]));
                }
            }
        } catch(IOException e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
    }

    @Override
    public void updateProfileDescriptionByEmail(String email, String profileDescription) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line = reader.readLine();
            List<String> lines = new ArrayList<>();
            User updatedUser = null;

            while (line != null) {
                User user = userMapper.fromLine(line);
                if (!email.equals(user.getEmail())) {
                    lines.add(line);
                } else {
                    updatedUser = new User(user.getId(), user.getEmail(), user.getPassword(), profileDescription);
                }
                line = reader.readLine();
            }

            if (updatedUser != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    for (String l : lines) {
                        writer.write(l + "\n");
                    }
                    writer.write(userMapper.toLine(updatedUser));
                }
            } else {
                throw new IllegalStateException("Юзер с email " + email + " не найден");
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


}
