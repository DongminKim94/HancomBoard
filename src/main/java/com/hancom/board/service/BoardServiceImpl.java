package com.hancom.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hancom.board.DAO.BoardDAO;
import com.hancom.board.model.Board;

@Service
public class BoardServiceImpl implements BoardService {

	
	@Autowired
	private BoardDAO boardDAO;
	
	@Transactional
	@Override
	public List<Board> get() {
		return boardDAO.get();
	}

	@Transactional
	@Override
	public Board get(int number) {
		return boardDAO.get(number);
	}
	
	@Transactional
	@Override
	public void save(Board board) {
		boardDAO.save(board);
	}

	@Transactional
	@Override
	public void delete(int number) {
		boardDAO.delete(number);
	}

}
