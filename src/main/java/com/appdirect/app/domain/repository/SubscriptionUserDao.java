package com.appdirect.app.domain.repository;



import com.appdirect.app.domain.entity.Order;
import com.appdirect.app.domain.entity.SubscriptionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionUserDao extends JpaRepository<SubscriptionUser, Long> {
}
