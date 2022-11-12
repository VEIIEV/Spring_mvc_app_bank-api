package com.example.bank_api.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "usersIdSeq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersIdSeq")
    private Long id;

    @Column(name = "balance")
    private double balance;

    public User(double balance) {
        this.balance=balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void put(double value) {
        this.balance += value;
    }

    public int withdraw(double value) {
        if (this.balance - value >= 0) {
            this.balance -= value;
            return 1;
        } else return 0;
    }

    @Override
    public String toString() {
        return "User " +
                "id=" + id +
                ", balance=" + balance +
                ' ';
    }
}
