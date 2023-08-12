package com.bank.transfer.exception;

public class NoSuchTransferException extends RuntimeException{
    public NoSuchTransferException(String message) {
        super(message);
    }
}
