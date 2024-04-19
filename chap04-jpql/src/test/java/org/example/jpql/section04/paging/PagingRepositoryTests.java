package org.example.jpql.section04.paging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest  // 스프링부트에서 테스트 코드 할때 필요한 등록
public class PagingRepositoryTests {

    @Autowired //의존성 주입
    private PagingRepository pagingRepository;


    @DisplayName("페이징 api를 이용한 조회 테스트")
    @Test
    void testUsingPagingAPI() {
        int offset = 10;
        int limit = 5;

        List<Menu> menuList = pagingRepository.usingPagingAPI(offset, limit);

        assertTrue(menuList.size() > 0 && menuList.size() <6);
        menuList.forEach(System.out::println);
    }
}
