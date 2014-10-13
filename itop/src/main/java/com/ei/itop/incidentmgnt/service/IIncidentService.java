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
	public List<IcIncident> queryIncident(QCIncident qcIncident,
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
	public long queryIncidentCount(QCIncident qcIncident, long opId)
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
	public IcIncident queryIncident(long incidentId, long opId)
			throws Exception;

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
	public long addIncident(IncidentInfo incidentInfo, long opId)
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
	public long modifyIncident(long incidentId, IncidentInfo incidentInfo,
			long opId) throws Exception;

	/**
	 * 新增事件时直接提交
	 * 
	 * @param incidentInfo
	 *            事件信息
	 * @param opId
	 *            操作员ID
	 * @return 事件ID
	 * @throws Exception
	 */
	public long commitIncident(IncidentInfo incidentInfo, long opId)
			throws Exception;

	/**
	 * 编辑事件时直接提交
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
	public long commitIncident(long incidentId, IncidentInfo incidentInfo,
			long opId) throws Exception;

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
	public long commitIncident(long incidentId, long opId) throws Exception;

	/**
	 * 删除事件，逻辑删除
	 * 
	 * @param incidentId
	 *            事件ID
	 * @param opId
	 *            操作员ID
	 * @throws Exception
	 *             当事件状态不可删除时会也抛出异常
	 */
	public void removeIncident(long incidentId, long opId) throws Exception;

	/**
	 * 顾问补全事件影响度、事件分类、事件优先级三部分内容
	 * 
	 * @param incidentInfo
	 *            事件信息，此逻辑仅关注顾问设置的影响度、分类、优先级三个信息
	 * @param opId
	 *            操作员ID
	 * @throws Exception
	 */
	public void adviserCompleteInfo(IncidentInfo incidentInfo, long opId)
			throws Exception;

	/**
	 * 用户反馈满意度
	 * 
	 * @param incidentInfo
	 *            事件信息，此逻辑仅关注用户设置的满意度信息
	 * @param opId
	 *            操作员ID
	 * @throws Exception
	 */
	public void userSetFeedbackVal(IncidentInfo incidentInfo, long opId)
			throws Exception;

	/**
	 * 顾问关闭事件
	 * 
	 * @param opId
	 *            操作员ID
	 * @throws Exception
	 */
	public void closeIncident(long opId) throws Exception;
}
