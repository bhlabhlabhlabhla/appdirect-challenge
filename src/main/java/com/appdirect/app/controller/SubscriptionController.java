package com.appdirect.app.controller;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.repository.SubscriptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionDao subscriptionDao;

    @RequestMapping(path = "/subscriptions", method = RequestMethod.GET)
    public List<Subscription> getAllSubscriptions() {
        return (List<Subscription>) subscriptionDao.findAll();
    }
    @RequestMapping(path = "/subscriptions/{id}", method = RequestMethod.GET)
    public Subscription getSubscription(@PathVariable("id") long id) {
        return subscriptionDao.findOne(id);
    }

}
