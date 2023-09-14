package com.di2win.dto;

public record StatementDto(long account,
                           long agency,
                           String from,
                           String until,
                           String transaction_type){
}

