package com.costsmanagerhit.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Expense {
    @Id
    private int id;
    private int userId;
    private double amount;
    private String type, description, month;

    public Expense()
    {
        super();
    }

    /**
     *
     * @param amount expense's amount
     * @param type expense's type
     * @param description expense's description
     * @param month expense's month
     * @param userId expense's user id
     */
    public Expense(double amount, String type, String description, String month, int userId) {
        setAmount(amount);
        setType(type);
        setDescription(description);
        setMonth(month);
        setUserId(userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount<=0)
            this.amount=9.99;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "userId=" + userId +
                ", id=" + id +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", month='" + month + '\'' +
                '}';
    }
}
