package com.seekon.smartclient.framework;


public interface Application {
		
	/**
	 * 返回应用程序名称
	 * 
	 * @return String
	 */
	public String getApplicationName();

	/**
	 * 返回应用程序版本号
	 * 
	 * @return String
	 */
	public abstract String getVersion();

	/**
	 * 返回应用程序标题
	 * 
	 * @return String
	 */
	public abstract String getTitle();

	/**
	 * 设置应用程序标题
	 * 
	 * @param title
	 *          String
	 */
	public abstract void setTitle(String title);

	/**
	 * 终止程序
	 */
	public abstract void terminate();

	/**
	 * 主框架窗口
	 * 
	 * @return MainFrame
	 */
	public abstract MainFrame getMainFrame();

	/**
	 * 初始化应用程序
	 */
	public abstract void init();

	/**
	 * 切换主题风格
	 */
	public void changeLookAndFeel(String lookAndFeelClassName) throws Exception;
	
	/**
	 * 应用程序销毁
	 */
	public void destroy();

	/**
	 * 运行程序为Application
	 */
	public abstract void run();
	
	/**
	 * 重新加载
	 */
	public void reload();
}
