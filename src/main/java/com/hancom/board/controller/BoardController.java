package com.hancom.board.controller;

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

import com.hancom.board.model.Board;
import com.hancom.board.service.BoardService;

@RestController
@RequestMapping("/hancom")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	//목록
	@GetMapping("/board")
	public List<Board> get(){
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
	public Board save(@RequestBody Board board) {
		boardService.save(board);
		return board;
	}
	
	//수정
	@PutMapping("/board")
	public Board update(@RequestBody Board board) {
		boardService.save(board);
		return board;
	}
	
	@DeleteMapping("/board/{number}")
	public String delete(@PathVariable int number) {
		boardService.delete(number);
		return number + "번 게시물이 삭제되었습니다.";
	}
	
}
