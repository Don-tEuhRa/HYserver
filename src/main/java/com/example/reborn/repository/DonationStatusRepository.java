package com.example.reborn.repository;

import com.example.reborn.type.entity.DonationStatus;
import com.example.reborn.type.entity.Receipt;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.ReceiptStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationStatusRepository extends JpaRepository<DonationStatus, Long> {
 Optional<DonationStatus> findByReceipt(Receipt receipt);

    DonationStatus findByUser(User user);

    List<DonationStatus> findAllByUser(User user);

   List<DonationStatus> findAllByUserAndStatusName(User user,ReceiptStatus statusName);

   Long countByUserAndStatusName(User user, ReceiptStatus statusName);
}
