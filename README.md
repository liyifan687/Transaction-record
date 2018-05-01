# Transaction-record
基于 hadoop，hbase的电商交易记录的简单分析
 
将数据.csv文件导入到mysql下，再使用sqoop将mysql中数据导入到hbase中。

主要就是将七百万条数据全部导入hbase，设计好rowkey，web端较简单，只是为了实现根据rowkey进行查询，删除，插入操作，并不复杂。

数据较大，原始数据没有备份，存在了mysql中暂未上传。

