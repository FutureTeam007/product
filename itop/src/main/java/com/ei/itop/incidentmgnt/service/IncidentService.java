/**
 * 
 */
package com.ei.itop.incidentmgnt.service;

import java.util.List;

import com.ei.itop.common.bean.OpInfo;
import com.ei.itop.common.dbentity.CcCust;
import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.incidentmgnt.bean.IncidentCountInfoByState;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.QCIncident;

/**
 * @author Jack.Qi
 * 
 */
public interface IncidentService {

	/**
	 * 查询事件
	 * 
	 * @param qcIncident
	 *            查询条件
	 * @param startIndex
	 *            分页起始记录，从1开始，-1为不分页
	 * @param pageSize
	 *            每页记录数
	 * @param opId
	 *            操作员ID
	 * @return 事件记录列表
	 * @throws Exception
	 */
	public List<IcIncident> MBLQueryIncident(QCIncident qcIncident,
			long startIndex, int pageSize, OpInfo opInfo) throws Exception;

	/**
	 * 查询事件记录数
	 * 
	 * @param qcIncident
	 *            查询条件
	 * @param opInfo
	 *            操作员ID
	 * @return 记录数
	 * @throws Exception
	 */
	public long MBLQueryIncidentCount(QCIncident qcIncident, OpInfo opInfo)
			throws Exception;

	/**
	 * 根据ID查询某一事件详细信息
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opInfo
	 *            操作员ID
	 * @return
	 * @throws Exception
	 */
	public IncidentInfo MBLQueryIncident(long incidentId, OpInfo opInfo)
			throws Exception;

	/**
	 * 查询事件数量，按状态分组
	 * 
	 * @param qcIncident
	 * @param opInfo
	 * @throws Exception
	 */
	public List<IncidentCountInfoByState> MBLQueryIncidentCountGroupByState(
			QCIncident qcIncident, long orgId, OpInfo opInfo) throws Exception;

	/**
	 * 根据ID查询某一事件详细信息，此逻辑为原子逻辑，不会触发记录系统操作日志
	 * 
	 * @param incidentId
	 *            事件ID
	 * @return
	 * @throws Exception
	 */
	public IncidentInfo queryIncident(long incidentId) throws Exception;

	/**
	 * 获得事件所处阶段
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getItPhase(long orgId, long custId, long productId, long opId)
			throws Exception;

	/**
	 * 新创建一个事件，包括附件，不提交，仅保存
	 * 
	 * @param incidentInfo
	 *            事件信息含附件
	 * @param opInfo
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLAddIncidentAndAttach(IncidentInfo incidentInfo, OpInfo opInfo)
			throws Exception;

	/**
	 * 修改事件信息，含附件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incidentInfo
	 *            事件信息含附件
	 * @param opInfo
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLModifyIncidentAndAttach(long incidentId,
			IncidentInfo incidentInfo, OpInfo opInfo) throws Exception;

	/**
	 * 新增事件时直接提交，提交事件时系统自动生成第一条事务
	 * 
	 * @param incidentInfo
	 *            事件信息
	 * @param opInfo
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLAddAndCommitIncidentAndAttach(IncidentInfo incidentInfo,
			OpInfo opInfo) throws Exception;

	/**
	 * 编辑事件时直接提交，提交事件时系统自动生成第一条事务
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incidentInfo
	 *            事件信息
	 * @param opInfo
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLModifyAndCommitIncidentAndAttach(long incidentId,
			IncidentInfo incidentInfo, OpInfo opInfo) throws Exception;

	/**
	 * 提交事件，目前页面中并没有直接提交事件的入口
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opInfo
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLCommitIncident(long incidentId, OpInfo opInfo)
			throws Exception;

	/**
	 * 删除事件，逻辑删除，只能刪除未提交的事件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opInfo
	 *            操作员ID
	 * @throws Exception
	 *             当事件状态不可删除时会也抛出异常
	 */
	public void MBLRemoveIncident(long incidentId, OpInfo opInfo)
			throws Exception;

	/**
	 * 顾问补全事件影响度、事件分类、事件优先级、时间复杂度几部分内容，此逻辑在顾问提交事务时判断信息是否尚未补全触发调用
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incident
	 *            事件信息，此逻辑仅关注顾问设置的影响度、分类、优先级、复杂度几个信息
	 * @throws Exception
	 */
	public void MBLAdviserCompleteInfo(long incidentId, IcIncident incident,
			OpInfo opInfo) throws Exception;

	/**
	 * 用户反馈满意度
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incident
	 *            事件信息，此逻辑仅关注用户设置的满意度信息
	 * @param opInfo
	 *            操作员ID
	 * @throws Exception
	 */
	public void MBLUserSetFeedbackVal(long incidentId, IcIncident incident,
			OpInfo opInfo) throws Exception;

	/**
	 * 顾问关闭事件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opInfo
	 *            操作员ID
	 * @throws Exception
	 */
	public void MBLAdviserCloseIncident(long incidentId, OpInfo opInfo)
			throws Exception;

	/**
	 * 修改事件
	 * 
	 * @param incidentId
	 * @param incidentInfo
	 * @param opInfo
	 * @return
	 * @throws Exception
	 */
	public long modifyIncidentAndAttach(long incidentId,
			IncidentInfo incidentInfo, OpInfo opInfo) throws Exception;
	/**
	 * 用户归档事件
	 * @param incidentId
	 * @param stockFlags
	 * @param oi
	 */
	public void MBLUserStockIncident(long incidentId, String[] stockFlags,
			OpInfo oi) throws Exception ;
}
