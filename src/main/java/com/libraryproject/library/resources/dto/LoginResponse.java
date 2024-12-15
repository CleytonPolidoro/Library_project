package com.libraryproject.library.resources.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
