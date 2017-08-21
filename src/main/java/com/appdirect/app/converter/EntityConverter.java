package com.appdirect.app.converter;



import java.io.Serializable;

public interface EntityConverter<E extends Serializable, T> {
    void toEntity(E e, T t);
}
