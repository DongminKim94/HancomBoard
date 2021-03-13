package com.hancom.board.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="board")
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="number", nullable=false, updatable=false)
	private Integer number; //번호
	
	@Column(name="title", nullable=false)
	private String title; //제목
	
	@Column(name="author", nullable=false)
	private String author; //작성자
	
	@Column(name="content")
	private String content; //본문내용
	
	@Column(name="created_date", nullable=false)
	private Date createdDate; //작성일
	
}
