package com.example.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.jwt.entity.Role;
import com.example.jwt.jpa.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepo;

//    @Test
//    public void testCreateUser(){
//        Role admin = new Role("ROLE_ADMIN");
//        Role editor = new Role("ROLE_EDITOR");
//        Role customer = new Role("ROLE_CUSTOMER");
//
//        roleRepo.saveAll(List.of(admin, editor, customer));
//
//        long count = roleRepo.count();
//        assertEquals(3, count);
//    }
}
