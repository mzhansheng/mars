package com.seekon.mars.wf.engine.util;

import java.io.Serializable;

public final class ActivityInstanceState implements Serializable {

	private static final long serialVersionUID = -5778320117727095605L;

	public static final int OPEN_NOTRUNNING_INT = 0;// 流程未启动

	public static final int OPEN_SUSPENDED_INT = 1;// 挂起

	public static final int OPEN_RUNNING_INT = 2;// 运行

	public static final int CLOSED_ABORTED_INT = 3;// 非正常终止

	public static final int CLOSED_TERMINATED_INT = 4;// 终止

	public static final int CLOSED_COMPLETED_INT = 5; // 完成

	public static final ActivityInstanceState OPEN_NOTRUNNING = new ActivityInstanceState(
			OPEN_NOTRUNNING_INT);

	public static final ActivityInstanceState OPEN_SUSPENDED = new ActivityInstanceState(
			OPEN_SUSPENDED_INT);

	public static final ActivityInstanceState OPEN_RUNNING = new ActivityInstanceState(
			OPEN_RUNNING_INT);

	public static final ActivityInstanceState CLOSED_ABORTED = new ActivityInstanceState(
			CLOSED_ABORTED_INT);

	public static final ActivityInstanceState CLOSED_TERMINATED = new ActivityInstanceState(
			CLOSED_TERMINATED_INT);

	public static final ActivityInstanceState CLOSED_COMPLETED = new ActivityInstanceState(
			CLOSED_COMPLETED_INT);

	private int _state;

	public ActivityInstanceState(int state) {
		this._state = state;
	}

	public int getValue() {
		return _state;
	}

	public boolean equals(Object obj) {
		if (obj instanceof ActivityInstanceState) {
			return ((ActivityInstanceState) obj).getValue() == _state;
		}
		return false;
	}
}
