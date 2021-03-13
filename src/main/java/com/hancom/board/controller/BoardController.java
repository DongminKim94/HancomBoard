package com.hancom.board.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hancom.board.DTO.BoardInputDTO;
import com.hancom.board.DTO.BoardOutputDTO;
import com.hancom.board.model.Board;
import com.hancom.board.service.BoardService;

@RestController
@RequestMapping("/hancom")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	//목록
	@GetMapping("/board")
	public List<BoardOutputDTO> get(){
		
		List<Board> boardList = boardService.get();
		List<BoardOutputDTO> boardOutputDTOList;
		boardList.forEach(e -> {
			
		});
		
		return boardService.get();
	}
	
	//상세보기
	@GetMapping("/board/{number}")
	public Board get(@PathVariable int number) {
		
		Board board = boardService.get(number);
		if(board == null) {
			throw new RuntimeException(number + "번 게시물을 찾지 못했습니다.");
		}
		
		return board;
	}
	
	//입력
	@PostMapping("/board")
	public Board save(@RequestBody BoardInputDTO boardDTO) {
		
		//해당 포맷에 맞춰 최초 작성일을 저장한다.
		SimpleDateFormat createdSimpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//BoardDTO에서 Board 모델로 변경하는 작업 
		Board board = Board.builder().title(boardDTO.getTitle()).author(boardDTO.getAuthor()).content(boardDTO.getContet()).
				createdDate(createdSimpleDate.format(new Date()).toString()).build();
		boardService.save(board);
		
		return board;
	}
	
	//수정
	@PutMapping("/board")
	public Board update(@RequestBody Board board) {
		
		boardService.save(board);
		
		return board;
	}
	
	//삭제
	@DeleteMapping("/board/{number}")
	public String delete(@PathVariable int number) {
		
		boardService.delete(number);
		
		return number + "번 게시물이 삭제되었습니다.";
	}
	
}
