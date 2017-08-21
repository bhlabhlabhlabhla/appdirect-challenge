package com.appdirect.app.domain.repository;


import com.appdirect.app.domain.entity.SubscriptionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "subscriptionUsers", path = "subscriptionUsers")
public interface SubscriptionUserDao extends PagingAndSortingRepository<SubscriptionUser, Long> {

    @Query("select s from SubscriptionUser s where s.uuid=?1")
    SubscriptionUser findByUserUUID(String uuid);

    @Query("select s from SubscriptionUser s where s.uuid=?1 and s.subscription.accountIdentifier=?2")
    SubscriptionUser findByUserUUIDAndSubscriptionAccountIdentifier(String userUUID, String accountIdentifier);

}
