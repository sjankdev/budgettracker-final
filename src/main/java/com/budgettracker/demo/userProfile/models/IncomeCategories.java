package com.budgettracker.demo.userProfile.models;

public enum IncomeCategories {

    SALARY("Salary"),
    BUSINESS("Business"),
    GIFTS("Gifts"),
    EXTRA_INCOME("Extra income"),
    LOAN("Loan"),
    PARENTAL_LEAVE("Parental Leave"),
    INSURANCE_PAYOUT("Insurance payout"),
    OTHER("Other");


    private final String displayName;

    IncomeCategories(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}


