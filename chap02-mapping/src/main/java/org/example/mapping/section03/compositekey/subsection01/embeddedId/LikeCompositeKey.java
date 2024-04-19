package org.example.mapping.section03.compositekey.subsection01.embeddedId;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable  // 복합키 클래스는 무조건 직렬화 처리 해야한다.
public class LikeCompositeKey implements Serializable  {

    @Column(name = "liked_member_no")
    private int likedMemberNo;

    @Column(name = "liked_book_no")
    private int likedBookNo;

    protected LikeCompositeKey() {}

    public LikeCompositeKey(int likedMemberNo, int likedBookNo) {
        this.likedMemberNo = likedMemberNo;
        this.likedBookNo = likedBookNo;
    }

    @Override
    public String toString() {
        return "LikeCompositeKey{" +
                "likedMemberNo=" + likedMemberNo +
                ", likedBookNo=" + likedBookNo +
                '}';
    }
}
