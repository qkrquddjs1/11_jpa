package org.example.jpql.section03.projection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProjectionServiceTests {

    @Autowired  // 의존성 주입
    private ProjectionService projectionService;
    @Autowired  // 의존성 주입
    private ProjectionRepository projectionRepository;

    @DisplayName("단일 엔터티 프로젝션 테스트")
    @Test
    void testSingleEntityProjection() {
        List<Menu> menuList = projectionService.singleEntityProjection();
        assertNotNull(menuList);
    }

    @DisplayName("임베디드 타입 프로젝션")
    @Test
    void testEmbeddedTypeProjection() {
        List<MenuInfo> menuInfoList = projectionRepository.embeddedTypeProjection();
        assertNotNull(menuInfoList);
    }

    @DisplayName("스칼라 타입 프로젝션")
    @Test
    void testScalarTypeProjection() {
        List<Objects[]> categoryList = projectionRepository.scalarTypeProjection();
        assertNotNull(categoryList);
        categoryList.forEach(
                row -> {
                    for (Object column : row) {
                        System.out.println(column + "");
                    }
                    System.out.println();
                }
        );
    }

    @DisplayName("new 명령어를 활용한 프로젝션")
    @Test
    void testNewCommandProjection() {
        List<CategoryInfo> categoryInfoList = projectionRepository.newCommandProjection();
        assertNotNull(categoryInfoList);
        categoryInfoList.forEach(System.out::println);
    }
}
