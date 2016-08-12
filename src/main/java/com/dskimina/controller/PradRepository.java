package com.dskimina.controller;

import com.dskimina.mapping.Odczyt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by daniel on 09.08.16.
 */
@Repository
@Transactional
public class PradRepository {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public List<Odczyt> getAll() {
        return getSession().createCriteria(Odczyt.class).list();
    }
}
