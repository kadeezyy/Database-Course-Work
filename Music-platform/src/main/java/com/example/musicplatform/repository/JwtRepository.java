package com.example.musicplatform.repository;

import com.example.musicplatform.entity.Routines;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.JwtToken;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JwtRepository {
    private final DSLContext jooq;
    private final com.example.musicplatform.entity.tables.JwtToken JWT_TOKEN = com.example.musicplatform.entity.tables.JwtToken.JWT_TOKEN;

    public JwtRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public UUID save(String token, CustomUser user) {
        return jooq.select(Routines.addJwtToken(user.getId(), false, false, token)).fetchOne(0, UUID.class);
    }
}
