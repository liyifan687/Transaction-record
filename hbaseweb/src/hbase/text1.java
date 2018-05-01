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
 * ���ַ���ת��Ϊ�ֽڵķ����С�
 * ��������Bytes.toBytesΪhadoopʹ�õķ�������String.toBytes��javaʹ�õķ���
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
			//put����ʵ��������Ϊrowkey
			Put put = new Put(Bytes.toBytes("201301001"));
			//add���������е����
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
     * ���� rowkeyɾ��һ����¼ 
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
            System.out.println("ɾ���гɹ�!");  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
  
    }  */
	@SuppressWarnings("deprecation")
	public static void deleteData() throws Exception{
		table = new HTable(getConfig(),TableName.valueOf("students"));
		//����ɾ����rowkey
		String s = "201301002";
		Delete delete = new Delete(s.getBytes());
		//����ɾ�����������
		delete.deleteColumn(Bytes.toBytes("basic_info"),Bytes.toBytes("name"));
		
		table.delete(delete);
	}
	@SuppressWarnings("deprecation")
	public static void createTable(){
		HBaseAdmin admin = null;
		try {
			admin = new HBaseAdmin(getConfig());
			//1\ȷ���Ƿ���ڸñ�
			  if(admin.tableExists("newtable")) {
			    //��Ϊ������,Ȼ��ɾ��
			    admin.disableTable("newtable");
			    admin.deleteTable("newtable");
			  }
			  //�����������
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
	//�����������������,��Ҫʹ��list����
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
	//�ӱ��н���scan��ȡ������
	@SuppressWarnings("deprecation")
	public static void ScanData() throws Exception {
		Scan scan = new Scan(); //����ɨ�����
		table = new HTable(getConfig(),TableName.valueOf("shop"));
		ResultScanner RS = table.getScanner(scan); //ResultScanner�д�ŵ������е������������м��洢����Ϣ�����Ա�����Ҳ����һ�������м���
		for(Result rs : RS){						//Ȼ������Result�У��ٷŽ�list�����С� ���Ա����������м�Ϊ����һ������Ϣ��
			List<Cell> cs = rs.listCells();
			Iterator<Cell> it = cs.iterator();		//��Result������֮����Ҫ����listCells������
			while(it.hasNext()){                    //Cell�д�ŵ���һ���м����е���Ϣ��
				Cell cell = (Cell) it.next();
				String rowkey = Bytes.toString(CellUtil.cloneRow(cell));
				String fname = Bytes.toString(CellUtil.cloneFamily(cell));
				String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
				String value = Bytes.toString(CellUtil.cloneValue(cell));
				System.out.println("rowkey=="+rowkey+"fname"+fname+"---qualifier=>"+qualifier+"---value=="+value);
			}
			//���������ʹ�õ�������������������ϵ����ݡ�
			//������ʹ�ø߼�forѭ�������������
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
	//�ӱ��н���get  rowkey����
	@SuppressWarnings("deprecation")
	public static void getData() throws Exception{
		table = new HTable(getConfig(),TableName.valueOf("students"));
		Get get = new Get(Bytes.toBytes("201301001"));
		//ȫ��ɨ��ʹ�õ���
//		Scan scan = new Scan();
//		ResultScanner RS = table.getScanner(scan);
		//��ĳ��ĳ����get����ʹ�õ���
//		Get get = new Get(Bytes.toBytes("201301001"));
//		Result rt = table.get(get);
		
		Result rt = table.get(get);
		//��hbase��listCells������ʾ��ȡ����м���������Ϣ������ֵΪlist����
		List<Cell> cs = rt.listCells();
		for(Cell cell : cs){
			String fname  = Bytes.toString(CellUtil.cloneFamily(cell));
			String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
			String value = Bytes.toString(CellUtil.cloneValue(cell));
			System.out.println("fname--"+fname+"--qualifier=��"+qualifier+"--value=��"+value);
		}
	}
	//�ӱ��н���get �м��Ĳ�����ָ���к���
	@SuppressWarnings("deprecation")
	public static void get_row_qualifier() throws Exception{
		table = new HTable(getConfig(),TableName.valueOf("shop"));
		Get get = new Get(Bytes.toBytes("2013"));
		//���Ҫ��ȡ�����е�ָ��������ֵʱ�����������
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
			System.out.println("����������������������������");
		}
	}
	
	public static void main(String[] args) throws Exception {
		ScanData();
	}
}
