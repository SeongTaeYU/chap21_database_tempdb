package com.javalab.databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 상품 가격변경(수정)
 * 탱크로리 상품의 가격을 500,000으로 수정
 */
public class Database05_Update {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbId = "tempdb";
		String dbPwd = "1234";
		
		Connection con = null;
		//쿼리문에 인자를 전달해서 SQL 구문을 실행해주는 객체
		PreparedStatement pstmt = null;
		ResultSet rs = null;	//select 결과 객체 저장
		
		String sql;	//SQL 문을 저장할 변수 선언
		
		try {
			Class.forName(driver);
			System.out.println("1.드라이버로드 성공!");
			
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("2.커넥션 객체 생성 성공");	
			
			//update Query
			int productId = 12;
			int price = 500000;
			
			sql = "update product set price = ? where product_id = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1,price);
			pstmt.setInt(2,productId);
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("수정 성공");
			}else {
				System.out.println("수정 실패");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 ERR : "+e.getMessage());
		}catch(SQLException e) {
			System.out.println("SQL ERR : "+e.getMessage());
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if( pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(SQLException e) {
				System.out.println("Connection 자원해제 ERR : "+e.getMessage());
			}
		}
	}

}
