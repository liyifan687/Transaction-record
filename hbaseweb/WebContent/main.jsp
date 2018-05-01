<%@ page language="java" pageEncoding="gb2312" import="java.util.*,java.sql.*,hbase.*"%>
<html>
<head>
	<title></title>
</head>
<body bgcolor="#E3E3E3">

	<form action="findServlet" method="post">
		<table border="1">
			<tbody>
			<tr>
			<td>查询</td>
			</tr>
			
			<tr>
			<td>表名</td>
			<td><input type="text" name="tableName"/></td>
			</tr>
			<tr>
			<td>rowkey</td>
			<td><input type="text" name="rowKey"/></td>
			</tr>

			</tbody>
		</table>
		<input type="submit" value="查询"/>
	</form>
	
	<form action="insertServlet" method="post">
		<table border="1">
			<tbody>
			<tr>
			<td>插入</td>
			</tr>
			
			<tr>
			<td>表名</td>
			<td><input type="text" name="tableName"/></td>
			</tr>
			<tr>
			<td>rowkey</td>
			<td><input type="text" name="rowKey"/></td>
			</tr>
			<tr>
			<td>列族</td>
			<td><input type="text" name="colFamily"/></td>
			</tr>
			<tr>
			<td>列</td>
			<td><input type="text" name="col"/></td>
			</tr>
			<tr>
			<td>值</td>
			<td><input type="text" name="val"/></td>
			</tr>
			</tbody>
		</table>
		<input type="submit" value="插入"/>
	</form>
	
	<form action="deleteServlet" method="post">
		<table border="1">
			<tbody>
			<tr>
			<td>删除</td>
			</tr>
			
			<tr>
			<td>表名</td>
			<td><input type="text" name="tableName"/></td>
			</tr>
			<tr>
			<td>rowkey</td>
			<td><input type="text" name="rowKey"/></td>
			</tr>
			<tr>
			<td>列族</td>
			<td><input type="text" name="colFamily"/></td>
			</tr>
			<tr>
			<td>列</td>
			<td><input type="text" name="col"/></td>
			</tr>
			</tbody>
		</table>
		<input type="submit" value="删除"/>
	</form>
	
</body>
</html>
