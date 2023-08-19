package com.example.reborn.repository;

import com.example.reborn.type.entity.Receipt;
import com.example.reborn.type.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    List<Receipt> findAllByUser(User user);

   Optional<Receipt> findByReceiptId(Long receiptId);

    Optional<Receipt> findByReceiptIdAndUser(Long receiptId, User user);
}
