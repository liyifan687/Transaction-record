package servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import hbase.lianjie;
public class FindServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("gb2312");				//设置请求编码
		String tableName=request.getParameter("tableName");
		String rowkey=request.getParameter("rowKey");		//获取提交的rowkey
         PrintWriter out=response.getWriter();
		lianjie.init();
    	Table table = lianjie.connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowkey.getBytes());
        Result result = table.get(get);
       if(result==null)
       {
    	   out.println("<p>fail"+"</p> ");
       }
       String[] s=rowkey.split("_");
   	   out.println("user_id="+s[0]+"\t");
   	   out.println("merchant_id="+s[1]+"\t");
        Cell[] cells = result.rawCells();
        for(Cell cell:cells){
            out.println("<p>owName:"+new String(CellUtil.cloneRow(cell))+"</p> ");
            out.println("<p>Timetamp:"+cell.getTimestamp()+"</p> ");
            out.println("<p>column Family:"+new String(CellUtil.cloneFamily(cell))+"</p> ");
            out.println("<p>row Name:"+new String(CellUtil.cloneQualifier(cell))+" </p>");
            out.println("<p>value:"+new String(CellUtil.cloneValue(cell))+"</p> ");
        }
        lianjie.showCell(result);
        table.close();
        lianjie.close();	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}
}

