package org.example.associationmapping.section01.manytoone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ManyToOneServiceTests {

    @Autowired // 의존성 주입
    private ManyToOneService manyToOneService;


    @DisplayName("N:1 연관관계 객체 그래프 탐색을 이용한 조회 테스트")
    @Test
    void manyToOneFindTest() {
        // giver : 테스트 실행전 주어진 상황
        int menuCode = 11; // 테스트할 메뉴의 코드를 지정합니다.

        // when : 주어진 상황에서 어떤동작을 수행하는지
        Menu foundMenu = manyToOneService.findMenu(menuCode); // 주어진 코드에 해당하는 메뉴를 찾습니다.

        //then : 특정동작을 실행한후에 결과를 보여주는
        Category category = foundMenu.getCategory(); // 찾은 메뉴에서 카테고리를 가져옵니다.
        assertNotNull(category);  // 카테고리가 null이 아닌지 확인합니다.
    }

    @DisplayName("N:1 연관관계 객체 지향 쿼리(JPQL)을 이용한 조회 테스트")
    @Test
    void manyToOneJpqlFindTest() {
        // giver
        int menuCode = 11; // 테스트할 메뉴의 코드를 지정

        // when
        String categoryName = manyToOneService.findCategoryNameByJpql(menuCode); // 주어진 코드에 해당하는 메뉴의 카테고리 이름을 JPQL을 사용하여 찾는다.

        //then
            assertNotNull(categoryName); // 카테고리 이름이 null이 아닌지 확인
        System.out.println("[Category Name]" + categoryName); // 카테고리 이름을 출력
    }

    // getMenuInfo() 메서드는 테스트에 사용될 메뉴 정보를 제공하는 스트림을 생성
    private static Stream<Arguments> getMenuInfo() {
        return Stream.of(
                // Arguments,of() 사용하여 하나의 메뉴 정보를 포함하는 Arguments 객체 생성
                Arguments.of(123, "돈가스 스파게티", 30000, 123, "퓨전분식", "Y")
        );
    }

    @DisplayName("N:1 연관관계 객체 삽입 테스트")
    @ParameterizedTest
    @MethodSource("getMenuInfo")
    void manyToOneInsertTest(int menuCode, String menuName, int menuPrice, int categoryCode, String categoryName, String orderableStatus) {
        // given // 주어진 메뉴 정보를 이용하여 MenuDTO 객체를 생성
        MenuDTO menuInfo = new MenuDTO(
                menuCode,
                menuName,
                menuPrice,
                // 카테고리 정보를 포함하는 CategoryDTO 객체를 생성
                new CategoryDTO(
                        categoryCode,
                        categoryName,
                        null
                ),
                orderableStatus
        );

        //when
        //then  // 메뉴를 등록하는 메서드를 호출하고 예외가 발생하지 않은지 확인.
        assertDoesNotThrow(
                () -> manyToOneService.registMenu(menuInfo)
        );
    }





}
