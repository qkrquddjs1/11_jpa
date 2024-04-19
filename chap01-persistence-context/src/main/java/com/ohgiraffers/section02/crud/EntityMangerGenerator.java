package com.ohgiraffers.section02.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityMangerGenerator {

    private static EntityManagerFactory factory
            = Persistence.createEntityManagerFactory("jpatest");

    private EntityMangerGenerator() {}

    // EntotyManger 객체를 반환하는 정적 메소드
    public static EntityManager getInstance() {
        // 미리 생성한 EntityManagerFactory에서 EntityManager를 생성하여 반환.
        return factory.createEntityManager();
    }
}
