package com.hancom.board.service;

import java.util.List;

import com.hancom.board.model.Board;

public interface BoardService {
	List<Board> get();
	
	Board get(int number);
	
	void save(Board board);
	
	void delete(int number);
	
}
