package com.hibernate.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
