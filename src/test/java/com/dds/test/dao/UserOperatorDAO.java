package com.dds.test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Configurable(autowire=Autowire.BY_NAME)
public class UserOperatorDAO {
	@Autowired
	private JdbcTemplate ddsJdbcTemplate=null;
	 
	public void insertText(final String text) {
		final String sql = "insert into testTable (text) values(?)";
		KeyHolder kh = new GeneratedKeyHolder();
		ddsJdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, text);
				return ps;
			}
		}, kh);
		Connection con = DataSourceUtils.getConnection(ddsJdbcTemplate.getDataSource());
		System.out.println("================="+con.hashCode());
	}
	public List<String> queryAllTexts(){
		String sql = "select text from testTable ";
		final List<String> list = new ArrayList<String>();
		ddsJdbcTemplate.query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				String text=rs.getString("text");
				list.add(text);
			}
		});
		Connection con = DataSourceUtils.getConnection(ddsJdbcTemplate.getDataSource());
		System.out.println("================="+con.hashCode());
		return list;
	}
}
