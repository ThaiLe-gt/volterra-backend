package com.dt.auth.account.domain;

import java.util.UUID;

public record Account(UUID id, String username, String email, String status) {
}
