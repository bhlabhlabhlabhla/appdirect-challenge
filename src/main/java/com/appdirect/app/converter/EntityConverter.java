package com.appdirect.app.converter;



import java.io.Serializable;

public interface EntityConverter<E extends Serializable, T> {
    E toEntity(T t);
}
