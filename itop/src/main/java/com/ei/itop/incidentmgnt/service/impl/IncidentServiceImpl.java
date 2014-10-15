/**
 * 
 */
package com.ei.itop.incidentmgnt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ailk.dazzle.util.AppContext;
import com.ailk.dazzle.util.ibatis.GenericDAO;
import com.ei.itop.common.dao.ICommonDAO;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.QCIncident;
import com.ei.itop.incidentmgnt.service.IIncidentService;

/**
 * @author Jack.Qi
 * 
 */
@Service(value = "incidentService")
public class IncidentServiceImpl implements IIncidentService {

	private static final Logger log = Logger
			.getLogger(IncidentServiceImpl.class);

	@Resource(name = "app.siCommonDAO")
	private GenericDAO<Long, IcIncident> incidentDAO;

	@Resource(name = "commonDDLDAO")
	private ICommonDAO commonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#queryIncident(com.ei
	 * .itop.incidentmgnt.bean.QCIncident, long, int, long)
	 */
	public List<IcIncident> MBLQueryIncident(QCIncident qcIncident,
			long startIndex, int pageSize, long opId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#queryIncidentCount(
	 * com.ei.itop.incidentmgnt.bean.QCIncident, long)
	 */
	public long MBLQueryIncidentCount(QCIncident qcIncident, long opId)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#queryIncident(long,
	 * long)
	 */
	public IcIncident MBLQueryIncident(long incidentId, long opId)
			throws Exception {
		// TODO Auto-generated method stub
		IcIncident incident = incidentDAO.find(
				"IC_INCIDENT.selectByPrimaryKey", incidentId);

		// 记录系统操作日志

		return incident;
	}

	/**
	 * 根据ID查询某一事件详细信息，此逻辑为原子逻辑，不会触发记录系统操作日志
	 * 
	 * @param incidentId
	 * @return
	 * @throws Exception
	 */
	public IcIncident queryIncident(long incidentId) throws Exception {
		// TODO Auto-generated method stub
		IcIncident incident = incidentDAO.find(
				"IC_INCIDENT.selectByPrimaryKey", incidentId);

		// 记录系统操作日志

		return incident;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#addIncident(com.ei.
	 * itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long MBLAddIncident(IncidentInfo incidentInfo, long opId)
			throws Exception {
		// TODO Auto-generated method stub

		// 保存事件信息
		long incidentId = incidentDAO.save("IC_INCIDENT.insert", incidentInfo);

		// 保存附件信息

		// 记录系统操作日志

		return incidentId;
	}

	/**
	 * 新增事件，此逻辑为原子逻辑，不会触发记录系统操作日志
	 * 
	 * @param incidentInfo
	 * @return
	 * @throws Exception
	 */
	private long addIncident(IncidentInfo incidentInfo) throws Exception {
		// TODO Auto-generated method stub

		// 保存事件信息
		long incidentId = incidentDAO.save("IC_INCIDENT.insert", incidentInfo);

		// 保存附件信息

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#modifyIncident(long,
	 * com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long MBLModifyIncident(long incidentId, IncidentInfo incidentInfo,
			long opId) throws Exception {
		// TODO Auto-generated method stub

		// 保存事件信息
		incidentDAO.update("IC_INCIDENT.updateByPrimaryKey", incidentInfo);

		// 保存附件信息

		// 记录系统操作日志

		return incidentId;
	}

	/**
	 * 修改事件，此逻辑为原子逻辑，不会触发记录系统操作日志
	 * 
	 * @param incidentId
	 * @param incidentInfo
	 * @return
	 * @throws Exception
	 */
	private long modifyIncident(long incidentId, IncidentInfo incidentInfo)
			throws Exception {
		// TODO Auto-generated method stub

		// 保存事件信息
		incidentDAO.update("IC_INCIDENT.updateByPrimaryKey", incidentInfo);

		// 保存附件信息

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#modifyIncidentSelective
	 * (long, com.ei.itop.common.dbentity.IcIncident)
	 */
	public long modifyIncidentSelective(long incidentId, IcIncident incident)
			throws Exception {
		// TODO Auto-generated method stub

		// 保存事件信息
		incidentDAO.update("IC_INCIDENT.updateByPrimaryKeySelective", incident);

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#commitIncident(com.
	 * ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long MBLAddAndCommitIncident(IncidentInfo incidentInfo, long opId)
			throws Exception {
		// TODO Auto-generated method stub

		// 提交时需调整事件状态为待响应
		incidentInfo.setItStateVal("待响应");
		incidentInfo.setItStateCode("2");

		// 保存事件信息
		long incidentId = MBLAddIncident(incidentInfo, opId);

		// 系统自动生成第一条事务

		// 将事件的附件转为第一条事务的附件
		// 即将附件表的事务ID由null改为第一条事务的ID

		// 记录系统操作日志

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#commitIncident(long,
	 * com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long MBLModifyAndCommitIncident(long incidentId,
			IncidentInfo incidentInfo, long opId) throws Exception {
		// TODO Auto-generated method stub

		// 提交时需调整事件状态为待响应
		incidentInfo.setItStateVal("待响应");
		incidentInfo.setItStateCode("2");

		// 保存事件信息
		modifyIncident(incidentId, incidentInfo);

		// 系统自动生成第一条事务

		// 将事件的附件转为第一条事务的附件
		// 即将附件表的事务ID由null改为第一条事务的ID

		// 记录系统操作日志

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#commitIncident(long,
	 * long)
	 */
	public long MBLCommitIncident(long incidentId, long opId) throws Exception {
		// TODO Auto-generated method stub

		throw new Exception("目前系统中并没有直接提交事件的入口，此逻辑暂未实现");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#removeIncident(long,
	 * long)
	 */
	public void MBLRemoveIncident(long incidentId, long opId) throws Exception {
		// TODO Auto-generated method stub

		IcIncident incident = queryIncident(incidentId);

		// 如果不是待提交状态，则不允许删除事件
		if (!"0".equals(incident.getItStateCode())) {
			throw new Exception("只有待提交状态的事件才可以删除");
		}

		// 修改事件数据状态为已失效

		// 记录系统操作日志
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#adviserCompleteInfo
	 * (com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public void adviserCompleteInfo(long incidentId, IncidentInfo incidentInfo)
			throws Exception {
		// TODO Auto-generated method stub

		if (incidentInfo.getAffectCodeOp() == null
				|| incidentInfo.getAffectValOp() == null
				|| incidentInfo.getClassCodeOp() == null
				|| incidentInfo.getClassValOp() == null
				|| incidentInfo.getPriorityCode() == null
				|| incidentInfo.getPriorityVal() == null) {
			throw new Exception("必须一次性补全顾问影响度、分类、优先级，缺一不可");
		}

		IcIncident incident = new IcIncident();

		incident.setAffectCodeOp(incidentInfo.getAffectCodeOp());
		incident.setAffectValOp(incidentInfo.getAffectValOp());
		incident.setClassCodeOp(incidentInfo.getClassCodeOp());
		incident.setClassValOp(incidentInfo.getClassValOp());
		incident.setPriorityCode(incidentInfo.getPriorityCode());
		incident.setPriorityVal(incidentInfo.getPriorityVal());

		// 修改事件的顾问影响度、分类、优先级字段
		incidentDAO.update("IC_INCIDENT.updateByPrimaryKeySelective", incident);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#userSetFeedbackVal(
	 * com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public void MBLUserSetFeedbackVal(long incidentId,
			IncidentInfo incidentInfo, long opId) throws Exception {
		// TODO Auto-generated method stub

		// 查询事件信息
		IcIncident incident = queryIncident(incidentId);

		// 仅当事件状态为8-已完成时才可以进行用户反馈满意度操作
		if (!"8".equals(incident.getItStateCode())) {
			throw new Exception("仅当事件状态为已完成时才可以评价");
		}

		// 判断传入信息是否完整
		if (incidentInfo.getFeedbackCode() == null
				|| incidentInfo.getFeedbackVal() == null) {
			throw new Exception("用户满意度信息填写不完整");
		}

		incident = new IcIncident();

		incident.setFeedbackCode(incidentInfo.getFeedbackCode());
		incident.setFeedbackVal(incidentInfo.getFeedbackVal());
		incident.setFeedbackTime(commonDAO.getSysDate());

		// 修改事件的用户反馈字段
		incidentDAO.update("IC_INCIDENT.updateByPrimaryKeySelective", incident);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#closeIncident(long)
	 */
	public void MBLAdviserCloseIncident(long incidentId, long opId)
			throws Exception {
		// TODO Auto-generated method stub

		// 查询事件信息
		IcIncident incident = queryIncident(incidentId);

		// 仅当事件状态为8-已完成，且用户已经评价完毕时才可以进行用户反馈满意度操作
		if (!"8".equals(incident.getItStateCode())) {
			throw new Exception("仅当事件状态为已完成且用户已经评价完毕时才可以关闭");
		} else if (incident.getFeedbackCode() == null) {
			throw new Exception("仅当事件状态为已完成且用户已经评价完毕时才可以关闭");
		}

		incident = new IcIncident();

		incident.setItStateCode("9");
		incident.setItStateVal("已关闭");
		// incident.setLastModifyTime(incidentDAO.);

		// 修改事件的用户反馈字段
		incidentDAO.update("IC_INCIDENT.updateByPrimaryKeySelective", incident);
	}

	public static void main(String[] args) throws Exception {
		IIncidentService is = (IIncidentService) AppContext
				.getBean("incidentService");
		is.MBLRemoveIncident(1, 1);

	}
}
