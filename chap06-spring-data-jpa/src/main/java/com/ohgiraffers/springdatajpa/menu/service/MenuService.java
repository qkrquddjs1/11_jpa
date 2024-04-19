package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // 룸북으로 클래스에 있는 final 필드를 이용하여 생성자를 자동생성 가능
public class MenuService {

    private final MenuRepository menuRepository; // 의존성 주입
    private final CategoryRepository cateogryRepository;
    private final ModelMapper modelMapper;  // 의존성 주입



    /* 1. findById*/
    // 메뉴 코드로 메뉴를 찾아서  MenuDTO로 변환하여 반환
    public MenuDTO findMenuByMenuCode(int menuCode) {
        // menuRepository를 사용하여 주어진 메뉴 코드에 해당하는 메뉴를 데이터베이스에서 찾음
        Menu foundMenu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(foundMenu, MenuDTO.class); // Menu를 MenuDTO로 매핑하여 반환
    }
    /* 2. finaAll : All*/
    // 모든 메뉴를 찾아서 MenuDTO 리스트로 변환하여 반환
    public List<MenuDTO> findMenuList() {
        // menuRepository를 사용하여 모든 메뉴를 데이터베이스에서 가져옴 (메뉴 코드 기준으로 내림차순 정렬)
        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());
        // 메뉴 리스트를 스트림으로 변환한 후, 각 메뉴를 MenuDTO로 매핑하여 리스트로 변환하여 반환
        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .toList();
    }

    /* 3. findAll : pageable */
    public Page<MenuDTO> findMenuList(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("menuCode").descending()
        );

        Page<Menu> menuList = menuRepository.findAll(pageable);
        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }

    /* 4. Query Method*/

    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);
//        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(
                menuPrice,
                Sort.by("menuPrice").descending()
                );

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .toList();

    }

    /* 5. JPQL or Native Query */
    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList = cateogryRepository.findAllCategory();


        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }

    /* 6. save */
    @Transactional
    public void registMenu(MenuDTO menuDTO) {

        menuRepository.save(modelMapper.map(menuDTO, Menu.class));


    }

    /* 7. 수정(엔터티 객체 필드 값 변경) */
    @Transactional
    public void modifyMenu(MenuDTO menuDTO) {
        Menu foundMenu = menuRepository.findById(menuDTO.getMenuCode()).orElseThrow(IllegalArgumentException::new);

        /* setter 사용 지양, 기능에 맞는 메소드 정의해서 사용 */
        foundMenu.modifyMenuName(menuDTO.getMenuName());

    }

    /* 8. 삭제 deleteById */
    @Transactional
    public void deleteMenu(Integer menuCode) {
        menuRepository.deleteById(menuCode);

    }
}

