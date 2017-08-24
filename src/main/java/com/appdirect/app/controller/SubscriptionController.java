package com.appdirect.app.controller;

import com.appdirect.app.domain.entity.Subscription;
import com.appdirect.app.domain.repository.SubscriptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest Controller to execute HTTP methods on Subscriptions.
 */
@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionDao subscriptionDao;

    /**
     * Handles the HTTP GET method on Path '/subscriptions'
     *
     * It fetches all the subscriptions from database using SubscriptionDao layer.
     *
     * @return List of Subscriptions found in our database.
     */
    @RequestMapping(path = "/subscriptions", method = RequestMethod.GET)
    public List<Subscription> getAllSubscriptions() {
        return (List<Subscription>) subscriptionDao.findAll();
    }

    /**
     * Handles the HTTP GET method on Path '/subscriptions/{ID}'
     *
     * It fetches a specific subscription based on provided subscription id on path parameter
     *
     * @param id Subscription ID
     * @return Fetched Subscription from database
     */
    @RequestMapping(path = "/subscriptions/{id}", method = RequestMethod.GET)
    public Subscription getSubscription(@PathVariable("id") long id) {
        return subscriptionDao.findOne(id);
    }

}
