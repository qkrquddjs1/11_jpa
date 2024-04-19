package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.common.Pagenation;
import com.ohgiraffers.springdatajpa.common.PagingButton;
import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller  // 컨트롤러 설정
@RequestMapping("/menu") // 이컨트롤러의 모든 핸들러에 대한 기본 URL경로를 /menu 로 설정
@RequiredArgsConstructor // 롬복을 사용하여 필수 생성자를 생성
public class MenuController {

    private final MenuService menuService; // 의존성 주입

    @GetMapping("/{menuCode}") // GET 메소드로 요청을 처리하는 메소드
    public String findMenuByCode(@PathVariable int menuCode, Model model) { // menuCode 변수를 받아들이고, Model 객체를 받아들이는 findMenuByCode 메소드를 정의

        // menuCode에 해당하는 메뉴를 조회
        MenuDTO resultMenu = menuService.findMenuByMenuCode(menuCode);
        // 모델에 조회된 메뉴를 추가
        model.addAttribute("menu", resultMenu);

        return "menu/detail"; // "menu/detail" 뷰를 반환
    }

    @GetMapping("/list")  // GET 메소드로 요청을 처리하는 메소드 , 페이징 처리 @PageableDefault Pageable pageable
    public String findMenuList(Model model, @PageableDefault Pageable pageable) { // Model 객체를 받아들이는 findMenuList 메소드를 정의합니다.
        /* 페이징 처리 이전  */
//        // 메뉴 목록을 조회.
//        List<MenuDTO> menuList = menuService.findMenuList();
//        // 모델에 조회된 메뉴 목록을 추가
//        model.addAttribute("menuList", menuList);

        /*  페이징 처리 이후 */
        log.info("pageable: {}", pageable);

        Page<MenuDTO> menuList = menuService.findMenuList(pageable);
        log.info("{}", menuList.getContent());
        log.info("{}", menuList.getTotalPages());
        log.info("{}", menuList.getTotalElements());
        log.info("{}", menuList.getSize());
        log.info("{}", menuList.getNumberOfElements());
        log.info("{}", menuList.isFirst());
        log.info("{}", menuList.isLast());
        log.info("{}", menuList.getSort());
        log.info("{}", menuList.getNumber());

        PagingButton paging = Pagenation.getPagingButtonInfo(menuList);

        model.addAttribute("menuList", menuList);
        model.addAttribute("paging", paging);


        return "menu/list"; // "menu/list" 뷰를 반환
    }

    @GetMapping("/querymethod")
    public void querymethodPage() {}

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model) {

        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);

        model.addAttribute("menuList", menuList);



        return "menu/searchResult";
    }

    @GetMapping("/regist")
    public void registPage() {}

    @GetMapping("/category")
    @ResponseBody
    public List<CategoryDTO> findCategoryList() {
        return menuService.findAllCategory();
    }

    @PostMapping("/regist")
    public String registNewMenu(@ModelAttribute MenuDTO menuDTO){

        menuService.registMenu(menuDTO);


        return "redirect:/menu/list";
    }

    @GetMapping("/modify")
    public void modifyPage() {}

    @PostMapping("/modify")
    public String modifyMenu(@ModelAttribute MenuDTO menuDTO) {
        menuService.modifyMenu(menuDTO);
        return "redirect:/menu/" + menuDTO.getMenuCode();
    }
    @GetMapping("/delete")
    public void deletePage() {}

    @PostMapping("/delete")
    public String deleteMenu(@RequestParam Integer menuCode) {
        menuService.deleteMenu(menuCode);
        return "redirect:/menu/list";

    }

}
