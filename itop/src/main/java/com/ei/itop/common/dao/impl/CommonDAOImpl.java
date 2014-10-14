/**
 * 
 */
package com.ei.itop.common.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dao.ICommonDAO;

/**
 * @author Jack.Qi
 * 
 */
@Component("commonDDLDAO")
public class CommonDAOImpl implements ICommonDAO {

	private GenericDAO commonDAO;

	/**
	 * @return the commonDAO
	 */
	public GenericDAO getCommonDAO() {
		return commonDAO;
	}

	/**
	 * @param commonDAO
	 *            the commonDAO to set
	 */
	public void setCommonDAO(GenericDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.common.dao.ICommonDAO#getSequenceNextValue(java.lang.String)
	 */
	public long getSequenceNextValue(String seqName) throws Exception {
		// TODO Auto-generated method stub
		Long value = (Long) commonDAO
				.find("COMMON.getSequenceNextVal", seqName);
		return value == null ? 0 : value.longValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.common.dao.ICommonDAO#getSysDate()
	 */
	public Date getSysDate() throws Exception {
		// TODO Auto-generated method stub
		Date sysDate = (Date) commonDAO.find("COMMON.getSysDate", null);
		return sysDate == null ? new Date() : sysDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.common.dao.ICommonDAO#isTableExist(java.lang.String)
	 */
	public boolean isTableExist(String tableName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", tableName);
		long count = (Long) commonDAO.find("COMMON.getTableCount", params);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.common.dao.ICommonDAO#createSequence(java.lang.String)
	 */
	public void createSequence(String seqName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqName", seqName);
		commonDAO.execStatement("COMMON.createSequence", params);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.common.dao.ICommonDAO#modifySequence(java.lang.String,
	 * long)
	 */
	public void modifySequence(String seqName, long seqValue) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqName", seqName);
		params.put("seqValue", seqValue);
		commonDAO.execStatement("COMMON.modifySequence", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.common.dao.ICommonDAO#createSequence(java.lang.String,
	 * int)
	 */
	public void createSequence(String seqName, int startNum) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqName", seqName);
		params.put("startNum", startNum);
		commonDAO.execStatement("COMMON.createSequenceWithStartNum", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.common.dao.ICommonDAO#dropTable(java.lang.String)
	 */
	public void dropTable(String tableName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", tableName);
		commonDAO.execStatement("COMMON.dropTable", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.common.dao.ICommonDAO#dropSequence(java.lang.String)
	 */
	public void dropSequence(String seqName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seqName", seqName);
		commonDAO.execStatement("COMMON.dropSequence", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.common.dao.ICommonDAO#createIndex(java.lang.String,
	 * java.lang.String, java.util.List)
	 */
	public void createIndex(String indexName, String tableName,
			List<String> columnNames) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("indexName", indexName);
		params.put("tableName", tableName);
		if (columnNames != null && columnNames.size() != 0) {
			String columnNameStr = "";
			for (int i = 0; i < columnNames.size(); i++) {
				if (i == columnNames.size() - 1) {
					columnNameStr += columnNames.get(i) + " ASC";
				} else {
					columnNameStr += columnNames.get(i) + " ASC,";
				}
			}
			params.put("columnNames", columnNameStr);
			commonDAO.execStatement("COMMON.createIndex", params);
		} else {
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.common.dao.ICommonDAO#dropIndex(java.lang.String)
	 */
	public void dropIndex(String indexName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("indexName", indexName);
		commonDAO.execStatement("COMMON.dropIndex", params);
	}

}
