package com.appdirect.app.service.processor;



import com.appdirect.app.converter.*;
import com.appdirect.app.domain.entity.*;
import com.appdirect.app.domain.entity.Company;
import com.appdirect.app.domain.entity.MarketPlace;
import com.appdirect.app.domain.entity.Order;
import com.appdirect.app.domain.entity.type.SubscriptionState;
import com.appdirect.app.domain.repository.*;
import com.appdirect.app.dto.*;
import com.appdirect.app.dto.Error;
import com.appdirect.app.exception.EventValidationFailedException;
import com.appdirect.app.validation.EventValidator;
import com.appdirect.app.validation.UniqueIdValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionCreateProcessor implements EventProcessor {

    Logger logger = LoggerFactory.getLogger(SubscriptionCreateProcessor.class);

    @Autowired
    protected SubscriptionDao subscriptionDao;

    @Autowired
    protected SubscriptionUserDao subscriptionUserDao;

    @Autowired
    protected CompanyDao companyDao;

    @Autowired
    protected MarketPlaceDao marketPlaceDao;

    @Autowired
    protected OrderDao orderDao;


    @Override
    @Transactional
    public AbstractNotificationResponse processEvent(Event event) {
        logger.info("Handling Subscription Create Event");

        // validations
        validate(event);

       // creation
        EntityConverter<Subscription, Event> subscriptionEntityConverter = new SubscriptionEntityConverter();
        EntityConverter<Company, com.appdirect.app.dto.Company> companyEntityConverter = new CompanyEntityConverter();
        EntityConverter<MarketPlace, com.appdirect.app.dto.MarketPlace> marketPlaceEntityConverter = new MarketPlaceEntityConverter();
        EntityConverter<Order, com.appdirect.app.dto.Order> orderEntityConverter = new OrderEntityConverter();

        Company company = companyEntityConverter.toEntity(event.getPayload().getCompany());
        companyDao.save(company);

        MarketPlace marketPlace = marketPlaceEntityConverter.toEntity(event.getMarketPlace());
        marketPlaceDao.save(marketPlace);

        Order order = orderEntityConverter.toEntity(event.getPayload().getOrder());
        orderDao.save(order);

        Subscription subscription = subscriptionEntityConverter.toEntity(event);
        subscription.setCompany(company);
        subscription.setMarketPlace(marketPlace);
        subscription.setOrder(order);
        subscription.setState(SubscriptionState.ACTIVE);
        subscriptionDao.save(subscription);
        logger.info("Subscription created for Creator UUID: {} with Subscription Id: {}", subscription.getAccountIdentifier(), subscription.getId());

        EntityConverter<SubscriptionUser, Creator> subscriptionUserEntityConverter = new SubscriptionUserEntityConverter();
        SubscriptionUser subscriptionUser = subscriptionUserEntityConverter.toEntity(event.getCreator());
        subscriptionUser.setSubscription(subscription);
        subscriptionUser.setAdministrator(true);
        subscriptionUserDao.save(subscriptionUser);
        logger.info("Administrator user with UUID: {} created for Company with UUId: {}", subscriptionUser.getUuid(), subscription.getCompany().getCompanyUuid());
        printCurrentDbRecord();
        return new SuccessNotificationResponse(subscription.getAccountIdentifier(), String.valueOf(subscriptionUser.getId()));
    }

    @SuppressWarnings("unchecked")
    private void validate(Event event) {
        EventValidator validator = new UniqueIdValidator(subscriptionDao);
        String errorMessage = String.format("Subscription with Creator UUID: %s already exists", event.getCreator().getUuid());
        validator.validateEvent(event, Subscription.class, event.getCreator().getUuid(), new EventValidationFailedException(errorMessage, Error.FORBIDDEN));
    }

    void printCurrentDbRecord() {
        logger.info("____ Current Subscriptions ____");
        subscriptionDao.findAll().forEach(subscription -> {
            logger.info(subscription.toString());
        });
    }


}
