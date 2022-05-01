package com.code.et.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tracker")
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @Email
    @Column(name = "email", unique = true)
    private String email;
    @NotBlank
    @Size(min = 6)
    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tracker_id")
    private List<Category> categories;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tracker_id")
    private List<Transaction> transactions;

    public Tracker(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // convenience method
    public void addCategory(Category category) {
        if(categories == null) {
            categories = new ArrayList<>();
        }
        categories.add(category);
    }

    public void addTransaction(Transaction transaction) {
        if(transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
    }
}
