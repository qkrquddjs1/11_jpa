package com.ohgiraffers.section01.entiymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EntityMangerGenerator {

    // EntityManagerFactoryGenerator를 사용하여 EntityManagerFactory를 가져온다.
    public static EntityManager getInstance() {
        EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();
        // 얻은 EntityManagerFactory를 사용하여 EntityManager를 생성하고 반환.
        return factory.createEntityManager();
    }
}
