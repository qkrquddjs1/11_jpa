package com.ohgiraffers.section03.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class EntityLifeCycleTests {

    private EntityLifeCycle lifeCycle;

    /* 테스트 실행 전에 실행되는 메서드
    * EntityLifeCycle 객체를 생성하여 lifeCycle 변수에 할당 */
    @BeforeEach
    void setup() {
        this.lifeCycle = new EntityLifeCycle();
    }

    @DisplayName("비영속 테스트")
    @ParameterizedTest /* 매개변수화된 테스를 지원하는 JUnit의 애노테이션, 동일한 메서드를 여러번 실행하고, 각 실행에 대해 다른 매개변수값을 제공*/
    @ValueSource(ints = {1,3}) /*매개변수화된 테스트에서 사용될 매개변수를 지정하는 애노테이션 , 1과 3의 메뉴 코드를 지정. */
    void testTransient(int menuCode) {
        // when // 주어진 메뉴 코드로 메뉴를 찾는다.
        Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);
        // 찾아온 메뉴를 기반으로 새로운 메뉴 객체 생성
        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
        );
        // EntityManager 인스턴스를 얻어온다.
        EntityManager entityManager = lifeCycle.getManagerInstance();

        //then  // 찾아온 메뉴와 새로운 메뉴가 서로 다른 객체인지 확인
        assertNotEquals(foundMenu, newMenu);
        // 찾아온 메뉴가 영속성 컨텍스트에 있는지 확인
        assertTrue(entityManager.contains(foundMenu)); // fountMenu는 영속성 컴텍스트에서 관리 되는 영속 상태의 객체
        // 새로운 메뉴가 영속성 컨텍스트에 있는지 확인
        assertFalse(entityManager.contains(newMenu));// NewMenu는 영속성 컴텍스트에서 관리 되지 않는 비영속 객체
    }

    @DisplayName("다른 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1,3})
    void testManagedOtherEntityManager(int menuCode) {
        // when // 주어진 메뉴 코드로 메뉴를 찾아옴
        Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
        // 주어진 메뉴 코드로 메뉴를 다시 한번 찾아옴
        Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);

        // then // 두메뉴 객체가 서로 다른 객체인지 확인
        assertNotEquals(menu1, menu2);
    }

    @DisplayName("같은 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1,3})
    void testManagedSameEntityManager(int menuCode) {
        // given // EntityManagerGenerator를 사용하여 EntityManager 인스턴스를 가져옴.
        EntityManager manager = EntityMangerGenerator.getInstance();

        // when   // 주어진 메뉴코드로 메뉴를 찾아옴
        Menu menu1 = manager.find(Menu.class, menuCode);
        // 주어진 메뉴 코드로 다시 한번더 찾아옴
        Menu menu2 = manager.find(Menu.class, menuCode);


        // then  // 두 메뉴 객체가 서로 같은 객체인지 확인
        assertEquals(menu1, menu2);
    }

    @DisplayName("준영속화 detach 테스트")
    @ParameterizedTest
    @CsvSource({"11, 1000", "12, 1000"})  // 두개의 매개변수를 받는 CSV 형식으로 값을 제공
    void testDetachEntity(int menuCode, int menuPrice) {
        // given  // EntityManagerGenerator를 사용하여 EntityManager 인스턴스를 가져옴
        EntityManager entityManager = EntityMangerGenerator.getInstance();
        // EntityManager로부터 EntityTransaction을 가져옴
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when  // 트랜잭션을 시작
        entityTransaction.begin();
        // 주어진 메뉴 코드로 메뉴를 찾아옴
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        // detach : 특정 엔티티만 준영속 상태(영속성 컴텍스트가 관리하던 객체를 관리하지 않는 상태)로 만든다.
        // 준영속 상태는 영속성 컨텍스트가 관리하지 않는 상태입니다.
        entityManager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);   // 메뉴의 가격을 변경한다
        // flush : 영속성 컨텍스트의 상태를 DB로 내보낸다. commit하지 않은 상태이므로 rollback 가능하다.
        // 이상태에서는 아직 트랜잭션을 커밋하지 않았으므로 롤백이 가능하다.
        entityManager.flush();

        // then // 변경된 메뉴 가격이 기대한 값과 다른지 확인
        assertNotEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        // 트랜잭션을 롤백 한다.
        entityTransaction.rollback();


    }

    @DisplayName("준영속화 detach 후 다시 영속화 테스트")
    @ParameterizedTest
    @CsvSource({"11, 1000", "12, 1000"})
    void testDetachAndMerge(int menuCode, int menuPrice) {
        // given  // EntityManagerGenerator를 사용하여 EntityManager 인스턴스를 가져옵니다.
        EntityManager entityManager = EntityMangerGenerator.getInstance();
        // EntityManager로부터 EntityTransaction을 가져옵니다.
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when  // 트랜잭션을 시작
        entityTransaction.begin();
        // 주어진 메뉴코드로 메뉴를 찾아온다.
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        // detach 메서드를 사용하여 특정 엔티티를 준영속 상태로 만든다.
        // detach => 준영속 상태 : 영속성 컨텍스트가 관리하지 않는 상태
        entityManager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);  // 메뉴의 가격을 변경 .
        // merge : 파라미터로 넘어온 준영속 엔티티 객체의 식별자 값으로 1차 캐시에서 엔티티 객체를 조회 한다.
        // (없으면 DB에서 조회하여 1차 캐시에 저장한다.)
        // 조회한 영속 엔티티 객체에 준영속 상태의 엔티티 객체의 값을 병합 한 뒤 영속 엔티티 객체를 반환한다.
        // 혹은 조회 할 수 없는 데이터라면 새로 생성해서 병합한다.
        entityManager.merge(foundMenu); // merge 메서드를 호출하여 준영속 상태의 엔티티를 다시 영속 상태로 만든다. merge 메서드는 준영속 엔티티 객체를 병합하여 영속 상태의 엔티티 객체를 반환한다.
        // flush 메서드를 호출하여 영속성 컨텍스트의 상태를 데이터베이스에 내보냅니다.
        /* flush() 메서드는 영속성 컨텍스트의 변경 내용을 DB에 즉시 동기화 하는 역할을한다. 일반적으로 JPA는 트랜잭션이 커밋될때만 데이터베이스에 변경사항을 저장.
        * 그러나 떄때로 개발자가 트랜잭션이 커밋되기 전에 변경 사항을 DB에 즉시 반영 시킬수 있다. 그떄 flush() 메서드를 사용한다.
        * flush()를 호출하면 영속성 컨텍스트의 변경 내용이 DB에 즉시 반영 되지만, 트랜잭션이 커밋되지않아 롤백할수 있음.
        * 따라서 flush()를 호출하더라고 트랜잭션 내에서 커밋되지 않앗으면 DB에 변경사항은 저장안댐. */
        entityManager.flush();

        // then // 변경된 메뉴 가격이 기대한 값과 같은지 확인한다.
        assertEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        // 트랜잭션을 반환
        entityTransaction.rollback();

    }


    @DisplayName("detach후 merge한 데이터 update 테스트")
    @ParameterizedTest
    // 매개변수화된 테스트에서 사용될 매개변수를 지정하는 애노테이션, 여기서는 메뉴 코드와 메뉴 이름을 받는 CSV 형식으로 값을 제공
    // 각 줄은 하나의 테스트 케이스를 나타낸다 가겨 열은 해당 테스트 케이스의 매개변수를 나타낸다.
    // 첫번째 테스트 메뉴 코드가 11이고 메뉴이름이 "하양 민트초코죽" 을 나타내고 두번쨰 테스트 메뉴코드는 12 "까만 딸기탕후루를"나타낸다
    // 이러한 정의된 테스트 케이스는 @ParameterizedTest에서 사용되어 메서드에 전달 된다.
    @CsvSource({"11, 하양 민트초코죽", "12, 까만 딸기탕후루"})
    void testMergeUpdate(int menuCode, String menuName) {
        // given // EntityManagergenerator를 사용하여 EntityManager 인스턴스를 가져옵니다.
        EntityManager entityManager = EntityMangerGenerator.getInstance();
        // 메뉴 코드를 기반으로 메뉴를 검색하여 foundMenu에 할당합니다.
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        // foundMenu을 준영속 상태로 변경합니다.
        entityManager.detach(foundMenu);

        // when //foundMenu의 메뉴 이름을 새로운 메뉴 이름으로 설정합니다.
        foundMenu.setMenuName(menuName);
        // 변경된 foundMenu를 다시 검색하여 refoundMenu에 할당합니다.
        Menu refoundMenu = entityManager.find(Menu.class, menuCode);
        // 변경된 foundMenu를 merge하여 영속성 컨텍스트에 병합합니다.
        entityManager.merge(foundMenu);

        // then  // 변경된 메뉴 이름이 예상한 메뉴 이름과 일치하는지 확인한다.
        assertEquals(menuName, refoundMenu.getMenuName());

    }

    @DisplayName("detach 후 merge한 데이터 save 테스트")
    @Test
    void testMergeSave() {
        // given // EntityManagerGenerator를 사용하여 EntityManager 인스턴스를 가져옵니다.
        EntityManager entityManager = EntityMangerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        // EntityManager로부터 메뉴 코드가 20인 메뉴를 검색하여 foundMenu에 할당합니다.
        Menu foundMenu = entityManager.find(Menu.class, 20);
        // foundMenu을 준영속 상태로 변경합니다.
        entityManager.detach(foundMenu);

        // when // 트랜잭션을 시작합니다.
        entityTransaction.begin();
        foundMenu.setMenuName("치약맛 초코 아이스크림"); // foundMenu의 메뉴 이름을 "치약맛 초코 아이스크림"으로 설정합니다.
        foundMenu.setMenuCode(999);  // foundMenu의 메뉴 코드를 999로 변경합니다.
        entityManager.merge(foundMenu); // 변경된 foundMenu를 merge하여 영속성 컨텍스트에 저장합니다.
        entityTransaction.commit(); // 트랜잭션을 커밋합니다,

        // then // 메뉴코드가 999인 메뉴의 메뉴 이름이 "치약맛 초코 아이스크림"인지 확인합니다.
        assertEquals("치약맛 초코 아이스크림", entityManager.find(Menu.class, 999).getMenuName());

    }


    @DisplayName("준영속화 clear 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 3})
    void testClearPersistenceContext(int menuCode){
        // given
        EntityManager entityManager = EntityMangerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        // when
        // clear : 영속성 컨텍스트를 초기화 한다. -> 영속성 컨텍스트 내의 모든 엔티티를 준영속화 시킨다.
        entityManager.clear();

        // then
        Menu expectdMenu = entityManager.find(Menu.class, menuCode);
        assertEquals(foundMenu, expectdMenu);
    }

    @DisplayName("준영속화 close 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 3})
    void testClosePersistenceContext(int menuCode){
        // given
        EntityManager entityManager = EntityMangerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        // when
        // close : 영속성 컨텍스트를 종료 한다. -> 영속성 컨텍스트 내의 모든 엔티티를 준영속화 시킨다.
        entityManager.close();

        // then
//        Menu expectdMenu = entityManager.find(Menu.class, menuCode);
//        assertEquals(foundMenu, expectdMenu);
        assertThrows(
                IllegalStateException.class,
                () -> entityManager.find(Menu.class, menuCode)
        );
    }


    @DisplayName("영속성 엔티티 삭제 remove 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1})
    void testRemoveEntity(int menuCode){
        // given
        EntityManager entityManager = EntityMangerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        // when
        entityTransaction.begin();
        // remove : 엔티티를 영속성 컨텍스트 및 데이터베이스에서 삭제
        // 단, 트랙잭션을 제어하지 않으면 데이터 베이스에 영구 반영 되지는 않는다.
        // 트랜잭션을 커밋하는 순간 영속성 컴텍스트에서 관리하는 엔티티 객체가 데이터 베이스에 반영 된다.
        entityManager.remove(foundMenu);

        // flush : 영속성 컨텍스트의 변경 내용을 데이터 베이스에서 동기화 하는 작업
        entityManager.flush();

        // then
        Menu refoundMenu = entityManager.find(Menu.class , menuCode);
        assertNull(refoundMenu);
        entityTransaction.rollback();
    }










}
