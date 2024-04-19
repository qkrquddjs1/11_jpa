package org.example.jpql.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public class SimpleJPQLRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String selectSingleMenuByTypedQuery() {
        String jpql = "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        String resultMenuName = query.getSingleResult(); //하나의 행만 입력받을떄 쓴다 여러 행의 결과를 받고 싶을떄 쓰는  query.getResultList()이 있다.
        return resultMenuName;

    }

    public Object selectSingleMenuByQuery() {
        String jpql = "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";
        Query query = entityManager.createQuery(jpql);
        Object resultMenuName = query.getSingleResult(); //하나의 행만 입력받을떄 쓴다 여러 행의 결과를 받고 싶을떄 쓰는  query.getResultList()이 있다.
        return resultMenuName;

    }

    public Menu selectSingleRowByTypedQuery() {
        String jpql = "SELECT m FROM Section01Menu as m WHERE m.menuCode = 8";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        Menu resultMenu = query.getSingleResult(); //하나의 행만 입력받을떄 쓴다 여러 행의 결과를 받고 싶을떄 쓰는  query.getResultList()이 있다.
        return resultMenu;
    }

    public List<Menu> selectMultiRowByTypedQuery() {
        String jpql = "SELECT m FROM Section01Menu as m ";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> resultMenuList = query.getResultList(); // 여러행의 결과를 받을떄 쓴다.
        return resultMenuList;
    }

    public List<Integer> selectUsingDistinct() {
        String jpql = "SELECT DISTINCT m.categoryCode FROM Section01Menu m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> resultCategoryList = query.getResultList(); // 여러행 결과를 받을떄 쓴다.

        return resultCategoryList;
    }

    /* 11, 12 카테고리 코드를 가진 메뉴 목록 조회 */
    public List<Menu> selectUsingIn() {
        String jpql = "SELECT m FROM Section01Menu as m WHERE m.categoryCode IN (11, 12)";
        List<Menu> resultMenuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        return resultMenuList;
    }
    /* "마늘"이라는 문자열이 메뉴명에 포함되는 메뉴 목록 조회 */
    public List<Menu> selectUsingLike() {
        String jpql = "SELECT m FROM Section01Menu as m WHERE m.menuName LIKE '%마늘%'";
        List<Menu> resultMenuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        return resultMenuList;

    }
}
