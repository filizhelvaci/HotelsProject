package com.flz.model.mapper;

import java.util.List;

public interface BaseMapper<S, T> {

    T map(S source);

    List<T> map(List<S> sources);

}