package com.huskycare.ems.model;

public enum Department {
    ENGINEERING(10),
    HUMAN_RESOURCES(8),
    MARKETING(12),
    SALE(15),
    IT(9);
    private final int bonusPercentage;

    public int getBonusPercentage() {
        return bonusPercentage;
    }

    Department(int bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }
}
