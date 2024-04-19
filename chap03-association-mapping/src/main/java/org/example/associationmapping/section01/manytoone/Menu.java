package org.example.associationmapping.section01.manytoone;

import jakarta.persistence.*;
@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
public class Menu {

    @Id
    private int menuCode;
    private String menuName;
    private int menuPrice;

    /* 영속성 전이
    *  특정 엔터티를 영속화 할 때 연관 된 엔터티도 함께 영속화 한다는 의미이다. */
    /* 기본적으로는 즉시 로딩 되지만 필요에 따라 지연 로딩으로 변경할수도 있다.*/
    @ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) // 지연로딩  fetch = FetchType.LAZY , 다대일 관께를 나타내며 영속성 전이와 지연 로딩을 설정합니다.
    @JoinColumn(name = "categoryCode") // 외래 키를 지정하고 칼럼 이름을 지정합니다.
    private Category category;
    private String orderableStatus;

    protected Menu() {}

    public Menu(int menuCode, String menuName, int menuPrice, Category category, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
