package org.example.jpql.section06.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JoinRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Menu> selectByInnerJoin() {
        String jpql = "SELECT m FROM Section06Menu m JOIN m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        return menuList;
    }

    public List<Object[]> selectByOuterJoin() {
        String jpql = "SELECT m.menuName, c.categoryName FROM Section06Menu m RIGHT JOIN m.category c"
                    + "ORDERBY m.category.categoryCode";
        List <Object[]> menuList = entityManager.createQuery(jpql).getResultList();
        return menuList;

    }

    public List<Object[]> selectByCollectionJoin() {
        String jpql = "SELECT c.categoryName, m.menuName FROM Section06Category c LEFT JOIN c.menuList m";
        List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();
        return categoryList;
    }

    public List<Object[]> selectByThetaJoin() {
        String jpql = "SELECT c.categoryName, m.menuName FROM Section06Category c, Section06Menu m";
        List<Object[]> categoryList = entityManager.createQuery(jpql).getResultList();

        return categoryList;
    }

    public List<Menu> selectByFetchJoin() {
        /* FETCH : JPQL에서 성능 최적화를 위해 제공하는 기능으로 연관 된 엔터티나 컬렉션을 한 번에 조회한다.
        * 지연 로딩이 아닌 즉시 로딩을 수행한다.*/
        String jpql = "SELECT m FROM Section06Menu m JOIN FETCH m.category c";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();
        return menuList;
    }
}
