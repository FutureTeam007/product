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
import com.ei.itop.common.bean.OpInfo;
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
	public long MBLQueryIncidentCount(QCIncident qcIncident, OpInfo opInfo)
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
	public IcIncident MBLQueryIncident(long incidentId, OpInfo opInfo)
			throws Exception {
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
	 * com.ei.itop.incidentmgnt.service.IIncidentService#queryIncident(long)
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
	public long MBLAddIncidentAndAttach(IncidentInfo incidentInfo, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		// 保存事件信息
		long incidentId = addIncidentAndAttach(incidentInfo, opInfo);

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
	protected long addIncidentAndAttach(IncidentInfo incidentInfo, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		// IncidentInfo ii = new IncidentInfo();

		// 业务信息
		// ii.setScOrgId(incidentInfo.getScOrgId());
		// ii.setScOrgName(incidentInfo.getScOrgName());
		// ii.setCcCustId(incidentInfo.getCcCustId());
		// ii.setCustName(incidentInfo.getCustName());
		//
		// ii.setScProductId(incidentInfo.getScProductId());
		// ii.setProdName(incidentInfo.getProdName());
		// ii.setScModuleId(incidentInfo.getScModuleId());
		// ii.setModuleName(incidentInfo.getModuleName());
		// ii.setAffectCodeUser(incidentInfo.getAffectCodeUser());
		// ii.setAffectValUser(incidentInfo.getAffectValUser());
		// ii.setClassCodeUser(incidentInfo.getClassCodeUser());
		// ii.setClassValUser(incidentInfo.getClassValUser());
		// ii.setBrief(incidentInfo.getBrief());
		// ii.setHappenTime(incidentInfo.getHappenTime());
		// ii.setDetail(incidentInfo.getDetail());
		// ii.setCcList(incidentInfo.getCcList());

		// 自动填入事件系列号
		incidentInfo.setIncidentCode(generateIncidentCode());

		// 自动填入事件提出用户、创建人、修改人
		incidentInfo.setCcUserId(opInfo.getOpId());
		incidentInfo.setCcLoginCode(opInfo.getOpCode());
		incidentInfo.setCreator(opInfo.getOpName());

		// 保存事件实体信息
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
	public long MBLModifyIncidentAndAttach(long incidentId,
			IncidentInfo incidentInfo, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 保存事件实体信息
		modifyIncidentAndAttach(incidentId, incidentInfo, opInfo);

		// 记录系统操作日志

		return incidentId;
	}

	/**
	 * 生成事件系列号
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String generateIncidentCode() throws Exception {
		return "XXX001";
	}

	/**
	 * 修改事件，含附件
	 * 
	 * @param incidentId
	 * @param incidentInfo
	 * @param opInfo
	 * @return
	 * @throws Exception
	 */
	protected long modifyIncidentAndAttach(long incidentId,
			IncidentInfo incidentInfo, OpInfo opInfo) throws Exception {

		// IncidentInfo ii = new IncidentInfo();

		// 业务信息
		// ii.setScOrgId(incidentInfo.getScOrgId());
		// ii.setScOrgName(incidentInfo.getScOrgName());
		// ii.setCcCustId(incidentInfo.getCcCustId());
		// ii.setCustName(incidentInfo.getCustName());
		//
		// ii.setScProductId(incidentInfo.getScProductId());
		// ii.setProdName(incidentInfo.getProdName());
		// ii.setScModuleId(incidentInfo.getScModuleId());
		// ii.setModuleName(incidentInfo.getModuleName());
		// ii.setAffectCodeUser(incidentInfo.getAffectCodeUser());
		// ii.setAffectValUser(incidentInfo.getAffectValUser());
		// ii.setClassCodeUser(incidentInfo.getClassCodeUser());
		// ii.setClassValUser(incidentInfo.getClassValUser());
		// ii.setBrief(incidentInfo.getBrief());
		// ii.setHappenTime(incidentInfo.getHappenTime());
		// ii.setDetail(incidentInfo.getDetail());
		// ii.setCcList(incidentInfo.getCcList());
		//
		// ii.setItStateCode(incidentInfo.getItStateCode());
		// ii.setItStateVal(incidentInfo.getItStateVal());

		// 设置主键
		incidentInfo.setIcIncidentId(incidentId);

		// 修改人
		incidentInfo.setModifier(opInfo.getOpName());

		// 保存事件实体信息
		incidentDAO.update("IC_INCIDENT.updateByPrimaryKeySelective",
				incidentInfo);

		// 保存附件信息

		return incidentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#commitIncident(com.
	 * ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public long MBLAddAndCommitIncidentAndAttach(IncidentInfo incidentInfo,
			OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 提交时需调整事件状态为待响应
		incidentInfo.setItStateVal("待响应");
		incidentInfo.setItStateCode("2");

		// 保存事件信息
		long incidentId = addIncidentAndAttach(incidentInfo, opInfo);

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
	public long MBLModifyAndCommitIncidentAndAttach(long incidentId,
			IncidentInfo incidentInfo, OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 提交时需调整事件状态为待响应
		incidentInfo.setItStateVal("待响应");
		incidentInfo.setItStateCode("2");

		// 保存事件信息
		modifyIncidentAndAttach(incidentId, incidentInfo, opInfo);

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
	public long MBLCommitIncident(long incidentId, OpInfo opInfo)
			throws Exception {
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
	public void MBLRemoveIncident(long incidentId, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		IcIncident incident = queryIncident(incidentId);

		// 如果不是待提交状态，则不允许删除事件
		if (!"0".equals(incident.getItStateCode())) {
			throw new Exception("只有待提交状态的事件才可以删除");
		}

		// 修改事件数据状态为已失效
		IncidentInfo ii = new IncidentInfo();
		ii.setDataState(new Long(0));

		// 修改记录
		modifyIncidentAndAttach(incidentId, ii, opInfo);

		// 记录系统操作日志
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#adviserCompleteInfo
	 * (com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public void adviserCompleteInfo(long incidentId, IcIncident incident,
			OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		if (incident.getAffectCodeOp() == null
				|| incident.getAffectValOp() == null
				|| incident.getClassCodeOp() == null
				|| incident.getClassValOp() == null
				|| incident.getPriorityCode() == null
				|| incident.getPriorityVal() == null) {
			throw new Exception("必须一次性补全顾问影响度、分类、优先级，缺一不可");
		}

		IncidentInfo ii = new IncidentInfo();

		// 设置主键
		ii.setIcIncidentId(incidentId);

		// 业务信息
		ii.setAffectCodeOp(incident.getAffectCodeOp());
		ii.setAffectValOp(incident.getAffectValOp());
		ii.setClassCodeOp(incident.getClassCodeOp());
		ii.setClassValOp(incident.getClassValOp());
		ii.setPriorityCode(incident.getPriorityCode());
		ii.setPriorityVal(incident.getPriorityVal());

		// 保存事件信息
		modifyIncidentAndAttach(incidentId, ii, opInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#userSetFeedbackVal(
	 * com.ei.itop.incidentmgnt.bean.IncidentInfo, long)
	 */
	public void MBLUserSetFeedbackVal(long incidentId, IcIncident incident,
			OpInfo opInfo) throws Exception {
		// TODO Auto-generated method stub

		// 判断传入信息是否完整
		if (incident.getFeedbackCode() == null
				|| incident.getFeedbackVal() == null) {
			throw new Exception("用户满意度信息填写不完整");
		}

		// 查询事件信息
		IcIncident icIncident = queryIncident(incidentId);

		// 仅当事件状态为8-已完成时才可以进行用户反馈满意度操作
		if (!"8".equals(icIncident.getItStateCode())) {
			throw new Exception("仅当事件状态为已完成时才可以评价");
		}

		IncidentInfo ii = new IncidentInfo();

		// 业务信息
		ii.setFeedbackCode(incident.getFeedbackCode());
		ii.setFeedbackVal(incident.getFeedbackVal());
		ii.setFeedbackTime(commonDAO.getSysDate());

		// 保存事件信息
		modifyIncidentAndAttach(incidentId, ii, opInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ei.itop.incidentmgnt.service.IIncidentService#closeIncident(long)
	 */
	public void MBLAdviserCloseIncident(long incidentId, OpInfo opInfo)
			throws Exception {
		// TODO Auto-generated method stub

		// 查询事件信息
		IcIncident incident = queryIncident(incidentId);

		// 仅当事件状态为8-已完成，且用户已经评价完毕时才可以进行用户反馈满意度操作
		log.debug("******/" + incident.getFeedbackCode() + "/***");
		if (!"8".equals(incident.getItStateCode())) {
			throw new Exception("仅当事件状态为已完成且用户已经评价完毕时才可以关闭");
		} else if (incident.getFeedbackCode() == null) {
			throw new Exception("仅当事件状态为已完成且用户已经评价完毕时才可以关闭");
		}

		IncidentInfo ii = new IncidentInfo();

		ii.setItStateCode("9");
		ii.setItStateVal("已关闭");

		// 保存事件信息
		modifyIncidentAndAttach(incidentId, ii, opInfo);
	}

	public static void main(String[] args) throws Exception {
		IIncidentService is = (IIncidentService) AppContext
				.getBean("incidentService");

		OpInfo oi = new OpInfo();
		oi.setOpType("OP");
		oi.setOpId(0);
		oi.setOpCode("SamZhang@163.com");
		oi.setOpName("Sam.Zhang（张三）");

		IncidentInfo ii = new IncidentInfo();
		// is.MBLAddIncident(ii, oi);

		// ii.setScOrgId(new Long(2001));
		// ii.setScOrgName("拓创");
		// is.MBLModifyIncidentAndAttach(10000, ii, oi);

		// ii.setCcCustId(new Long(300001));
		// ii.setCustName("CNH公司");
		// is.MBLModifyAndCommitIncidentAndAttach(10000, ii, oi);

		// ii.setAffectCodeOp("2");
		// ii.setAffectValOp("一般");
		// ii.setClassCodeOp("12");
		// ii.setClassValOp("业务咨询");
		// ii.setPriorityCode("3");
		// ii.setPriorityVal("高");
		// is.adviserCompleteInfo(10000, ii, oi);

		// is.MBLRemoveIncident(10001, oi);

		// is.queryIncident(10003);

		// ii.setFeedbackCode("3");
		// ii.setFeedbackVal("不满意");
		// is.MBLUserSetFeedbackVal(10003, ii, oi);

		// is.MBLAdviserCloseIncident(10005, oi);

		is.MBLAddAndCommitIncidentAndAttach(ii, oi);
		is.MBLModifyAndCommitIncidentAndAttach(10000, ii, oi);
	}
}
