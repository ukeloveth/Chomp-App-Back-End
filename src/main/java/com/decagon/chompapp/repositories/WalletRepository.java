package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
   Wallet findWalletByUser_Email(String email);
}
