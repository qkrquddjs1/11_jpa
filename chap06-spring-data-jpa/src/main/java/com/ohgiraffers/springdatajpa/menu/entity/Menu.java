package com.ohgiraffers.springdatajpa.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // JPA 앤티티 클래스임을 나타낸다
@Table(name = "tbl_menu") // 엔티티가 매핑도니느 DB 테이블이름을 지정
@Getter
/* @Setter 지양 -> 객체를 언제든지 변경할 수 있으면 객체의 안정성 보장 X
 * 값 변경이 필요한 경우에는 해당 기능을 위한 메소드를 별도로 생성
 * */
@NoArgsConstructor(access = AccessLevel.PROTECTED) // -> 기본 생성자 접근 제한으로 무분별한 객체 생성 지양
/* @AllArgsConstructor 지양 */
/* @ToString : 사용 가능하나 연관 관계 매핑 필드는 제거하고 사용 */
public class Menu {

    @Id // 엔티티 기본 키임을 나타낸다
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성되는 기본키 값을 나태는것을 IDENTITY 전략으로 설정
    private int menuCode;
    private String menuName;
    private int menuPrice;
    private int categoryCode;
    private String orderableStatus;

    public void modifyMenuName(String menuName) {
        this.menuName = menuName;
    }
}

