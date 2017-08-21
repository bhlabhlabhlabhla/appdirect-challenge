package com.appdirect.app.validation;



import com.appdirect.app.dto.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

public interface EventValidator<E extends Serializable, ID extends Serializable, R extends RuntimeException> {

    void validateEvent(Event event, Class<E> tClazz, ID id, CrudRepository jpaRepository, R r);

}
