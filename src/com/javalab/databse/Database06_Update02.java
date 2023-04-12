package com.javalab.databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 상품가격 변경(수정)
 * - 전자제품 카테고리에 소속된 상품들만 가격을 10% (가격 * 1.10) 인상
 */
public class Database06_Update02 {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbId = "tempdb";
		String dbPwd = "1234";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql;
		
		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공");
			
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("커넥션 객체 생성 성공");
			
			double rate = 1.10;	//상승률(%)
			String category_name = "전자제품";
			
//			int caId = 1;
			
//			sql = "update product set price = price*1.10 where category_id = ?";
			sql = "update product p";
			sql += " set p.price = p.price * ? ";
			sql += " where p.category_id = ";
			sql += " (select c.category_id ";
			sql += " from category c";
			sql += " where c.category_name = ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setDouble(1, rate);
			pstmt.setString(2,category_name);
			
//			pstmt.setInt(1,caId);
			
			int result = pstmt.executeUpdate();
			if(result > 0) {
				System.out.println("수정성공");
			}else {
				System.out.println("수정실패");
			}
			
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버로드 ERR "+e.getMessage());
		}catch(SQLException e) {
			System.out.println("SQL ERR : "+e.getMessage());
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(SQLException e) {
				System.out.println("SQL ERR : "+e.getMessage());
			}
		}
		
	}

}
