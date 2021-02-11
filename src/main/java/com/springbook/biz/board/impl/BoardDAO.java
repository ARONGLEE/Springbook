package com.springbook.biz.board.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.common.JDBCUtil;

//DAO(Data Access Object)
@Repository
public class BoardDAO extends JdbcDaoSupport {
	/*
	 * //JDBC 관련 변수 private Connection conn = null; private PreparedStatement stmt =
	 * null; private ResultSet rs = null;
	 */
	
	//SQL 명령어들
	private final String BOARD_INSERT = "insert into board(seq, title, writer, content) values( values((select nvl(max(seq),0)+1 from board),?,?,?)";
	private final String BOARD_UPDATE = "update board set title=?, content=?, where seq=?";
	private final String BOARD_DELETE = "delete board where seq=?";
	private final String BOARD_GET = "select * from board where seq=?";
	private final String BOARD_LIST = "select * from board order by seq desc";
	
	@Autowired
	public void setSuperDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	// CRUD 기능의 메소드 구현
	// 글 등록
	public void insertBoard(BoardVO vo) {
		System.out.println("===> JDBC로 insertBoard() 기능 처리");
		getJdbcTemplate().update(BOARD_INSERT, vo.getTitle(), vo.getWirter(), vo.getContent());
		/*
		 * try { conn = JDBCUtil.getConnection(); stmt =
		 * conn.prepareStatement(BOARD_INSERT); stmt.setString(1, vo.getTitle());
		 * stmt.setString(2, vo.getWirter()); stmt.setString(3, vo.getContent());
		 * stmt.executeUpdate(); } catch (SQLException e) { e.printStackTrace(); }
		 * finally { JDBCUtil.close(stmt, conn); }
		 */
	}
	// 글 수정
	public void updateBoard(BoardVO vo) {
		System.out.println("===> JDBC로 updateBoard() 기능 처리");
		getJdbcTemplate().update(BOARD_UPDATE, vo.getTitle(), vo.getContent(), vo.getSeq());
		/*
		 * try { conn = JDBCUtil.getConnection(); stmt =
		 * conn.prepareStatement(BOARD_UPDATE); stmt.setString(1, vo.getTitle());
		 * stmt.setString(2, vo.getContent()); stmt.setInt(3, vo.getSeq());
		 * stmt.executeUpdate(); } catch (SQLException e) { e.printStackTrace(); }
		 * finally { JDBCUtil.close(stmt, conn); }
		 */
	}
	// 글 삭제
	public void deleteBoard(BoardVO vo) {
		System.out.println("===> JDBC로 deleteBoard() 기능 처리");
		getJdbcTemplate().update(BOARD_DELETE, vo.getSeq());
		/*
		 * try { conn = JDBCUtil.getConnection(); stmt =
		 * conn.prepareStatement(BOARD_DELETE); stmt.setInt(1, vo.getSeq());
		 * stmt.executeUpdate(); } catch (SQLException e) { e.printStackTrace(); }
		 * finally { JDBCUtil.close(stmt, conn); }
		 */
	}
	// 글 상세 조회
	public BoardVO getBoard(BoardVO vo) {
		System.out.println("===> JDBC로 getBoard() 기능 처리");
		Object[] args = {vo.getSeq()};
		return getJdbcTemplate().queryForObject(BOARD_GET, args, new BoardRowMapper());
		/*
		 * BoardVO board = null; try { conn = JDBCUtil.getConnection(); stmt =
		 * conn.prepareStatement(BOARD_GET); stmt.setInt(1, vo.getSeq()); rs =
		 * stmt.executeQuery(); if(rs.next()) { board = new BoardVO();
		 * board.setSeq(rs.getInt("SEQ")); board.setTitle(rs.getString("TITLE"));
		 * board.setWirter(rs.getString("WRITER"));
		 * board.setContent(rs.getString("CONTENT"));
		 * board.setRegDate(rs.getDate("REGDATE")); board.setCnt(rs.getInt("CNT")); } }
		 * catch (SQLException e) { e.printStackTrace(); } finally {
		 * JDBCUtil.close(stmt, conn); } return board;
		 */
	}
	// 글 목록 조회
	public List<BoardVO> getBoardList(BoardVO vo){
		System.out.println("===> JDBC로 getBoardList() 기능 처리");
		return getJdbcTemplate().query(BOARD_LIST, new BoardRowMapper());
		/*
		 * List<BoardVO> boardList = new ArrayList<BoardVO>(); try { conn =
		 * JDBCUtil.getConnection(); stmt = conn.prepareStatement(BOARD_LIST); rs =
		 * stmt.executeQuery(); while(rs.next()) { BoardVO board = new BoardVO();
		 * board.setSeq(rs.getInt("SEQ")); board.setTitle(rs.getString("TITLE"));
		 * board.setWirter(rs.getString("WRITER"));
		 * board.setContent(rs.getString("CONTENT"));
		 * board.setRegDate(rs.getDate("REGDATE")); board.setCnt(rs.getInt("CNT"));
		 * boardList.add(board); } } catch (SQLException e) { e.printStackTrace(); }
		 * finally { JDBCUtil.close(rs, stmt, conn); } return boardList;
		 */
	}
}
