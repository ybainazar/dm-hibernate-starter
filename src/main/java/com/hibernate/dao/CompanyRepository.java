package com.hibernate.dao;

import com.hibernate.entity.Company;
import org.hibernate.SessionFactory;

public class CompanyRepository extends RepositoryBase<Integer, Company> {

    public CompanyRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Company.class);
    }
}
