package org.example.backend.security;

public record AppUser(String id,
                      String name,
                      String avatarUrl,
                      String authority) {
}
