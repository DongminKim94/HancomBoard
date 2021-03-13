package com.hancom.board.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hancom.board.model.Board;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Board> get() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Board> query = currentSession.createQuery("from Board", Board.class);
		List<Board> list = query.getResultList();
		
		return list;
	}

	@Override
	public Board get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Board board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}
		
}
