package com.batch.service;

import com.batch.entities.Person;
import com.batch.persistence.IPersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements IPersonService {

    @Autowired
    private IPersonDAO personDAO;

    @Override
    public void save(Person person) {
        personDAO.save(person);
    }

    @Override
    public void saveAll(List<Person> personList) {
        personDAO.saveAll(personList);
    }
}
