package com.ohgiraffers.section03.entity;

import jakarta.persistence.*;
// Menu 클래스를 위한 엔티티 매핑 설정
@Entity(name = "Section03Menu")  /* @Entity 애노테이션은 JPA 에서 엔티티클래스를 나타내는것 , 엔티티의 이름을 지정(Section03Menu), 이 이름은 DB에서 해당 엔티티를 식별하는데 사용.*/
@Table(name = "tbl_menu") /*@Table 애노테이션은 해당 엔티티 클래스가 매핑되는 데이터베이스 테이블을 지정,(tbl_menu)는 DB에 사용되는 테이블, 엔티티 클래스의 객체들은 "tbl_menu" 테이블에 저장된다.*/
public class Menu {

    // 주요 키(primary key)를 나타내는 필드
    @Id
    @Column(name = "menu_code")  // 데이터베이스의 열 이름 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략 설정
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

    protected Menu() {}

    public Menu(String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }


    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
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

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
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
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }


}
