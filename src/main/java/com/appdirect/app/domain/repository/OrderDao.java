package com.appdirect.app.domain.repository;



import com.appdirect.app.domain.entity.MarketPlace;
import com.appdirect.app.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
}
