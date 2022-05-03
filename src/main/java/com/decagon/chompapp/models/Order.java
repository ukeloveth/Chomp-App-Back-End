package com.decagon.chompapp.models;

import javax.persistence.*;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "total_price")
    private Double totalPrice;


    @OneToOne(fetch = FetchType.LAZY)
    private Cart orderItems;

    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
}
