package com.example.musicplatform.repository;

import com.example.musicplatform.dto.CustomUserDto;
import com.example.musicplatform.entity.Routines;
import com.example.musicplatform.entity.enums.Role;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.CustomUser;
import org.jooq.DSLContext;

import static org.jooq.impl.DSL.*;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {
    private final DSLContext jooq;
    private final com.example.musicplatform.entity.tables.CustomUser CUSTOM_USER = com.example.musicplatform.entity.tables.CustomUser.CUSTOM_USER;

    public UserRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public CustomUser findUserByUsername(String username) {
        return jooq.select().from(CUSTOM_USER)
                .where(CUSTOM_USER.USERNAME.eq(username))
                .fetchOneInto(CustomUser.class);
    }


    public List<CustomUser> findAllUsersByUsername(String name) {
        return jooq.select().from(CUSTOM_USER)
                .where(CUSTOM_USER.USERNAME.eq(name))
                .fetchOptional()
                .stream().distinct()
                .map((record -> record.into(CustomUser.class))).toList();
    }

    public CustomUser findUserById(UUID id) {
        return jooq.select().from(CUSTOM_USER)
                .where(CUSTOM_USER.ID.eq(id))
                .fetchOptional()
                .orElseThrow(
                        () -> new NotFoundException(DataAccessMessages.OBJECT_NOT_FOUND.name())
                ).into(CustomUser.class);
    }

    public CustomUser save(CustomUserDto user) {
        return jooq.select(Routines.createNewUser(Role.regular, user.getUsername(), user.getPassword(), user.getEmail()))
                .fetchOne(0, CustomUser.class);
    }
}
