package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/* <엔티티, Id타입> */
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    /* JpaRepository를 상속받아서 인터페이스를 정의한 이유는 별도의 데이터 엑세스메소드를 작성하지않고
    *  기본적인 CRUD 기능을 사용할수 있다. 인터페이스에는 별도의 메소드 작성이 필요없다 */

    /* 파라미터로 전달 받은 가격을 초과하는 메뉴 목록 조회 */
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice);

    /* 파라미터로 전달 받은 가격을 초과하는 메뉴 목록 가격순으로 조회 */
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(Integer menuPrice);

    /* 파라미터로 전달 받은 가격을 초과하는 메뉴 목록 전달 받은 정렬 기준으로 조회 */
    List<Menu> findByMenuPriceGreaterThan(Integer menuPrice, Sort sort);

}

