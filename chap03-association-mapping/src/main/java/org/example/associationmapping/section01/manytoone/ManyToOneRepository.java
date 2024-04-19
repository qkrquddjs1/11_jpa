package org.example.associationmapping.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ManyToOneRepository {

    @PersistenceContext
    private EntityManager entityManager;
    // 메뉴조회 메서드
    public Menu find(int menuCode) {
        return entityManager.find(Menu.class, menuCode);
    }
    // JPQL을 이용한 카테고리 이름 조회 메서드
    public String findCategoryName(int menuCode) {
        // JPQL을 이용하여 주어진 메뉴코드에 해당하는 카테고리 이름을 조회합니다.
        String jpql = "SELECT c.categoryName FROM menu_and_category m JOIN m.category c WHERE m.menuCode = :menuCode";

        return entityManager.createQuery(jpql, String.class)
                .setParameter("menuCode", menuCode)
                .getSingleResult();
    }

    // 메뉴 등록 메서드
    public void regist(Menu menu) {
        entityManager.persist(menu);
    }


}
