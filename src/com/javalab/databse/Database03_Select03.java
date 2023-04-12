package com.javalab.databse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 2.카테고리가 "전자제품"인 상품들의 정보를 출력
// -출력할 컬럼 : 상품ID, 상품명, 가격, 입고일, 카테고리ID, 카테고리명
// - 가격순 정렬
public class Database03_Select03 {

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
			
			
			//조회 조건
			String ProductName = "전자제품";
			
			//생성한 PreparedStatement
			sql = "select p.product_id as \"상품ID\" , p.product_name as \"상품명\", p.price as \"가격\" "
					+ ", to_char(receipt_date,'yyyy-mm-dd')as \"입고일\", c.category_id as \"카테고리ID\", c.category_name as \"카테고리명\"";
			sql += " from product p left outer join category c on p.category_id = c.category_id";
			sql += " where c.category_name = ?";
			sql += " order by p.price";
		
			//커넥션 객체를 통해서 데이터베스에서 쿼리를 실행
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,ProductName);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getInt("상품ID")+"\t"+
								   rs.getString("상품명")+"\t"+
								   rs.getInt("가격")+"\t"+
								   rs.getDate("입고일")+"\t"+
								   rs.getInt("카테고리ID")+"\t"+
								   rs.getString("카테고리명")
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
