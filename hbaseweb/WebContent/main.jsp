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
			<td>��ѯ</td>
			</tr>
			
			<tr>
			<td>����</td>
			<td><input type="text" name="tableName"/></td>
			</tr>
			<tr>
			<td>rowkey</td>
			<td><input type="text" name="rowKey"/></td>
			</tr>

			</tbody>
		</table>
		<input type="submit" value="��ѯ"/>
	</form>
	
	<form action="insertServlet" method="post">
		<table border="1">
			<tbody>
			<tr>
			<td>����</td>
			</tr>
			
			<tr>
			<td>����</td>
			<td><input type="text" name="tableName"/></td>
			</tr>
			<tr>
			<td>rowkey</td>
			<td><input type="text" name="rowKey"/></td>
			</tr>
			<tr>
			<td>����</td>
			<td><input type="text" name="colFamily"/></td>
			</tr>
			<tr>
			<td>��</td>
			<td><input type="text" name="col"/></td>
			</tr>
			<tr>
			<td>ֵ</td>
			<td><input type="text" name="val"/></td>
			</tr>
			</tbody>
		</table>
		<input type="submit" value="����"/>
	</form>
	
	<form action="deleteServlet" method="post">
		<table border="1">
			<tbody>
			<tr>
			<td>ɾ��</td>
			</tr>
			
			<tr>
			<td>����</td>
			<td><input type="text" name="tableName"/></td>
			</tr>
			<tr>
			<td>rowkey</td>
			<td><input type="text" name="rowKey"/></td>
			</tr>
			<tr>
			<td>����</td>
			<td><input type="text" name="colFamily"/></td>
			</tr>
			<tr>
			<td>��</td>
			<td><input type="text" name="col"/></td>
			</tr>
			</tbody>
		</table>
		<input type="submit" value="ɾ��"/>
	</form>
	
</body>
</html>
