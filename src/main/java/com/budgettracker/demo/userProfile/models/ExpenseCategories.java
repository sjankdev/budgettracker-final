package com.budgettracker.demo.userProfile.models;

public enum ExpenseCategories {

    FOOD_AND_DRINK("Food & Drink"),
    SHOPPING("Shopping"),
    TRANSPORT("Transport"),
    HOME("Home"),
    BILLS_AND_FEES("Bills & Fees"),
    ENTERTAINMENT("Entertainment"),
    CAR("Car"),
    TRAVEL("Travel"),
    FAMILY_AND_PERSONAL("Family & Personal"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education"),
    GROCERIES("Groceries"),
    GIFTS("Gifts"),
    BEAUTY("Beauty"),
    WORK("Work"),
    SPORTS_AND_HOBBIES("Sports & Hobbies"),
    OTHER("Other");


    private final String displayName;

    ExpenseCategories(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}


