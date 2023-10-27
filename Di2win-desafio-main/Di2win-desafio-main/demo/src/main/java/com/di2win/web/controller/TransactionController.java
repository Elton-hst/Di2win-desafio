package com.di2win.web.controller;

import com.di2win.web.dto.StatementDto;
import com.di2win.domin.entity.Account;
import com.di2win.domin.entity.Transaction;
import com.di2win.web.dto.TransactionDto;
import com.di2win.domin.entity.enums.TransactionType;
import com.di2win.domin.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/transaction")
@Tag(name = "transactions")
public class TransactionController {

    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @RequestMapping(value = "/deposito", method = RequestMethod.POST)
    public ResponseEntity<Transaction> deposit(@RequestBody TransactionDto transaction) throws Exception {
        Transaction deposit = transactionService.deposit(transaction);
        return new ResponseEntity<>(deposit, HttpStatus.CREATED);
    }

    @Operation(summary = "Realiza o saque", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saque realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o saque"),
    })
    @RequestMapping(value = "/saque", method = RequestMethod.POST)
    public ResponseEntity<Transaction> withdraw(@RequestBody TransactionDto transaction) throws Exception {
        Transaction withdraw = transactionService.withdraw(transaction);
        return new ResponseEntity<>(withdraw, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca de todas as transações", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saque realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o saque"),
    })
    @RequestMapping(value = "/getAllTrasactions", method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> getAllTrasactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/createTransaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transaction) throws Exception {
        Transaction newTransaction = this.transactionService.createTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/statement", method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> statement(@RequestBody StatementDto statementDto) throws Exception {
        List<Transaction> statement;

        Account account = new Account();
        account.setAccount(statementDto.account());
        account.setAgency(statementDto.agency());

        if(statementDto.from() == null && statementDto.until() == null) {
            statement = transactionService.statement(account, null, null);
        }else{
            statement = transactionService.statement(account, statementDto.from(), statementDto.until());
        }

        return new ResponseEntity<>(statement, HttpStatus.OK);
    }

}
