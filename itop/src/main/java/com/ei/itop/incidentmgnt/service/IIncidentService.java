/**
 * 
 */
package com.ei.itop.incidentmgnt.service;

import java.util.List;

import com.ei.itop.common.dbentity.IcIncident;
import com.ei.itop.incidentmgnt.bean.IncidentInfo;
import com.ei.itop.incidentmgnt.bean.QCIncident;

/**
 * @author Jack.Qi
 * 
 */
public interface IIncidentService {

	/**
	 * 查询事件
	 * 
	 * @param qcIncident
	 *            查询条件
	 * @param startIndex
	 *            分页起始记录，-1为不分页
	 * @param pageSize
	 *            每页记录数
	 * @param opId
	 *            操作员ID
	 * @return 事件记录列表
	 * @throws Exception
	 */
	public List<IcIncident> MBLQueryIncident(QCIncident qcIncident,
			long startIndex, int pageSize, long opId) throws Exception;

	/**
	 * 查询事件记录数
	 * 
	 * @param qcIncident
	 *            查询条件
	 * @param opId
	 *            操作员ID
	 * @return 记录数
	 * @throws Exception
	 */
	public long MBLQueryIncidentCount(QCIncident qcIncident, long opId)
			throws Exception;

	/**
	 * 根据ID查询某一事件详细信息
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opId
	 *            操作员ID
	 * @return
	 * @throws Exception
	 */
	public IcIncident MBLQueryIncident(long incidentId, long opId)
			throws Exception;

	/**
	 * 根据ID查询某一事件详细信息
	 * 
	 * @param incidentId
	 *            事件ID
	 * @return
	 * @throws Exception
	 */
	public IcIncident queryIncident(long incidentId) throws Exception;

	/**
	 * 新建一个事件
	 * 
	 * @param incidentInfo
	 *            事件信息
	 * @param opId
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLAddIncident(IncidentInfo incidentInfo, long opId)
			throws Exception;

	/**
	 * 修改事件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incidentInfo
	 *            事件信息
	 * @param opId
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLModifyIncident(long incidentId, IncidentInfo incidentInfo,
			long opId) throws Exception;

	/**
	 * 修改事件部分属性
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incident
	 *            事件信息，不为空的被认为是修改字段
	 * @return 事件ID
	 * @throws Exception
	 */
	public long modifyIncidentSelective(long incidentId, IcIncident incident)
			throws Exception;

	/**
	 * 新增事件时直接提交，提交事件时系统自动生成第一条事务
	 * 
	 * @param incidentInfo
	 *            事件信息
	 * @param opId
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLAddAndCommitIncident(IncidentInfo incidentInfo, long opId)
			throws Exception;

	/**
	 * 编辑事件时直接提交，提交事件时系统自动生成第一条事务
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incidentInfo
	 *            事件信息
	 * @param opId
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLModifyAndCommitIncident(long incidentId,
			IncidentInfo incidentInfo, long opId) throws Exception;

	/**
	 * 提交事件，目前页面中并没有直接提交事件的入口
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opId
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long MBLCommitIncident(long incidentId, long opId) throws Exception;

	/**
	 * 删除事件，逻辑删除，只能刪除未提交的事件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opId
	 *            操作员ID
	 * @throws Exception
	 *             当事件状态不可删除时会也抛出异常
	 */
	public void MBLRemoveIncident(long incidentId, long opId) throws Exception;

	/**
	 * 顾问补全事件影响度、事件分类、事件优先级三部分内容，此逻辑在顾问提交事务时判断信息是否尚未补全触发调用
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incidentInfo
	 *            事件信息，此逻辑仅关注顾问设置的影响度、分类、优先级三个信息
	 * @throws Exception
	 */
	public void adviserCompleteInfo(long incidentId, IncidentInfo incidentInfo)
			throws Exception;

	/**
	 * 用户反馈满意度
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param incidentInfo
	 *            事件信息，此逻辑仅关注用户设置的满意度信息
	 * @param opId
	 *            操作员ID
	 * @throws Exception
	 */
	public void MBLUserSetFeedbackVal(long incidentId,
			IncidentInfo incidentInfo, long opId) throws Exception;

	/**
	 * 顾问关闭事件
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opId
	 *            操作员ID
	 * @throws Exception
	 */
	public void MBLAdviserCloseIncident(long incidentId, long opId)
			throws Exception;
}
