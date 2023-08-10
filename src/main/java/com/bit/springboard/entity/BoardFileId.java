package com.bit.springboard.entity;

import lombok.Data;

import java.io.Serializable;

@Data
//IdClass는 항상 Serializable을 상속 받아야 한다.
public class BoardFileId implements Serializable {
    //IdClass에서 참조할 키 변수명은 원본 엔티티의 키 변수명과 일치해야 한다.
    //자료형은 테이블에 실제로 들어갈 타입을 지정해야 한다.
    private int board;
    private int boardFileNo;
}
