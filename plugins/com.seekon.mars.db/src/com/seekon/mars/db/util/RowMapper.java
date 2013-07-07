package com.seekon.mars.db.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {

  public Object mapRow(ResultSet rs, int arg1) throws SQLException;
}
