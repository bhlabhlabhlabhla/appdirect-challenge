package com.appdirect.app.domain.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.appdirect.app.domain.entity.*;

@Repository
public interface SubscriptionDao extends JpaRepository<Subscription, Long> {

    @Query("select s from Subscription s where s.accountIdentifier=?1")
    Subscription findByAccountIdentifier(String accountIdentifier);
}
