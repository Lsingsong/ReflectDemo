package com.southwind.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 通用工具类
 * @author southwind
 *
 */
public class MyQueryRunner {
	
	/**
	 * 将结果集动态封装成对象
	 * @param conn
	 * @param sql
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Object query(Connection conn,String sql,Class clazz,int id){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object obj = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			obj = clazz.newInstance();
			if(rs.next()){
				//遍历实体类属性集合，依次将结果集中的值赋给属性
				Field[] fields = clazz.getDeclaredFields();
				//获取ResultSet数据
				ResultSetMetaData rsmd = rs.getMetaData();
				for(int i = 0; i < fields.length; i++){
					Object value = setFieldValueByResultSet(fields[i],rsmd,rs);
					//通过属性名找到对应的setter方法
					String name = fields[i].getName();
					name = name.substring(0, 1).toUpperCase() + name.substring(1);
					String MethodName = "set"+name;
					Method methodObj = clazz.getMethod(MethodName,fields[i].getType());
					//调用setter方法完成赋值
					methodObj.invoke(obj, value);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
     * 根据将结果集中的值赋给对应的属性
     * @param field
     * @param rsmd
     * @param rs
     * @return
     */
	public Object setFieldValueByResultSet(Field field,ResultSetMetaData rsmd,ResultSet rs){
    	Object result = null;
    	try {
			int count = rsmd.getColumnCount();
			for(int i=1;i<=count;i++){
				//找到与属性名相同的结果集字段
				if(field.getName().equals(rsmd.getColumnName(i))){
					//获取属性的数据类型
					String type = field.getType().getName();
					switch (type) {
						case "int":
							result = rs.getInt(field.getName());
							break;
						case "java.lang.String":
							result = rs.getString(field.getName());
							break;
						case "double":
							result = rs.getDouble(field.getName());
							break;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
}
