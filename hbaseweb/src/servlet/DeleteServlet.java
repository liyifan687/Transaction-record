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
		request.setCharacterEncoding("gb2312");				//�����������
		String tableName=request.getParameter("tableName");
		String rowkey=request.getParameter("rowKey");		//��ȡ�ύ��rowkey
		String colFamily=request.getParameter("colFamily");
		String col=request.getParameter("col");	
         PrintWriter out=response.getWriter();
		lianjie.init();
		Table table = lianjie.connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowkey.getBytes());
        //ɾ��ָ���������������
        //delete.addFamily(colFamily.getBytes());
        //ɾ��ָ���е�����
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
