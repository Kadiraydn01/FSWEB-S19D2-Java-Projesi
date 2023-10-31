package com.example.bank.service;

import com.example.bank.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();
    Account save(Account account);
}
