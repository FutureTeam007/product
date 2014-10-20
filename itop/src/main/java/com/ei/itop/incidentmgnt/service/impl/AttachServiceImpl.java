/**
 * 
 */
package com.ei.itop.incidentmgnt.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dbentity.IcAttach;
import com.ei.itop.incidentmgnt.service.AttachService;

/**
 * @author Jack.Qi
 * 
 */
@Service("attachService")
public class AttachServiceImpl implements AttachService {

	private static final Logger log = Logger.getLogger(AttachServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, IcAttach> attachDAO;

	/**
	 * 删除某事件的附件，此逻辑会判断只有事务ID为空的附件记录才符合条件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @throws Exception
	 */
	private void deleteAttach(long incidentId) throws Exception {

		attachDAO.delete("IC_ATTACH.deleteByIncident", incidentId);
	}

	/**
	 * 删除某事务的附件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param transactionId
	 *            事务ID
	 * @throws Exception
	 */
	private void deleteAttach(long incidentId, long transactionId)
			throws Exception {

		HashMap<String, Object> hm = new HashMap<String, Object>();

		hm.put("incidentId", incidentId);
		hm.put("transactionId", transactionId);

		attachDAO.deleteByParams("IC_ATTACH.deleteByTransaction", hm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.incidentmgnt.service.AttachService#saveAttach(long,
	 * java.util.List)
	 */
	public void saveAttach(long incidentId, List<IcAttach> attachList)
			throws Exception {
		// TODO Auto-generated method stub

		// 先删除原有附件
		deleteAttach(incidentId);

		// 设置事件ID
		for (int i = 0; attachList != null && i < attachList.size(); i++) {
			attachList.get(i).setIcIncidentId(incidentId);
			attachList.get(i).setTransId(null);
		}

		// 保存新附件
		attachDAO.saveBatch("IC_ATTACH.insert", attachList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.incidentmgnt.service.AttachService#saveAttach(long,
	 * long, java.util.List)
	 */
	public void saveAttach(long incidentId, long transactionId,
			List<IcAttach> attachList) throws Exception {
		// TODO Auto-generated method stub

		// 先删除原有附件
		deleteAttach(incidentId, transactionId);

		// 设置事件ID、事务ID
		for (int i = 0; attachList != null && i < attachList.size(); i++) {
			attachList.get(i).setIcIncidentId(incidentId);
			attachList.get(i).setTransId(transactionId);
		}

		// 保存新附件
		attachDAO.saveBatch("IC_ATTACH.insert", attachList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.AttachService#changeTransAttach2IncidAttach
	 * (long, long)
	 */
	public void changeTransAttach2IncidAttach(long incidentId,
			long transactionId) throws Exception {
		// TODO Auto-generated method stub

		IcAttach attach = new IcAttach();
		attach.setIcIncidentId(incidentId);
		attach.setTransId(transactionId);

		attachDAO.update("IC_ATTACH.changeTransAttach2IncidAttach", attach);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ei.itop.incidentmgnt.service.AttachService#getAttachList(long,
	 * long)
	 */
	public List<IcAttach> getAttachList(long incidentId, Long transactionId)
			throws Exception {
		// TODO Auto-generated method stub

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("incidentId", incidentId);
		hm.put("transactionId", transactionId);

		List<IcAttach> list = attachDAO.findByParams(
				"IC_ATTACH.queryAttachLIstByIncidentIdAndTransactionId", hm);

		return list;
	}

}
