package com.libraryproject.library.entities.enums;

public enum LoanStatus {
    WAITING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5);

    private int code;
    private LoanStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static LoanStatus valueOf(int code) {
        for(LoanStatus loanStatus : LoanStatus.values()) {
            if(loanStatus.getCode() == code) {
                return loanStatus;
            }
        }
        throw new IllegalArgumentException("Invalid order status code");
    }



}
