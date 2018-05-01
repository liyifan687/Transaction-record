package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import java.io.IOException;
 
public class text{
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;
 
    //�������е���������ִ�У�ֻ��ɾ����ǰ��//���ɣ��磺ִ��insertRowʱ�뽫�������ע��
    public static void main(String[] args)throws IOException{
        //����һ��������ΪScore������Ϊsname,course
        //createTable("Score",new String[]{"sname","course"});
 
        //��Score���в���һ�����ݣ����м�Ϊ95001,snameΪMary����Ϊsname������û���������Ե��ĸ�����Ϊ�գ�
        //�ȼ����put 'Score','95001','sname','Mary'
        //insertRow("Score", "95001", "sname", "", "Mary");
        //��Score���в���һ�����ݣ����м�Ϊ95001,course:MathΪ88��courseΪ���壬MathΪcourse�µ����У�
        //�ȼ����put 'Score','95001','score:Math','88'
        //insertRow("Score", "95001", "course", "Math", "88");
        //��Score���в���һ�����ݣ����м�Ϊ95001,course:EnglishΪ85��courseΪ���壬EnglishΪcourse�µ����У�
        //�ȼ����put 'Score','95001','score:English','85'
        //insertRow("Score", "95001", "course", "English", "85");
 
        //1��ɾ��Score����ָ�������ݣ����м�Ϊ95001,����Ϊcourse����ΪMath
        //ִ��������ǰ��deleteRow�����Ķ����У���ɾ��ָ�������ݵĴ���ȡ��ע��ע�ͣ���ɾ���ƶ�����Ĵ���ע��
        //�ȼ����delete 'Score','95001','score:Math'
        //deleteRow("Score", "95001", "course", "Math");
 
        //2��ɾ��Score����ָ���������ݣ����м�Ϊ95001,����Ϊcourse��95001��Math��English��ֵ���ᱻɾ����
        //ִ��������ǰ��deleteRow�����Ķ����У���ɾ��ָ�������ݵĴ���ע�ͣ���ɾ���ƶ�����Ĵ���ȡ��ע��
        //�ȼ����delete 'Score','95001','score'
        //deleteRow("Score", "95001", "course", "");
 
        //3��ɾ��Score����ָ�������ݣ����м�Ϊ95001
        //ִ��������ǰ��deleteRow�����Ķ����У���ɾ��ָ�������ݵĴ���ע�ͣ��Լ���ɾ���ƶ�����Ĵ���ע��
        //�ȼ����deleteall 'Score','95001'
        //deleteRow("Score", "95001", "", "");
 
        //��ѯScore���У��м�Ϊ95001������Ϊcourse����ΪMath��ֵ
        getData("jilu", "99959_141", "cf1", "gender");
        getAllData("jilu","99959_141");
        //��ѯScore���У��м�Ϊ95001������Ϊsname��ֵ����Ϊsname������û���������Ե��ĸ�����Ϊ�գ�
        //getData("Score", "95001", "sname", "");
 
        //ɾ��Score��
        //deleteTable("Score");
    }
 
    //��������
    public static void init(){
        configuration  = HBaseConfiguration.create();
        configuration.set("hbase.rootdir","hdfs://Master:8020/hbase");
        configuration.set("hbase.zookeeper.quorum", "192.168.49.131");  
        
        configuration.set("hbase.zookeeper.property.clientPort", "2181");  
        try{
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //�ر�����
    public static void close(){
        try{
            if(admin != null){
                admin.close();
            }
            if(null != connection){
                connection.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
 
    /**
     * ����HBase�ı��л���һ��ϵͳĬ�ϵ�������Ϊ�����������������д�����Ĭ��Ϊput��������б������һ�����ݣ���˴˴����贴��id��
     * @param myTableName ����
     * @param colFamily ������
     * @throws IOException
     */
    public static void createTable(String myTableName,String[] colFamily) throws IOException {
 
        init();
        TableName tableName = TableName.valueOf(myTableName);
 
        if(admin.tableExists(tableName)){
            System.out.println("talbe is exists!");
        }else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for(String str:colFamily){
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(str);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
            System.out.println("create table success");
        }
        close();
    }
    /**
     * ɾ��ָ����
     * @param tableName ����
     * @throws IOException
     */
    public static void deleteTable(String tableName) throws IOException {
        init();
        TableName tn = TableName.valueOf(tableName);
        if (admin.tableExists(tn)) {
            admin.disableTable(tn);
            admin.deleteTable(tn);
        }
        close();
    }
 
    /**
     * �鿴���б�
     * @throws IOException
     */
    public static void listTables() throws IOException {
        init();
        HTableDescriptor hTableDescriptors[] = admin.listTables();
        for(HTableDescriptor hTableDescriptor :hTableDescriptors){
            System.out.println(hTableDescriptor.getNameAsString());
        }
        close();
    }
    /**
     * ��ĳһ�е�ĳһ�в�������
     * @param tableName ����
     * @param rowKey �м�
     * @param colFamily ������
     * @param col �����������������û�����У��˲�����Ϊ�գ�
     * @param val ֵ
     * @throws IOException
     */
    public static void insertRow(String tableName,String rowKey,String colFamily,String col,String val) throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowKey.getBytes());
        put.addColumn(colFamily.getBytes(), col.getBytes(), val.getBytes());
        table.put(put);
        table.close();
        close();
    }
 
    /**
     * ɾ������
     * @param tableName ����
     * @param rowKey �м�
     * @param colFamily ������
     * @param col ����
     * @throws IOException
     */
    public static void deleteRow(String tableName,String rowKey,String colFamily,String col) throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowKey.getBytes());
        //ɾ��ָ���������������
        //delete.addFamily(colFamily.getBytes());
        //ɾ��ָ���е�����
        //delete.addColumn(colFamily.getBytes(), col.getBytes());
 
        table.delete(delete);
        table.close();
        close();
    }
    public static void getAllData(String tableName,String rowKey)throws  IOException{
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());  
        Result result = table.get(get);
        showCell(result);
        table.close();
        close();
    }
    /**
     * �����м�rowkey��������
     * @param tableName ����
     * @param rowKey �м�
     * @param colFamily ������
     * @param col ����
     * @throws IOException
     */
    public static void getData(String tableName,String rowKey,String colFamily,String col)throws  IOException{
        init();
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        get.addColumn(colFamily.getBytes(),col.getBytes());
        Result result = table.get(get);
        showCell(result);
        table.close();
        close();
    }
    /**
     * ��ʽ�����
     * @param result
     */
    public static void showCell(Result result){
        Cell[] cells = result.rawCells();
        for(Cell cell:cells){
            System.out.println("RowName:"+new String(CellUtil.cloneRow(cell))+" ");
            System.out.println("Timetamp:"+cell.getTimestamp()+" ");
            System.out.println("column Family:"+new String(CellUtil.cloneFamily(cell))+" ");
            System.out.println("row Name:"+new String(CellUtil.cloneQualifier(cell))+" ");
            System.out.println("value:"+new String(CellUtil.cloneValue(cell))+" ");
        }
    }
}