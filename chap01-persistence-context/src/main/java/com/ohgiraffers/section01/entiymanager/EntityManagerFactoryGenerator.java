package com.ohgiraffers.section01.entiymanager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryGenerator {

    // "Jpatest"라는 이름의 영속성 유닛을 사용하여 EntityManagerFactory를 생성.
    private static EntityManagerFactory factory
            = Persistence.createEntityManagerFactory("jpatest");

    // 외부에서 객체를 인스턴스화하는 것을 막기 위해 생성자를 private으로 설정
    private EntityManagerFactoryGenerator() {}
    // 미리 생성된 EntityManaagerFactory를 반환하는 정적 메서드.
    public static EntityManagerFactory getInstance() { return factory; }
}
