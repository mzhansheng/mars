package com.seekon.mars.wf.engine.model.runtime;

/**
 * 
 * 工作流草稿
 * 表as_wf_draft
 *
 */
public class DraftModel implements java.io.Serializable {

	private static final long serialVersionUID = 221386161948875756L;

	private Long draftId;

	private String draftName;

	private String compoId;

	private String masteTableId;

	private String userId;

	private String userName;

	private String saveTime;

	private String status;

	public String getCompoId() {
		return compoId;
	}

	public void setCompoId(String compoId) {
		this.compoId = compoId;
	}

	public Long getDraftId() {
		return draftId;
	}

	public void setDraftId(Long draftId) {
		this.draftId = draftId;
	}

	public String getDraftName() {
		return draftName;
	}

	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}

	public String getMasteTableId() {
		return masteTableId;
	}

	public void setMasteTableId(String masteTableId) {
		this.masteTableId = masteTableId;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
