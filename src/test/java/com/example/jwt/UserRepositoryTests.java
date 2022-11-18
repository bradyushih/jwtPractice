package com.example.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.jwt.entity.User;
import com.example.jwt.jpa.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepo;

    @Test
    public void testCreateUser(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("nam2020");

        User newUser = new User().email("nam@codejava.net").password(password);
        User savedUser = userRepo.save(newUser);

        System.out.println("123");
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.id()).isGreaterThan(0);
    }
}
