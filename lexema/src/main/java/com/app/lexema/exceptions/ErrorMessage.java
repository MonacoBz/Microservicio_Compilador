package com.app.lexema.exceptions;

import java.time.LocalDateTime;

public record ErrorMessage(String error, LocalDateTime timesnap) {
}
