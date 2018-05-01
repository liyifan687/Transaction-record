package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;

import hbase.lianjie;

public class DeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("gb2312");				//设置请求编码
		String tableName=request.getParameter("tableName");
		String rowkey=request.getParameter("rowKey");		//获取提交的rowkey
		String colFamily=request.getParameter("colFamily");
		String col=request.getParameter("col");	
         PrintWriter out=response.getWriter();
		lianjie.init();
		Table table = lianjie.connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowkey.getBytes());
        //删除指定列族的所有数据
        //delete.addFamily(colFamily.getBytes());
        //删除指定列的数据
        delete.addColumn(colFamily.getBytes(), col.getBytes());
        table.delete(delete);
        table.close();
        out.println("<p>success:"+"</p> ");
        lianjie.close();	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}
}
