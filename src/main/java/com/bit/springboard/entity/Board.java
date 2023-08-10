package com.bit.springboard.entity;

import com.bit.springboard.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Entity: 엔티티 클래스로 지정
@Entity
//@Table: 테이블 이름 등을 지정
@Table(name = "T_BOARD")
//@SequenceGenerator: 시퀀스 생성 어노테이션
//MySQL에는 시퀀스 문법이 존재하지 않기 때문에 시퀀스 테이블이 생성된다.
//name: 시퀀스 제네레이터 이름 지정
//sequenceName: 시퀀스 이름 지정
//initailValue: 시퀀스의 시작값 설정
//allocationSize: 시퀀스의 증감값 설정
@SequenceGenerator(
        name = "BoardSeqGenerator",
        sequenceName = "T_BOARD_SEQ",
        allocationSize = 1
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    //컬럼 정의
    //@Id: PK로 지정
    @Id
    //키 값 지정 방식
    //@GeneratedValue: 생성된 값으로 키값을 지정하는 방식
    //GenerationType 속성
    //AUTO: 기본값, 데이터베이스가 자동으로 키값 할당
    //IDENTITY: AUTO_INCREMENT를 사용하여 키값 할당
    //SEQUENCE: 시퀀스를 사용하여 키값 할당, SequenceGenerator와 함께 사용한다.
    //TABLE: 키값으로 사용될 값을 별도의 테이블로 생성하여 관리한다.
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "BoardSeqGenerator"
    )
    private int boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    //기본값 설정
    private LocalDateTime boardRegdate = LocalDateTime.now();
    //@Column: 컬럼에 대한 특성 지정
    //name: 어느 컬럼과 매핑될지 지정
    //nullable: null값 허용 여부
    //unique: UK 지정 여부
    @Column(name = "BOARD_CNT", nullable = false)
    private int boardCnt = 0;

    //@Transient: 테이블의 컬럼으로 생성되지 않고 객체에서만 사용할 수 있는 멤버변수
    @Transient
    private String searchCondition;
    @Transient
    private String searchKeyword;

    public BoardDTO EntityToDTO() {
        return BoardDTO.builder()
                .boardNo(this.boardNo)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardWriter(this.boardWriter)
                .boardRegdate(this.boardRegdate.toString())
                .boardCnt(this.boardCnt)
                .build();
    }
}