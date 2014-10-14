/**
 * 
 */
package com.ei.itop.common.dao;

import java.util.Date;
import java.util.List;

/**
 * @author Jack.Qi
 * 
 */
public interface ICommonDAO {

	/**
	 * 获取Sequence下一个值
	 * 
	 * @param seqName
	 *            序列名称
	 * @return
	 * @throws Exception
	 * @author liull2
	 * @date 2013-5-21 下午1:42:39
	 */
	public long getSequenceNextValue(String seqName) throws Exception;

	/**
	 * 获取系统时间
	 * 
	 * @return
	 * @throws Exception
	 * @author liull2
	 * @date 2013-5-21 下午1:42:45
	 */
	public Date getSysDate() throws Exception;

	/**
	 * 判断表是否存在
	 * 
	 * @param tableName
	 *            表名
	 * @return
	 * @throws Exception
	 * @author liull2
	 * @date 2013-5-21 下午1:42:51
	 */
	public boolean isTableExist(String tableName) throws Exception;

	/**
	 * 创建sequence
	 * 
	 * @param seqName
	 *            序列名称
	 * @throws Exception
	 * @author liull2
	 * @date 2013-5-21 下午1:42:57
	 */
	public void createSequence(String seqName) throws Exception;

	/**
	 * 修改sequence的初始值
	 * 
	 * @param seqName
	 *            序列名称
	 * @param seqValue
	 *            序列值
	 * @throws Exception
	 * @author liull2
	 * @date 2013-5-21 下午1:43:04
	 */
	public void modifySequence(String seqName, long seqValue) throws Exception;

	/**
	 * 创建sequence
	 * 
	 * @param seqName
	 *            序列名称
	 * @param startNum
	 *            序列从startNum开始
	 * @throws Exception
	 * @author liull2
	 * @date 2013-5-21 下午1:43:10
	 */
	public void createSequence(String seqName, int startNum) throws Exception;

	/**
	 * 删除表
	 * 
	 * @param tableName
	 *            表名
	 * @throws Exception
	 * @author liull2
	 * @date 2013-5-21 下午1:43:20
	 */
	public void dropTable(String tableName) throws Exception;

	/**
	 * 删除序列
	 * 
	 * @param seqName
	 *            序列名称
	 * @throws Exception
	 * @author liull2
	 * @date 2013-5-21 下午1:43:27
	 */
	public void dropSequence(String seqName) throws Exception;

	/**
	 * 创建索引
	 * 
	 * @param indexName
	 *            索引名称
	 * @param tableName
	 *            表名称
	 * @param columnName
	 *            列名
	 * @throws Exception
	 * @author 刘蕾蕾
	 * @date 2013-4-27 下午03:55:06
	 */
	public void createIndex(String indexName, String tableName,
			List<String> columnNames) throws Exception;

	/**
	 * 删除索引
	 * 
	 * @param indexName
	 * @throws Exception
	 * @author wangchengjun
	 * @date 2014-8-16 下午2:56:37
	 */
	public void dropIndex(String indexName) throws Exception;
}
