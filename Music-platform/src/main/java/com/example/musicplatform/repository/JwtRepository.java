package com.example.musicplatform.repository;

import com.example.musicplatform.entity.Routines;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.model.pojos.JwtToken;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JwtRepository {
    private final DSLContext jooq;
    private final com.example.musicplatform.entity.tables.JwtToken JWT_TOKEN = com.example.musicplatform.entity.tables.JwtToken.JWT_TOKEN;

    public JwtRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public UUID save(String token, UUID userId) {
        return jooq.select(Routines.addJwtToken(userId, false, false, token)).fetchOne(0, UUID.class);
    }

    public List<JwtToken> findAllValidTokenByUser(CustomUser user) {
        return jooq.select().from(JWT_TOKEN)
                .where(JWT_TOKEN.USER_ID.eq(user.getId()))
                .fetch()
                .into(JwtToken.class);
    }

    public void saveAll(List<JwtToken> validUserTokens) {
        for (JwtToken token : validUserTokens) {
            jooq.update(JWT_TOKEN)
                    .set(JWT_TOKEN.EXPIRED, token.getExpired())
                    .set(JWT_TOKEN.REVOKED, token.getRevoked())
                    .where(JWT_TOKEN.ID.eq(token.getId()))
                    .execute();
        }
    }
}
