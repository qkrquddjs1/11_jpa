package com.ohgiraffers.section03.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityMangerGenerator {

    // Persistence 클래스를 사용하여 "Jpatest"라는 이름의 데이터베이스 유닛(unit)을 가진 EntityManagerFactory를 생성.
    private static EntityManagerFactory factory
            = Persistence.createEntityManagerFactory("jpatest");

    private EntityMangerGenerator() {}
    // EntityManager를 반환하는 정적 메소드.
    public static EntityManager getInstance() {
        // EntityManagerFactory를 사용하여 새로운 EntityManager를 생성하고 반환.
        return factory.createEntityManager();
    }
}
