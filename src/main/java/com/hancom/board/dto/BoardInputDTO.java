package com.hancom.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//유저로부터 입력을 받을 경우 게시물 번호, 최초 작성일, 최근 수정일등의 컬럼은 임의로 수정하면 안된다고 설계하였다.
//따라서 해당 필드들을 유저로부터 입력받지 않기 위하여 중간에 데이터 변환을 위한 DTO 클래스를 만들었다.
public class BoardInputDTO {
	
	private String title;
	
	private String author;
	
	private String contet;
}
