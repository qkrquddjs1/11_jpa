package org.example.associationmapping.section03.bidirection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BiDirectionService {

    private BiDirectionRepository biDirectionRepository; // BiDirectionRepository의 의존성 주입
    // BiDirectionService의 생성자로 BiDirectionRepository를 주입 받는다.
    public BiDirectionService(BiDirectionRepository biDirectionRepository) {
        this.biDirectionRepository = biDirectionRepository;
    }
    // 주어진 메뉴 코드에 해당하는 메뉴를 조회하는 메서드
    public Menu findMenu(int menuCode) {
        return biDirectionRepository.findMenu(menuCode);
    }
    // 주어진 카테고리 코드에 해당하는 카테고리를 조회하는 메서드
    @Transactional
    public Category findCategory(int categoryCode) {
        // BiDirectionRepository를 통해 카테고리를 조회합니다.
        Category category = biDirectionRepository.findCategory(categoryCode);
        System.out.println(category.getMenuList());
        System.out.println(category.getMenuList().get(0).getCategory());
        return category;
    }

    @Transactional //메뉴를 등록하는 메서드
    public void registMenu(Menu menu) {
        biDirectionRepository.saveMenu(menu);
    }

    @Transactional // 카테고리를 등록하는 메서드
    public void registCategory(Category category) {
        biDirectionRepository.saveCategory(category);
    }

}
