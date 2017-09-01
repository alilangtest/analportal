package byit.aladdin.workBook.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import byit.tableausubscribe.tab.bean.SubscribeType;

public class XmlTablesMain {
	// ==============================Fields========================================
	private Long id;
	private String workbookId;//工作簿id
	private Integer times;//更新次数
	private Date execTime;//执行工作簿刷新的日期
	private Date createdTime;//创建时间
	private Date updatedTime;//更新时间
	private String dispatchState;  //调度状态
	private String taskState;		//任务状态
	private String refreshState;	//刷新状态
	private String creator;			//创建者
	private String updator;			//更新者
	private String taskRefTime;		//任务刷新时间
	private Date apiExecTime;		//api执行时间
	private Date workbookRefTime;	//工作薄刷新时间
	private String refreshTime;  //刷新时间
	private String refreshFreq;   //刷新频率
	private Date nextTime;	  //下次的运行时间

	// ==============================Methods==========================================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkbookId() {
		return workbookId;
	}

	public void setWorkbookId(String workbookId) {
		this.workbookId = workbookId;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getDispatchState() {
		return dispatchState;
	}

	public void setDispatchState(String dispatchState) {
		this.dispatchState = dispatchState;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getRefreshState() {
		return refreshState;
	}

	public void setRefreshState(String refreshState) {
		this.refreshState = refreshState;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getTaskRefTime() {
		return taskRefTime;
	}

	public void setTaskRefTime(String taskRefTime) {
		this.taskRefTime = taskRefTime;
	}

	public Date getWorkbookRefTime() {
		return workbookRefTime;
	}

	public void setWorkbookRefTime(Date workbookRefTime) {
		this.workbookRefTime = workbookRefTime;
	}
	

	public String getRefreshFreq() {
		return refreshFreq;
	}

	public void setRefreshFreq(String refreshFreq) {
		this.refreshFreq = refreshFreq;
	}

	public String getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}



	private  SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private  SimpleDateFormat daySdf = new SimpleDateFormat("HH:mm");

	public Date getNextTime() {
		setNextTime();
		return nextTime;
	}
	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}
	//设置下次执行时间，当执行后会重新设置 以每天几点发送为例
	public void setNextTime() {
		//获取当前时间
		Calendar current =Calendar.getInstance();
		//获取发送时间
		Calendar sendCl =Calendar.getInstance();
		try {
			if(SubscribeType.once.getIndex().equals(refreshFreq)){
				sendCl.setTime(dateSdf.parse(refreshTime));
				//发送时间是否已过去
				if(current.getTime().after(sendCl.getTime())){
					nextTime= null;
				}else{//发送时间在当前时间后面--才有机会发送
					nextTime= sendCl.getTime();
				}
			}else if(SubscribeType.day.getIndex().equals(refreshFreq)){
				//发送时间在当前时间后面
				sendCl.setTime(daySdf.parse(refreshTime));
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				sendCl.set(Calendar.DAY_OF_YEAR, current.get(Calendar.DAY_OF_YEAR));
				//发送时间在当前时间之前 如发送时间2016-12-22 14：30    当前时间为2016-12-22 17：00则发送时间+1天
				if(current.getTime().after(sendCl.getTime())){
					//加一天
					sendCl.add(Calendar.DATE,1);
				}
				//获取发送时间赋值给下一次的发送时间
				nextTime= sendCl.getTime();
			}else if(SubscribeType.week.getIndex().equals(refreshFreq)){
				//发送时间在当前时间后面
				String[] str = refreshTime.split("_");
				sendCl.setTime(daySdf.parse(str[1]));
				
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				sendCl.set(Calendar.WEEK_OF_YEAR, current.get(Calendar.WEEK_OF_YEAR));
				sendCl.set(Calendar.DAY_OF_WEEK, Integer.parseInt(str[0]));
				//SimpleDateFormat sdf = new SimpleDateFormat("E HH:mm");
				//refreshTime = sdf.format(sendCl.getTime());
				//发送时间在当前时间之前
				if(current.getTime().after(sendCl.getTime())){
					//加7天
					sendCl.add(Calendar.DATE,7);
				}
				nextTime= sendCl.getTime();
			}else if(SubscribeType.month.getIndex().equals(refreshFreq)){
				SimpleDateFormat monthSdf = new SimpleDateFormat("dd HH:mm");
				//发送时间在当前时间后面
				sendCl.setTime(monthSdf.parse(refreshTime));
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				sendCl.set(Calendar.MONTH, current.get(Calendar.MONTH));
				//发送时间在当前时间之前
				if(current.getTime().after(sendCl.getTime())){
					//加1月
					sendCl.add(Calendar.MONTH,1);
				}
				nextTime= sendCl.getTime();
			}else if(SubscribeType.year.getIndex().equals(refreshFreq)){
				SimpleDateFormat yearSdf = new SimpleDateFormat("MM-dd HH:mm");
				//发送时间在当前时间后面
				sendCl.setTime(yearSdf.parse(refreshTime));
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				//发送时间在当前时间之前
				if(current.getTime().after(sendCl.getTime())){
					//加1年
					sendCl.add(Calendar.YEAR,1);
				}
				nextTime= sendCl.getTime();
			}
		} catch (ParseException e) {
			e.printStackTrace();
			nextTime = null;
		} catch (Exception e) {
			e.printStackTrace();
			nextTime = null;
		}
	}

	public Date getApiExecTime() {
		return apiExecTime;
	}

	public void setApiExecTime(Date apiExecTime) {
		this.apiExecTime = apiExecTime;
	}

	public Date getExecTime() {
		return execTime;
	}

	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}
	
}
