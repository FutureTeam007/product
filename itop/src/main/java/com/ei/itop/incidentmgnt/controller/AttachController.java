/**
 * 
 */
package com.ei.itop.incidentmgnt.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ailk.dazzle.common.web.BizController;
import com.ailk.dazzle.exception.BizException;
import com.ailk.dazzle.util.UUIDGenerator;
import com.ei.itop.common.dbentity.IcAttach;
import com.ei.itop.common.util.SessionUtil;
import com.ei.itop.incidentmgnt.service.AttachService;

/**
 * 附件控制器
 */
@Controller
@RequestMapping("/attach")
public class AttachController extends BizController {

	private static final String UPLOAD_SUPPORT = "xls,xlsx,doc,docx,jpg,jpeg,png,rar,zip,tar,7z,log,txt,pdf,mmap";

	private static final Logger log = Logger.getLogger(AttachController.class);

	@Autowired
	private AttachService attachService;

	@RequestMapping("/upload")
	public void upload(MultipartFile uploadFile, Integer objType, Long objId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// 获得文件流信息
		InputStream in = uploadFile.getInputStream();
		// 获得文件名
		String filename = uploadFile.getOriginalFilename();
		// 获得文件类型
		String fileType = filename.substring(filename.lastIndexOf(".") + 1,
				filename.length());
		if (!checkFileType(fileType)) {
			params.put("success", false);
			params.put(
					"message",
					SessionUtil.getRequestContext().getMessage(
							"i18n.upload.FileFormatError")
							+ "：" + UPLOAD_SUPPORT);
			returnJson(response, params);
			return;
		}
		// 符合条件，开始上传
		// 生成一个不重复的UUID作为存储文件名
		String diskName = UUIDGenerator.getNewId();
		try {
			String uploadSubPath = request.getSession().getServletContext()
					.getRealPath("/upload");
			String fileFullPath = uploadSubPath + "/" + diskName + "."
					+ fileType;
			File uploadDirectory = new File(uploadSubPath);
			// 如果目录不存在，则创建
			if (!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(fileFullPath);
				byte[] buffer = new byte[1024];
				int len = 0;
				// 读入流，保存至byte数组
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
			} catch (Exception e) {
				log.error("上传文件过程出现错误：", e);
				params.put("success", false);
				params.put("message", SessionUtil.getRequestContext()
						.getMessage("i18n.upload.UploadFailure"));
			} finally {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				// 保存附件信息到表中
				IcAttach attach = new IcAttach();
				attach.setAttachPath(fileFullPath);
				attach.setAttachName(filename);
				long attachId = attachService.saveAttach(attach);
				params.put("attachId", attachId);
			}
			params.put("success", true);
			params.put("filename", filename);
		} catch (Exception e) {
			log.error("", e);
			params.put("success", false);
			params.put(
					"message",
					SessionUtil.getRequestContext().getMessage(
							"i18n.upload.UploadFailure"));
		}
		returnJson(response, params);
	}

	@RequestMapping("/download")
	public void download(long attachId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO
		IcAttach attach = attachService
				.queryAttachList(new long[] { attachId }).get(0);
		String savePath = attach.getAttachPath();
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(
					new FileInputStream(new File(savePath)));
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(attach.getAttachName().getBytes("gb2312"),
							"ISO8859-1"));
			// 向response中写入文件
			byte[] buffer = new byte[8192];
			int len;
			while ((len = in.read(buffer)) > -1) {
				response.getOutputStream().write(buffer, 0, len);
			}
		} catch (Exception e) {
			log.error("下载文件过程出现错误：", e);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	@RequestMapping("/remove")
	public void remove(long attachId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO
		IcAttach attach = attachService
				.queryAttachList(new long[] { attachId }).get(0);
		String savePath = attach.getAttachPath();
		// 删除物理文件
		File file = new File(savePath);
		if (file.exists()) {
			file.delete();
		}
		// 删除数据
		attachService.deleteAttachByPrimaryId(attachId);
	}

	private boolean checkFileType(String fileType) {
		String[] allowTypes = UPLOAD_SUPPORT.split(",");
		for (String type : allowTypes) {
			if (type.equals(fileType.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

}
