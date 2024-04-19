package com.ohgiraffers.section03.entity;
import jakarta.persistence.EntityManager;
public class EntityLifeCycle {

    private EntityManager entityManager;

    // 주어진 메뉴 코드에 해당하는 메뉴를 데이터베이스에서 조회하여 반환
    public Menu findMenuByMenuCode(int menuCode) {
        entityManager = EntityMangerGenerator.getInstance();
        return entityManager.find(Menu.class, menuCode);
    }

    // 엔티티 매니저 인스턴스를 반환한다. :  새로운 엔티티 매니저를 생성하여 반환한다라는 뜻
    // 엔티티 매니저란 ? 영속성 컨텍스트를 관리하고 데이테베이스와의 상호 작용을 담당하는 객체,

    public EntityManager getManagerInstance(){
        return entityManager;
    }
}
