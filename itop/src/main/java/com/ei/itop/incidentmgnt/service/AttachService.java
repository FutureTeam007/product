/**
 * 
 */
package com.ei.itop.incidentmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.IcAttach;

/**
 * @author Jack.Qi
 * 
 */
public interface AttachService {

	/**
	 * 保存事件的附件，每次保存会将原有的记录直接物理删除
	 * 
	 * @param incidentId
	 * @param attachList
	 * @throws Exception
	 */
	public void saveAttach(long incidentId, List<IcAttach> attachList)
			throws Exception;

	/**
	 * 保存事务的附件，每次保存会将原有的记录直接物理删除
	 * 
	 * @param incidentId
	 * @param transactionId
	 * @param attachList
	 * @throws Exception
	 */
	public void saveAttach(long incidentId, long transactionId,
			List<IcAttach> attachList) throws Exception;

	/**
	 * 将事件的附件转为事务的附件，在事件提交时调用该逻辑将事件的附件转为第一事务的附件
	 * 
	 * @param incidentId
	 * @param transactionId
	 * @throws Exception
	 */
	public void changeTransAttach2IncidAttach(long incidentId,
			long transactionId) throws Exception;

	/**
	 * 根据事件ID、事务ID查询附件列表
	 * 
	 * @param incidentId
	 * @param transactionId
	 * @return
	 * @throws Exception
	 */
	public List<IcAttach> getAttachList(long incidentId, Long transactionId)
			throws Exception;
	/**
	 * 保存单个附件
	 * @param attach
	 * @return
	 * @throws Exception
	 */
	public long saveAttach(IcAttach attach) throws Exception;
	/**
	 * 查询附件列表
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<IcAttach> queryAttachList(long[] ids) throws Exception;
	/**
	 * 根据主键删除附件
	 * @param id
	 * @throws Exception
	 */
	public void deleteAttachByPrimaryId(long id) throws Exception;
}
