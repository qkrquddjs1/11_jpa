package com.ohgiraffers.section02.crud;

import jakarta.persistence.*;

@Entity(name = "Section02Menu") // 엔티티 이름을 지정하고, 해당이름으로 데이터베이스 테이블에 매핑.
@Table(name = "tbl_menu") // 엔티티가 매핑되는 데이터베이스 테이블의 이름을 지정.
public class Menu {

    @Id  // 엔티티의 기본 키를 나타낸다.
    @Column(name = "menu_code") // 데이터베이스 테이블의 열 이름을 지정.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 엔티티의 기본 키가 자동으로 생성되도록 지정.
    private int menuCode;

    @Column(name = "menu_name") //데이터베이스 테이블의 열 이름을 지정.
    private String menuName;

    @Column(name = "menu_price") //데이터베이스 테이블의 열 이름을 지정.
    private int menuPrice;

    @Column(name = "category_code") //데이터베이스 테이블의 열 이름을 지정.
    private int categoryCode;

    @Column(name = "orderable_status") //데이터베이스 테이블의 열 이름을 지정.
    private String orderableStatus;

    protected Menu() {}


    // 매개변수를 받는 생성자, 엔티티의 필드를 초기화 시킨다.
    public Menu(String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }


    public String getMenuName() {
        return menuName;
    }

    public int getMenuCode() {
        return menuCode;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }


}
