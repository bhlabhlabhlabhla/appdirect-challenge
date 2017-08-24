package com.appdirect.app.validation;

import com.appdirect.app.dto.Event;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * Entry point into Validation Services. Here I have defined validation rules that can be executed for event handling.
 * @param <E> Entity Type
 * @param <ID> ID Type
 * @param <R> RunTime Exception Type
 */
public interface EventValidator<E extends Serializable, ID extends Serializable, R extends RuntimeException> {

    /**
     * Validation method which can be implemented to do any kind of validations on provided dto or entity
     * and if validation fails a provided RuntimeException type will be thrown
     * @param event AppDirect Event which is being handled
     * @param tClazz Entity Class
     * @param id ID of Entity
     * @param jpaRepository Repository to execute DOA operators
     * @param r RuntimeException type
     */
    void validateEvent(Event event, Class<E> tClazz, ID id, CrudRepository jpaRepository, R r);

}
