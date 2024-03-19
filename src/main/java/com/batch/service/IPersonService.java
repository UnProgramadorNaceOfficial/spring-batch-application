package com.batch.service;

import com.batch.entities.Person;

import java.util.List;

public interface IPersonService {

    void save(Person person);

    void saveAll(List<Person> personList);
}
