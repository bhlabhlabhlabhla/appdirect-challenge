package com.appdirect.app.domain.repository;


import com.appdirect.app.domain.entity.SubscriptionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionUserDao extends JpaRepository<SubscriptionUser, Long> {

    @Query("select s from SubscriptionUser s where s.uuid=?1")
    SubscriptionUser findByUserUUID(String uuid);

    @Query("select s from SubscriptionUser s where s.uuid=?1 and s.subscription.accountIdentifier=?2")
    SubscriptionUser findByUserUUIDAndSubscriptionAccountIdentifier(String userUUID, String accountIdentifier);

}
