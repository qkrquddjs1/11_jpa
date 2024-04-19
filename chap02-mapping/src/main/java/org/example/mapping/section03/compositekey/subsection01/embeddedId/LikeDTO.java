package org.example.mapping.section03.compositekey.subsection01.embeddedId;

public class LikeDTO {

    private int likedMemberNo;
    private int likeBookNo;


    public LikeDTO(int likedMemberNo, int likeBookNo) {
        this.likedMemberNo = likedMemberNo;
        this.likeBookNo = likeBookNo;
    }


    public int getLikedMemberNo() {
        return likedMemberNo;
    }

    public void setLikedMemberNo(int likedMemberNo) {
        this.likedMemberNo = likedMemberNo;
    }

    public int getLikeBookNo() {
        return likeBookNo;
    }

    public void setLikeBookNo(int likeBookNo) {
        this.likeBookNo = likeBookNo;
    }


    @Override
    public String toString() {
        return "LikeDTO{" +
                "likedMemberNo=" + likedMemberNo +
                ", likeBookNo=" + likeBookNo +
                '}';
    }
}
