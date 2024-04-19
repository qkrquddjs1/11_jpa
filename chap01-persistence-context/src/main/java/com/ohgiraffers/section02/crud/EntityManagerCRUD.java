package com.ohgiraffers.section02.crud;

import com.ohgiraffers.section01.entiymanager.EntityMangerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EntityManagerCRUD {

    private EntityManager entityManager;

    /* 1. 특정 코드로 조회하는 기능 */
    public Menu findMenuByMenuCode(int menuCode) {
        // EntityMangerGenerator클래스를 통해 데이터 베이스와 연결 , EntityManagerFactory를 가져옵니다.
        entityManager = EntityMangerGenerator.getInstance();
        // 주어진 메뉴 코드를 사용하여 데이터베이스에서 해당하는 메뉴를 조회하고 반환한다.
        return entityManager.find(Menu.class, menuCode);
    }

    /* 2. 메뉴 데이터 저장하는 기능 */
    public Long saveAndReturnAllCount(Menu newMenu) {
        // EntityMangerGenerator 클래스를 통해 데이터베이스와의 연결을 설정하고, 이를 통해 EntityManager 인스턴스를 얻습니다.
        entityManager = EntityMangerGenerator.getInstance();
        // 데이터베이스 작업을 위한 트랜잭션을 가져와 EntityTransaction 객체에 할당합니다.
        EntityTransaction entityTransaction = entityManager.getTransaction();
        // 트랜잭션 시작
        entityTransaction.begin();
        // 새로운 메뉴 객체를 (newMenu)를 데이터 베이스에 저장한다.
        entityManager.persist(newMenu);
        // 트랜잭션을 커밋하여 데이터 베이스에 변경 사항을 적용합니다.
        entityTransaction.commit();
        // 변경된 데이터베이스의 모든 메뉴 수를 반환합니다. entityManager를 매개변수로 받아 데이터 베이스 에서 모든 메뉴의 수를 조회
        return getCount(entityManager);
    }

    private Long getCount(EntityManager entityManager) {
        // JPQL 문법 -> 나중에 별도 챕터에서 다름
        return entityManager.createQuery("SELECT COUNT(*) FROM Section02Menu", Long.class).getSingleResult();
    }

    /* 3. 메뉴 데이터 수정하는 기능 */
    public Menu modifyMenuName(int menuCode, String menuName){
        // EntityManagerFactoryGenerator를 통해 EntityManagerFactory를 얻음
        entityManager = EntityMangerGenerator.getInstance();
        // 데이터 베이스에서 해당 메뉴 코드에 해당하는 메뉴를 조회한다.
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        // 데이터베이스 작업을 위한 트랜잭션 시작
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 조회한 메뉴의 이름을 새로운 메뉴 이름으로 변경한다.
        foundMenu.setMenuName(menuName);
        // 트랜잭션 커밋
        transaction.commit();
        // 수정된 메뉴 객체 반환
        return foundMenu;

    }

    /* 4. 삭제하는 기능 */
    public Long removeAndReturnAllCount(int menuCode) {
        // EntityMangerGenerator 클래스에서 데이터베이스 연결, EntityManagerFactory를 얻어온다.
        entityManager = EntityMangerGenerator.getInstance();
        // 주어진 메뉴 코드를 사용하여 데이터베이스에서 해당하는 메뉴를 조회
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        // 데이터베이스 작업을 위한 트랜잭션 시작
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 조회한 메뉴를 데이터 베이스에서 삭제
        entityManager.remove(foundMenu);
        // 트랜잭션을 커밋한다. (저장)
        transaction.commit();
        // 현재 데이터베이스에 있는 모든 메뉴의 수를 반환.
        return getCount(entityManager);
    }



}
