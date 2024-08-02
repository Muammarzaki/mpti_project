package uin.helpers;

public class AccountExitsException extends RuntimeException {
    public AccountExitsException() {
        super("Account Exits");
    }
}
