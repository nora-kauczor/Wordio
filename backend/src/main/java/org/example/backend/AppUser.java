package org.example.backend;

public record AppUser(String id,
                      String name,
                      String avatarUrl,
                      String authority) {
}
