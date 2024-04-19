package org.example.jpql.scetion02.parameter;

import org.example.jpql.section02.parameter.Menu;
import org.example.jpql.section02.parameter.ParameterBindingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ParameterBindingRepositoryTests {

    @Autowired
    private ParameterBindingRepository parameterBindingRepository;

    @DisplayName("이름 기준 파라미터 바인딩 테스트")
    @Test
    void testParameterBindingByName() {
        //given
        String menuName = "한우딸기국밥";
        //when
        List<Menu> menuList = parameterBindingRepository.selectMenuByBindingName(menuName);
        //then
        assertEquals(menuName, menuList.get(0).getMenuName());
    }

    @DisplayName("위치 기준 파라미터 바인딩 테스트")
    @Test
    void testParameterBindingByPosition() {
        //given
        String menuName = "한우딸기국밥";
        //when
        List<Menu> menuList = parameterBindingRepository.selectMenuByBindingPosition(menuName);
        //then
        assertEquals(menuName, menuList.get(0).getMenuName());
    }
}
