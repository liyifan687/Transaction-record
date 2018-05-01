package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;

import hbase.lianjie;

public class InsertServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("gb2312");				//设置请求编码
		String tableName=request.getParameter("tableName");
		String rowkey=request.getParameter("rowKey");		//获取提交的rowkey
		String colFamily=request.getParameter("colFamily");
		String col=request.getParameter("col");	
		String val=request.getParameter("val");
		
         PrintWriter out=response.getWriter();
		lianjie.init();  
        Table table = lianjie.connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowkey.getBytes());
        put.addColumn(colFamily.getBytes(), col.getBytes(), val.getBytes());
        table.put(put);           
        out.println("<p>success:"+"</p> ");
        lianjie.close();	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}
}
