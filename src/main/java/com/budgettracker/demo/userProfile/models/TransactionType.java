package com.budgettracker.demo.userProfile.models;

public enum TransactionType {

    EXPENSE("Expense"),
    INCOME("Income");


    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}


