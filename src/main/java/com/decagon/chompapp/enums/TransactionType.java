package com.decagon.chompapp.enums;

public enum TransactionType {
    FUNDWALLET("fundwallet"), WITHDRAWAL("withdrawal");
   private final String transaction ;

    TransactionType(String transaction) {
        this.transaction = transaction;
    }
}
