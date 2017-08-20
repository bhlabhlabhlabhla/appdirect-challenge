package com.appdirect.app.service.processor;


import com.appdirect.app.converter.EntityConverterService;
import com.appdirect.app.domain.entity.*;
import com.appdirect.app.domain.entity.type.SubscriptionState;
import com.appdirect.app.domain.repository.*;
import com.appdirect.app.dto.AbstractNotificationResponse;
import com.appdirect.app.dto.Error;
import com.appdirect.app.dto.Event;
import com.appdirect.app.dto.SuccessNotificationResponse;
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
    @Autowired
    protected EntityConverterService entityConverterService;
    Logger logger = LoggerFactory.getLogger(SubscriptionCreateProcessor.class);

    @Override
    @Transactional
    public AbstractNotificationResponse processEvent(Event event) {
        logger.info("Handling Subscription Create Event");

        // validations
        validate(event);

        Company company = (Company) entityConverterService.convert(event.getPayload().getCompany());
        companyDao.save(company);

        MarketPlace marketPlace = (MarketPlace) entityConverterService.convert(event.getMarketPlace());
        marketPlaceDao.save(marketPlace);

        Order order = (Order) entityConverterService.convert(event.getPayload().getOrder());
        orderDao.save(order);

        Subscription subscription = (Subscription) entityConverterService.convert(event);
        subscription.setCompany(company);
        subscription.setMarketPlace(marketPlace);
        subscription.setOrder(order);
        subscription.setState(SubscriptionState.ACTIVE);
        subscriptionDao.save(subscription);
        logger.info("Subscription created for Creator UUID: {} with Subscription Id: {}", subscription.getAccountIdentifier(), subscription.getId());

        SubscriptionUser subscriptionUser = (SubscriptionUser) entityConverterService.convert(event.getCreator());
        subscriptionUser.setSubscription(subscription);
        subscriptionUser.setAdministrator(true);
        subscriptionUserDao.save(subscriptionUser);
        logger.info("Administrator user with UUID: {} created for Company with UUId: {}", subscriptionUser.getUuid(), subscription.getCompany().getCompanyUuid());
        return new SuccessNotificationResponse(subscription.getAccountIdentifier(), String.valueOf(subscriptionUser.getId()));
    }

    private void validate(Event event) {
        EventValidator validator = new UniqueIdValidator(subscriptionDao);
        String errorMessage = String.format("Subscription with Creator UUID: %s already exists", event.getCreator().getUuid());
        validator.validateEvent(event, Subscription.class, event.getCreator().getUuid(), new EventValidationFailedException(errorMessage, Error.FORBIDDEN));
    }


}
