package com.hancom.board.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
//각 게시글의 요소 중 본문내용과 최근 수정일을 제외한 전체 게시글 리스트를 반환해주는 DTO 클래스
public class BoardOutputDTO {
	
	private int number;
	
	private String title;
	
	private String author;
	
	private String createdDate;
	
	public BoardOutputDTO() {
		
	}
	
}
