package com.appdirect.app.domain.repository;


import com.appdirect.app.domain.entity.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * DAO operations on Subscription Entity
 */
@RepositoryRestResource(collectionResourceRel = "subscriptions", path = "subscriptions")
public interface SubscriptionDao extends PagingAndSortingRepository<Subscription, Long> {

    @Query("select s from Subscription s where s.accountIdentifier=?1")
    Subscription findByAccountIdentifier(String accountIdentifier);
}
