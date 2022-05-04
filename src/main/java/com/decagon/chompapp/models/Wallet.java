package com.decagon.chompapp.models;


import javax.persistence.*;

@Entity
public class Wallet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @OneToOne
    private User user;

}
