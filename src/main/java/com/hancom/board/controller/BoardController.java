package com.hancom.board.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.hancom.board.dto.BoardInputDTO;
import com.hancom.board.dto.BoardOutputDTO;
import com.hancom.board.model.Board;
import com.hancom.board.service.BoardService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/hancom")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	//목록
	//게시판에 게시물이 한 개도 없더라도 원하는 정보를 찾지 못하는 오류 상황은 아니다. 따라서 NPE 예외 처리를 작성하지 않았고 빈 리스트를 리턴해준다.
	@ApiOperation(value = "목록", notes = "게시판의 전체 목록을 조회한다. 해당 API는 게시물 번호, 제목, 작성자, 최초 작성일의 정보만 제공한다.")
	@GetMapping("/board")
	public List<BoardOutputDTO> get(){
		
		List<Board> boardList = boardService.get();
		List<BoardOutputDTO> boardOutputDTOList = new ArrayList<BoardOutputDTO>();
		
		//데이터베이스에 저장되어있는 모든 레코드를 boardList에 가져온 뒤 그 리스트의 각 요소들의 본문내용과 최신 수정일 필드를 제외하고 새로운 리스트를 만들어 반환해준다. 
		boardList.forEach(e -> {
			boardOutputDTOList.add(BoardOutputDTO.builder()
					.number(e.getNumber())
					.title(e.getTitle())
					.author(e.getAuthor())
					.createdDate(e.getCreatedDate())
					.build());
		});
			
		return boardOutputDTOList;
	}
	
	//상세보기
	@ApiOperation(value = "상세보기", notes = "게시판의 특정 게시물을 조회한다. 해당 API는 게시물의 모든 정보를 제공한다.")
	@GetMapping("/board/{number}")
	public Board get(@PathVariable int number) {
		
		Board board = boardService.get(number);
		
		//원하는 번호의 게시물이 없을 경우에는 RuntimeException을 발생시킨다.
		if(board == null) {
			throw new RuntimeException(number + "번 게시물을 찾지 못했습니다.");
		}
		
		return board;
	}
	
	//입력
	@ApiOperation(value = "입력", notes = "게시판에 새로운 게시물을 만든다. 해당 API는 유저가 게시물의 제목, 작성자, 본문 내용을 작성하고 서버에 전송해 줄 수 있다.")
	@PostMapping("/board")
	public Board save(@RequestBody BoardInputDTO boardDTO) {
		
		//필수 작성 항목인 제목이나 작성자가 기입되어있지 않을 경우 RuntimeException을 발생시킨다.
		if(boardDTO.getTitle() == null || boardDTO.getAuthor() == null) {
			throw new RuntimeException("필수 작성 항목을 채우지않아 게시물을 저장할 수 없습니다.");
		}
		
		//해당 포맷에 맞춰 최초 작성일을 저장한다.
		SimpleDateFormat createdSimpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//BoardDTO에서 Board 모델로 변경하는 작업
		Board board = Board.builder()
				.title(boardDTO.getTitle())
				.author(boardDTO.getAuthor())
				.content(boardDTO.getContent())
				.createdDate(createdSimpleDate.format(new Date()).toString())
				.build();
		boardService.save(board);
		
		return board;
	}
	
	//수정
	//수정 시에는 RequestBody에 Board 모델 클래스를 받아 수정하고자하는 게시물의 번호(기본키)를 알아내야 한다.
	//그러나 이러한 경우에는 유저가 임의로 변경하면 안되는 정보(번호, 최초 작성일, 최근 수정일)를 변경하여 보낼 수도 있다.
	//따라서 이러한 문제를 해결하기 위해 해당 메소드에서 먼저 게시물의 번호가 동일한 객체를 가져온 뒤 변경할 정보들만 변경해주는 작업을 한 뒤 데이터베이스에 저장해주는 방식으로 구현하였다.
	//(프론트엔드 단에서 미리 방지해 줄 수도 있겠지만 백엔드는 프론트엔드에서 어떤 정보가 올지 모르기에 모든 상황을 고려해서 로직을 작성해야한다고 배웠기에 아래와 같이 로직을 구성하였다.)
	@ApiOperation(value = "수정", notes = "게시판에 존재하던 게시물의 내용을 수정한다. 해당 API는 유저가 보내지 말아야할 정보(게시물 번호, 최초 작성일, 최근 수정일)를 보내더라도 "
			+ "백엔드 단에서 수정 가능한 정보만 변경하여 데이터베이스에 저장한다.")
	@PutMapping("/board")
	public Board update(@RequestBody Board board) {
		
		//수정을 원할 때 필수 작성 항목인 제목이나 작성자가 기입되어있지 않을 경우 RuntimeException을 발생시킨다.
		if(board.getTitle() == null || board.getAuthor() == null) {
			throw new RuntimeException("필수 작성 항목을 채우지않아 게시물을 수정할 수 없습니다.");
		}
		
		Board originBoard = boardService.get(board.getNumber());
		
		//수정하고자 하는 번호의 게시물이 없을 경우에는 RuntimeException을 발생시킨다.
		if(originBoard == null) {
			throw new RuntimeException(board.getNumber() + "번 게시물을 찾지 못했습니다.");
		}
		
		//해당 포맷에 맞춰 최근 수정일을 저장한다.
		SimpleDateFormat lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		//RequestBody로부터 넘어온 Board 객체의 필드 정보들 중 변경이 허용된 필드들만 변경해준다.
		originBoard.setTitle(board.getTitle());
		originBoard.setAuthor(board.getAuthor());
		originBoard.setContent(board.getContent());
		originBoard.setLastModifiedDate(lastModifiedDate.format(new Date()).toString());
		boardService.save(originBoard);
		
		return originBoard;
	}
	
	//삭제
	@ApiOperation(value = "삭제", notes = "게시판의 특정 게시물을 삭제한다. 해당 API는 특정 게시물 삭제가 잘 되었는지 결과를 리턴해준다.")
	@DeleteMapping("/board/{number}")
	public String delete(@PathVariable int number) {
		
		//삭제하고자 하는 번호의 게시물이 없을 경우에는 RuntimeException을 발생시킨다.
		if(boardService.get(number) == null) {
			throw new RuntimeException(number + "번 게시물을 삭제할 수 없습니다.");
		}
		
		boardService.delete(number);
		
		return number + "번 게시물이 삭제되었습니다.";
	}
	
}
