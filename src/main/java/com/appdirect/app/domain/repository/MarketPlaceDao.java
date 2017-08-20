package com.appdirect.app.domain.repository;



import com.appdirect.app.domain.entity.Company;
import com.appdirect.app.domain.entity.MarketPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketPlaceDao extends JpaRepository<MarketPlace, Long> {
}
