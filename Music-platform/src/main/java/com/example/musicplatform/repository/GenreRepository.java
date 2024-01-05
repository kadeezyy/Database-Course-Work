package com.example.musicplatform.repository;

import com.example.musicplatform.dto.GenreDto;
import com.example.musicplatform.entity.Routines;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.NotUniqueObjectException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.Genre;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class GenreRepository {
    private final DSLContext jooq;
    private final com.example.musicplatform.entity.tables.Genre GENRE = com.example.musicplatform.entity.tables.Genre.GENRE;


    public GenreRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    public Genre getGenreInfo(UUID id) {
        return jooq.selectFrom(GENRE).where(GENRE.ID.equal(id)).fetchOptional().orElseThrow(
                () -> new NotFoundException(DataAccessMessages.OBJECT_NOT_FOUND.name())
        ).into(Genre.class);
    }

    public UUID addGenre(GenreDto genre) throws NotUniqueObjectException {
        return jooq.select(Routines.addGenre(genre.name(), genre.description()))
                .fetchOptional()
                .orElseThrow(
                        () -> new NotUniqueObjectException(DataAccessMessages.NOT_UNIQUE_OBJECT.name())
                )
                .into(UUID.class);
    }
}
