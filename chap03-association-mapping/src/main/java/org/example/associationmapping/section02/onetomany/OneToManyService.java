package org.example.associationmapping.section02.onetomany;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OneToManyService {

    private OneToManyRepository oneToManyRepository;
    // 생성자를 통해 OneToManyRepository를 주입받습니다.
    public OneToManyService(OneToManyRepository oneToManyRepository) {
        this.oneToManyRepository = oneToManyRepository;
    }

    // 트랜잭션 내에서 주어진 카테고리 코드에 해당하는 카테고리를 검색
    // 해당 카테고리에 속한 메뉴 목록을 출력한 후에 카테고리를 반환합니다.
    @Transactional
    public Category findCategory(int categoryCode) {
        Category category = oneToManyRepository.find(categoryCode);  // 카테고리 검색
        System.out.println(" [menuList " + category.getMenuList());  // 카테고리에 속한 메뉴 목록 출력
        return category;  // 카테고리 반환
    }
    // 트랜잭션 내에서 주어진 카테고리 정보를 사용하여 새로운 카테고리를 등록
    // 또한 해당 카테고리에 속한 첫 번쨰 메뉴도 함께 등록
    @Transactional
    public void registCategory(CategoryDTO categoryInfo) {
        // 카테고리 정보를 이용하여 새로운 카테고리 객체를 생성
        Category category = new Category(
                categoryInfo.getCategoryCode(),
                categoryInfo.getCategoryName(),
                categoryInfo.getRefCategoryCode(),
                null
        );
        // 첫번쨰 메뉴 정보를 이용하여 새로운 메뉴 객체를 생성
        Menu menu = new Menu(
                categoryInfo.getMenuList().get(0).getMenuCode(),
                categoryInfo.getMenuList().get(0).getMenuName(),
                categoryInfo.getMenuList().get(0).getMenuPrice(),
                categoryInfo.getMenuList().get(0).getCategoryCode(),
                categoryInfo.getMenuList().get(0).getOrderableStatus()
        );
        // 메뉴 목록에 새로 생성한 메뉴를 추가합니다.
        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        category.setMenuList(menuList);
        // 새로운 카테고리를 저장소에 등록
        oneToManyRepository.regist(category);
    }




}
