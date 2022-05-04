
//package com.decagon.chompapp.models;
//
//import javax.persistence.*;
//import java.util.Date;
//
//public class Favourite {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long favouriteId;
//
//    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "user_id")
//    private Users user;
//
//    @Column(name = "created_date")
//    private Date createdDate;
//
//    @ManyToOne()
//    @JoinColumn(name = "product_id")
//    private Product product;
//}



