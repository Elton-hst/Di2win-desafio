package com.di2win.web.dto;

import java.time.LocalDateTime;

public record StatementDto(long account,
                           long agency,
                           LocalDateTime from,
                           LocalDateTime until){
}

