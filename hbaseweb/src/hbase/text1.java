package hbase;



import java.util.ArrayList;
		
		
import java.util.Iterator;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.*;


/**
 * 将字符串转化为字节的方法中。
 * 本例子中Bytes.toBytes为hadoop使用的方法。而String.toBytes是java使用的方法
 * 
 * 
 *
 */




public class text1 {
	static Configuration config = null;
	private static HTable table = null; 
	@Before
	private static Configuration getConfig(){
		config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "Master,Slave1,Slave2,Slave3");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		return config;
	}
	@SuppressWarnings("deprecation")
	public static void insertData()throws Exception {
			table = new HTable(getConfig(),TableName.valueOf("students"));
			//put进行实例，参数为rowkey
			Put put = new Put(Bytes.toBytes("201301001"));
			//add方法进行列的添加
			put.add(Bytes.toBytes("basic_info"),Bytes.toBytes("name"),Bytes.toBytes("ChenChaofeng"));
			//put.add(Bytes.toBytes("basic_info"),Bytes.toBytes("birthday"),Bytes.toBytes("2015-05-55"));
			table.put(put);
			table.close();
	}
	
	public static void deleteTable()throws Exception{
		@SuppressWarnings("deprecation")
		HBaseAdmin admin = new HBaseAdmin(getConfig());
		admin.disableTable("students");
		admin.deleteTable("students");
		admin.close();
	}
	/* *//** 
     * 根据 rowkey删除一条记录 
     * @param tablename 
     * @param rowkey 
     *//*  
     public static void deleteRow(String tablename, String rowkey)  {  
        try {  
            HTable table = new HTable(configuration, tablename);  
            List list = new ArrayList();  
            Delete d1 = new Delete(rowkey.getBytes());  
            list.add(d1);  
              
            table.delete(list);  
            System.out.println("删除行成功!");  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
  
    }  */
	@SuppressWarnings("deprecation")
	public static void deleteData() throws Exception{
		table = new HTable(getConfig(),TableName.valueOf("students"));
		//创建删除的rowkey
		String s = "201301002";
		Delete delete = new Delete(s.getBytes());
		//创建删除的列组的列
		delete.deleteColumn(Bytes.toBytes("basic_info"),Bytes.toBytes("name"));
		
		table.delete(delete);
	}
	@SuppressWarnings("deprecation")
	public static void createTable(){
		HBaseAdmin admin = null;
		try {
			admin = new HBaseAdmin(getConfig());
			//1\确认是否存在该表
			  if(admin.tableExists("newtable")) {
			    //置为不可用,然后删除
			    admin.disableTable("newtable");
			    admin.deleteTable("newtable");
			  }
			  //创建表的名字
			HTableDescriptor desc = new HTableDescriptor(TableName.valueOf("newtable"));
			HColumnDescriptor family1 = new HColumnDescriptor("f1");
			HColumnDescriptor family2 = new HColumnDescriptor("f2");
			desc.addFamily(family1);
			desc.addFamily(family2);
			admin.createTable(desc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				admin.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
	//向表中批量插入数据,需要使用list集合
	@SuppressWarnings("deprecation")
	public static void insert_list_Data() throws Exception{
		table = new HTable(getConfig(),TableName.valueOf("students"));
		ArrayList<Put> list = new ArrayList<Put>();
		Put put1 = new Put(Bytes.toBytes("201301001"));
		Put put2 = new Put(Bytes.toBytes("201301001"));
		
		put1.add(Bytes.toBytes("basic_info"), Bytes.toBytes("adress"), Bytes.toBytes("China"));
		put2.add(Bytes.toBytes("basic_info"), Bytes.toBytes("name"), Bytes.toBytes("chen"));
		
		list.add(put1);
		list.add(put2);
		table.put(list);
		table.close();
	}
	//从表中进行scan读取操作。
	@SuppressWarnings("deprecation")
	public static void ScanData() throws Exception {
		Scan scan = new Scan(); //创建扫描操作
		table = new HTable(getConfig(),TableName.valueOf("shop"));
		ResultScanner RS = table.getScanner(scan); //ResultScanner中存放的是所有的整个表中以行键存储的信息，所以遍历的也就是一个个的行键。
		for(Result rs : RS){						//然后存放在Result中，再放进list集合中。 所以遍历的是以行键为主的一条条信息。
			List<Cell> cs = rs.listCells();
			Iterator<Cell> it = cs.iterator();		//在Result与容器之间需要借助listCells方法；
			while(it.hasNext()){                    //Cell中存放的是一个行键所有的信息。
				Cell cell = (Cell) it.next();
				String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
				String fname = Bytes.toString(CellUtil.cloneFamily(cell));
				String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
				String value = Bytes.toString(CellUtil.cloneValue(cell));
				System.out.println("rowkey=="+rowkey+"fname"+fname+"---qualifier=>"+qualifier+"---value=="+value);
			}
			//上面的这种使用的是容器进行了输出集合的数据。
			//还可以使用高级for循环进行输出数据
			/*for(Cell cell : cs){
				String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
				String fname = Bytes.toString(CellUtil.cloneFamily(cell));
				String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
				String value = Bytes.toString(CellUtil.cloneValue(cell));
				System.out.println("rowkey=="+rowkey+"fname"+fname+"---qualifier=>"+qualifier+"---value=="+value);
			}*/
		}
		RS.close();
	}
	//从表中进行get  rowkey操作
	@SuppressWarnings("deprecation")
	public static void getData() throws Exception{
		table = new HTable(getConfig(),TableName.valueOf("students"));
		Get get = new Get(Bytes.toBytes("201301001"));
		//全表扫描使用的是
//		Scan scan = new Scan();
//		ResultScanner RS = table.getScanner(scan);
		//从某个某表中get操作使用的是
//		Get get = new Get(Bytes.toBytes("201301001"));
//		Result rt = table.get(get);
		
		Result rt = table.get(get);
		//在hbase中listCells方法表示获取表的行键的所有信息，返回值为list集合
		List<Cell> cs = rt.listCells();
		for(Cell cell : cs){
			String fname  = Bytes.toString(CellUtil.cloneFamily(cell));
			String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
			String value = Bytes.toString(CellUtil.cloneValue(cell));
			System.out.println("fname--"+fname+"--qualifier=》"+qualifier+"--value=》"+value);
		}
	}
	//从表中进行get 行键的操作：指定行和列
	@SuppressWarnings("deprecation")
	public static void get_row_qualifier() throws Exception{
		table = new HTable(getConfig(),TableName.valueOf("shop"));
		Get get = new Get(Bytes.toBytes("2013"));
		//如果要获取列组中的指定的行列值时，这样子添加
		get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"));
		Result rt = table.get(get);
		List<Cell> cs = rt.listCells();
		for(Cell cell : cs){
			//String fname = Bytes.toString(CellUtil.cloneFamily(cell));
			String value = Bytes.toString(CellUtil.cloneValue(cell));
			System.out.println("--value=>"+value);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void lj()throws Exception{
		Scan scan = new Scan();
		table = new HTable(getConfig(),TableName.valueOf("students"));
		ResultScanner RS = table.getScanner(scan);
		for(Result rs : RS){
			List<Cell> list = rs.listCells();
			for(Cell cell : list){
				String fname = Bytes.toString(CellUtil.cloneFamily(cell));
			}
		}
	}
	@SuppressWarnings("deprecation")
	@Test
	public static void lj1()throws Exception{
		table = new HTable(getConfig(),TableName.valueOf("students"));
		Get get = new Get(Bytes.toBytes("201301001"));
		Result rs = table.get(get);
		List<Cell> list = rs.listCells();
		for(Cell cell : list){
			String quarifier = Bytes.toString(CellUtil.cloneQualifier(cell));
		}
	}
	@SuppressWarnings("deprecation")
	public static void lj2(){
		try {
			table = new HTable(getConfig(),TableName.valueOf("students"));
			Get get = new Get(Bytes.toBytes("201301002"));
			get.addColumn(Bytes.toBytes("basic_info"), Bytes.toBytes("adress"));
			Result Rs = table.get(get);
			List<Cell> list = Rs.listCells();
			for(Cell cell : list){
				String address = Bytes.toString(CellUtil.cloneValue(cell));
				System.out.println("address=>"+address);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("列名出错，检查错误列组名或者列名");
		}
	}
	
	public static void main(String[] args) throws Exception {
		ScanData();
	}
}
