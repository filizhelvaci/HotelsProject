package com.flz.utils;

import com.flz.model.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class ServiceManager<T extends BaseEntity, ID> implements IService<T,ID>{
    private final JpaRepository<T,ID> repository;

    public ServiceManager(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T t) {
        long time=System.currentTimeMillis();
        t.setState(true);
        t.setCreateAt(time);
        return repository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
        long time=System.currentTimeMillis();
        t.forEach(x->{
            x.setState(true);
            x.setCreateAt(time);
        });
        return repository.saveAll(t);
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }


    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }
}
