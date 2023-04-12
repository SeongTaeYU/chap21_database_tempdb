package com.javalab.databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//3.가격이 25,000원 이상인 상품들의 이름과 가격을 조회

public class Database02_Select02 {

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
			System.out.println("드라이버로드 성공");
			
			con = DriverManager.getConnection(url,dbId,dbPwd);
			System.out.println("커넥션 객체생성 성공");
			
			//3. 조회조건
			int wherePrice = 25000;
			
			
			//4.생성한 PreparedStatement 객체를 통해서 쿼리하기 위한 SQL 쿼리 문장 만들기(삽입,수정,삭제,조회)
			sql = "select c.category_id, c.category_name, p.product_id, p.product_name, p.price, to_char(p.receipt_date,'yyyy-mm-dd')as receipt_date";
			sql += " from category c left outer join product p on c.category_id = p.category_id";
			sql += " where p.price >= ?";
			sql += " order by c.category_id, p.category_id";
			
			//5. 커넥션 객체를 통해서 데이터베이스에 쿼리(SQL)를 실행해주는 PreparedStatemenet 객체 얻음
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,wherePrice);	//쿼리문에 인자 전달(setInt)
			System.out.println("객체생성 성공");
			
			rs = pstmt.executeQuery();
			System.out.println();
			
			while(rs.next()) {
				System.out.println(rs.getInt("category_id")+"\t"+
								   rs.getString("category_name")+"\t"+
								   rs.getInt("product_id")+"\t"+
								   rs.getString("product_name")+"\t"+
								   rs.getInt("price")+"\t"+
								   rs.getDate("receipt_date")
						);	
			}
			
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버로드 ERR : "+e.getMessage());
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
