package com.picpaysimplificado.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDto(BigDecimal value, UUID senderId, UUID receiverId) {
}
