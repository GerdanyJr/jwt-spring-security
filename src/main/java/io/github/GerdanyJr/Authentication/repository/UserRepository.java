package io.github.GerdanyJr.Authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.GerdanyJr.Authentication.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);

    public Boolean existsByUsername(String username);
}
