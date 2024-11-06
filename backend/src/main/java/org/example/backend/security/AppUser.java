package org.example.backend.security;

import org.springframework.data.annotation.Id;

public record AppUser(String id,
                      String name,
                      String avatarUrl,
                      String authority) {
}
