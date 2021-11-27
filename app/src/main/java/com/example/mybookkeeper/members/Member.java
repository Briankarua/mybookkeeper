package com.example.mybookkeeper.members;

public class Member {
    private String memberID;
    private String memberName;
    boolean isMemberChecked;

    public Member(String memberID, String memberName, String isMemberChecked) {
        this.memberID = memberID;
        this.memberName = memberName;
        //this.isMemberChecked = isMemberChecked;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String accountName) {
        this.memberName = memberName;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public boolean getSubChecked() {
        return isMemberChecked;
    }

    public void setSubChecked(boolean isMemberChecked) {
        this.isMemberChecked = isMemberChecked;
    }
}
