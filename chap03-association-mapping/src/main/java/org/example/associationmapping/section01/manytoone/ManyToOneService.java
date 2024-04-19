package org.example.associationmapping.section01.manytoone;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ManyToOneService {

    private ManyToOneRepository manyToOneRepository;
    // 생성자
    public ManyToOneService(ManyToOneRepository manyToOneRepository) {
        this.manyToOneRepository = manyToOneRepository;
    }
    // 메뉴 조회 메서드
    public Menu findMenu(int menuCode) {
        return manyToOneRepository.find(menuCode);
    }
    // JPQL을 이용한 카테고리 이름 조회 메서드
    public String findCategoryNameByJpql(int menuCode) {
        return manyToOneRepository.findCategoryName(menuCode);
    }

    // 메뉴 등록 메서드
    @Transactional
    public void registMenu(MenuDTO menuInfo) {
        //MenuDTo로부터 Menu 객체 셍성
        Menu menu = new Menu(
                menuInfo.getMenuCode(),
                menuInfo.getMenuName(),
                menuInfo.getMenuPrice(),
                new Category(
                        menuInfo.getCategory().getCategoryCode(),
                        menuInfo.getCategory().getCategoryName(),
                        menuInfo.getCategory().getRefCategoryCode()
                ),
                menuInfo.getOrderableStatus()
        );
        // ManyToOneRepository를 통해 메뉴 등록
        manyToOneRepository.regist(menu);
    }

}
