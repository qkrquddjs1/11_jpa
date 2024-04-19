package org.example.jpql.section04.paging;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PagingRepository {

    @PersistenceContext
    private EntityManager entityManager;
    // 페이징 처리를 위한 메소드
    public List<Menu> usingPagingAPI(int offset, int limit) {
        String jpql = "SELECT m FROM Section04Menu m ORDER BY m.menuCode DESC";

        List<Menu> pagingMenuList = entityManager.createQuery(jpql, Menu.class)
                                                 .setFirstResult(offset)       // 조회를 시작할 위치 (0부터 시작) (offset) 오프셋
                                                 .setMaxResults(limit)         // 조회할 테이터의 개수   (limit) 리밋
                                                 .getResultList();
        return pagingMenuList;
    }



}
