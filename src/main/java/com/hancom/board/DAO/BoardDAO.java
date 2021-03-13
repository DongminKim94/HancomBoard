package com.hancom.board.DAO;

import java.util.List;

import com.hancom.board.model.Board;

public interface BoardDAO {
	
	List<Board> get();
	
	Board get(int id);
	
	void save(Board board);
	
	void delete(int id);
}
