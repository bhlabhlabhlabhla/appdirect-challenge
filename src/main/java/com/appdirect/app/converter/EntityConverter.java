package com.appdirect.app.converter;

import java.io.Serializable;

/**
 * This interface handles the conversion from DTO type objects to Hibernate entities.
 *
 * @param <E> Entity Type
 * @param <T> DTO type
 */
public interface EntityConverter<E extends Serializable, T> {
    void toEntity(E e, T t);
}
