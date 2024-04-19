package org.example.associationmapping.section02.onetomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OneToManyRepository {
    // EntityManager 인터페이스는 JPA에서 엔티티를 관리하고 데이터베이스와의 상호작용을 담당합니다.
    /* @PersisteneceContext 어노테이션은 영속성 컨텍스트를 주입하기위해 사용
    *  영속성 컨텍스트란 엔티티를 관리하고 영속 상태로 유지하는 환경을 의미한다
    *  따라서 이 어노테이션이 지정된 entityManager는 데이터베이스와의 상효작용을 가능하게 해준다*/
    @PersistenceContext
    private EntityManager entityManager;

    // 주어진 카테고리 코드에 해당하는 카테고리를 데이터베이스에서 찾아 반환
    public Category find(int categoryCode) {
        return entityManager.find(Category.class, categoryCode);
    }
    // 주어진 카테고리를 데이터베이스에 저장
    public void regist(Category category) {
        entityManager.persist(category);
    }

}
