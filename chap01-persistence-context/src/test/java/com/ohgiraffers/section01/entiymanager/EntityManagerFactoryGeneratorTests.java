package com.ohgiraffers.section01.entiymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityManagerFactoryGeneratorTests {

    @Test
    @DisplayName("엔터티 매니저 팩토리 생성 확인")
    void testGenerateEntityManagerFactory() {
        // given

        // when
        EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();

        // then
        assertNotNull(factory);

    }

    @Test
    @DisplayName("엔터티 매니저 팩토리 싱글톤 인스턴스 확인")
    void testisEntityManagerFactorySingletonInstance(){
        // given

        // when
        EntityManagerFactory factory1 = EntityManagerFactoryGenerator.getInstance();
        EntityManagerFactory factory2 = EntityManagerFactoryGenerator.getInstance();

        // then
        assertEquals(factory1, factory2);
        assertEquals(factory1.hashCode(), factory2.hashCode());
    }



    @Test
    @DisplayName("엔터티 매니저 생성 확인")
    void testGenerateEntityManager() {
        // given

        //when
        EntityManager entityManager = EntityMangerGenerator.getInstance();

        //then
        assertNotNull(entityManager);


    }

    @Test
    @DisplayName("엔터티 매니저 스코프 확인")
    void testEntityManagerLifeCycle() {
        // given

        //when
        EntityManager entityManager1 = EntityMangerGenerator.getInstance();
        EntityManager entityManager2 = EntityMangerGenerator.getInstance();

        //then
        assertNotEquals(entityManager1, entityManager2);
        assertNotEquals(entityManager1.hashCode(), entityManager2.hashCode());

    }

}
