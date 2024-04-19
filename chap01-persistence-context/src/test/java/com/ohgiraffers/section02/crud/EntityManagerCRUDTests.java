package com.ohgiraffers.section02.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityManagerCRUDTests {

    private EntityManagerCRUD crud; // EntityManagerCRUD 객체를 선언합니다.

    @BeforeEach
    void initManager() {
        this.crud = new EntityManagerCRUD();
    } // 각 테스트가 실행되기 전에 EntityManagerCRUD 객체를 초기화한다.


    @DisplayName("메뉴 코드로 메뉴 조회 테스트") // 이 테스트는 메뉴 코드로  메뉴를 조회하는 메소드를 테스트합니다.
    @ParameterizedTest // 하나의 테스트 메소드로 여러 개의 파라미터에 대한 테스트가 가능함
    @CsvSource({"1,1", "2,2", "3,3"}) // CSV 형식으로 입력 값을 제공하여 테스트를 실행합니다.
    void testFindMethodByMenuCode(int menuCode, int expected) {

        // when
        Menu foundMenu = crud.findMenuByMenuCode(menuCode); // 주어진 메뉴코들 조회합니다.

        //then
        assertEquals(expected, foundMenu.getMenuCode()); // 반환된 메뉴의 메뉴 코드가 예상 값과 일치하는지 확인.
        System.out.println("foundMenu=" + foundMenu); // 조회된 메뉴의 정보를 출력.
    }

    // 새로운 메뉴를 생성하는데 사용될 테스트 케이스를 반환하는 정적 메소드
    private static Stream<Arguments> newMenu() {
        return Stream.of( // 메뉴의 이름.가격,카테고리 코드, 주문 가능 여부를 매개변수로 전달
                Arguments.of(
                        "신메뉴",     //메뉴의 이름
                        35000,                  //메뉴의 가격
                        4,                      //메뉴의 카테고리 코드
                        "Y"                     // 메뉴의 주문가능 여부
                )

        );
    }

    @DisplayName("새로운 메뉴 추가 테스트")
    @ParameterizedTest
    @MethodSource("newMenu")
    void testRegist(String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        // when // 새로운 메뉴 객체를 생성합니다. 매개변수로 받은 값을 사용하여 생서합니다.
        Menu newMenu = new Menu(menuName, menuPrice, categoryCode, orderableStatus);
        // 새로윤 메뉴를 저장하고, 저장된 메뉴의 개수를 반환한다.
        Long count = crud.saveAndReturnAllCount(newMenu);

        // then // 저장된 메뉴의 개수가 33인지 확인합니다.
        assertEquals(33, count);
    }

    @DisplayName("메뉴 이름 수정 테스트")
    @ParameterizedTest
    @CsvSource("1, 변경 된 이름")
    void testModifyMenuName(int menuCode, String menuName) {
        //when // 수정된 메뉴의 정보를 받아옵니다. 메뉴 코드와 새로운 이름을 이용하여 수정 된다.
        Menu modifiedMenu = crud.modifyMenuName(menuCode, menuName);

        // then // 수정된 메뉴의 이름이 기대한 새로운 이름과 일치하는지 확인합니다.
        assertEquals(menuName, modifiedMenu.getMenuName());
    }

    @DisplayName("메뉴 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = {39})
    void testRemoveMenu(int menuCode) {
        //when  //주어진 메뉴 코드를 가진 메뉴를 삭제하고 삭제 후 전체 메뉴 개수를 반환한다,
        Long count = crud.removeAndReturnAllCount(menuCode);

        //then // 삭제된 후 전체 메뉴 개수가 기대하는 값(32)과 일치하는지 확인한다.
        assertEquals(32, count);


    }




}
