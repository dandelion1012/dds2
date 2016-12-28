package com.dds.test.ddsImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.dds.impl.AbstractDataSourceFactory;
import com.dds.impl.DataSourceInfo;

public class MyDataSourceFactory extends AbstractDataSourceFactory {
	private JdbcTemplate jdbcTemplate = null;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public DataSourceInfo findDataSourceInfo(String dsID) throws Exception {
		String sql = "select dsid,driverclsname,url, username,password from dsinfo where dsid = ?";
		final List<DataSourceInfo> list = new ArrayList<DataSourceInfo>();
		jdbcTemplate.query(sql, new String[]{dsID}, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				DataSourceInfo info = new DataSourceInfo();
				info.setDsID(rs.getString("dsid"));
				info.setDriverClsName(rs.getString("driverclsname"));
				info.setUrl(rs.getString("url"));
				info.setUserName(rs.getString("username"));
				info.setPassword(rs.getString("password"));
				list.add(info);
			}
			
		});
		return list.size() > 0? list.get(0) : null;
	}

}
