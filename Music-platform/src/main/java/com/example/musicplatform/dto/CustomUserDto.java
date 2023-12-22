package com.example.musicplatform.dto;

import com.example.musicplatform.model.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class CustomUserDto implements Serializable {
    private UUID id;
    private String username;

    @Builder.Default
    private String password = "hidden";

    private Role role = Role.regular;
}