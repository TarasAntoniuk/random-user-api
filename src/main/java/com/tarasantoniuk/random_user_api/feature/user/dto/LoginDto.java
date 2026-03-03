package com.tarasantoniuk.random_user_api.feature.user.dto;


/**
 * required by specification:
 * * "API must return exactly the same response as the external API."
 * <p>
 * Note: In production, sensitive fields should be filtered out.
 * Exposing password hashes (especially MD5/SHA1) is a security anti-pattern
 * as they are vulnerable to offline brute-force attacks.
 */
public record LoginDto(
        String uuid,
        String username,
        String password,
        String salt,
        String md5,
        String sha1,
        String sha256
) {}