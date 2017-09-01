package byit.tableausubscribe.tab.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import byit.tableausubscribe.common.util.Constants;





public class ExcelSubscribe {

	
	
	
	public String getScreening() {
		return screening;
	}
	public void setScreening(String screening) {
		this.screening = screening;
	}
	@Override
	public String toString() {
		return "ExcelSubscribe [mailTitleExcel=" + mailTitleExcel
				+ ", tableId=" + tableId + ", tableName=" + tableName
				+ ", screening=" + screening + ", sendTypeExcel="
				+ sendTypeExcel + ", sendTimeExcel=" + sendTimeExcel
				+ ", sendStateExcel=" + sendStateExcel + ", mailListExcel="
				+ mailListExcel + ", sendTypeNameExcel=" + sendTypeNameExcel
				+ ", sendTimeNameExcel=" + sendTimeNameExcel
				+ ", sendResultListExcel=" + sendResultListExcel
				+ ", nextTime=" + nextTime + ", excelMails=" + excelMails
				+ ", dateSdf=" + dateSdf + ", daySdf=" + daySdf + "]";
	}

	private String id;
	private String seqNum;//序号
	private String mailTitleExcel;
	private String tableId;//仪表板(多个逗号隔开)
	private String tableName;//仪表板名称(逗号隔开)
	private String screening;//筛选条件
	//订阅规则和订阅时间顺序不能乱
	private String sendTypeExcel;//订阅规则 
	private String sendTimeExcel;//订阅发送时间
	private String sendStateExcel = Constants.SENDDNS;//"未发送";//发送状态
	private List<String> mailListExcel;//订阅人
	//-------------以上与配置文件匹配--------------------------//
	private String sendTypeNameExcel;//订阅规则 
	private String sendTimeNameExcel;//订阅发送时间
	
	//邮件返回结果
	private List<SendResult> sendResultListExcel;
	//配置文件不需要配置，下次执行时间:为了减少遍历
	private Date nextTime;
	//配置文件不需要配置
	private String excelMails;
	
	private String _addDate;//筛选天数，仅为了返显
	private String _selCond;//筛选条件，仅为了返显
	private String isSendProp;//是否发送提示邮件
	private String isChecked;//选中字段，仅为了返显
	/*
	 * 按照用户的规则，邮件当天是否发送，如未发送，需要在 tempSendTime存储当天下次发送的时间，如已发送，tempSendTime可不存数据
	 * true表示当天发送成功，false表示当天发送失败
	 */
	private String isTodaySend;
	/*
	 * 临时发送时间
	 * 有些报表在某天的指定时间内数据未就位，所以报表为通过邮件发送。但数据就位后应该发送出去，这个字段就表示这些数据下次发送的时间
	 */
	private Date tempSendTime;
	private String ptempSendTime;
	private String userId;//操作员
	private String subTableId;//具体某个excel表，如果有多个excel表同时存在，会在tableId字段全部显示，在subTableId字段分别显示
	private String tables;
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getIsSendProp() {
		return isSendProp;
	}
	public void setIsSendProp(String isSendProp) {
		this.isSendProp = isSendProp;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSendTypeExcel() {
		return sendTypeExcel;
	}
	public void setSendTypeExcel(String sendTypeExcel) {
		this.sendTypeExcel = sendTypeExcel;
	}
	public String getSendTimeExcel() {
		return sendTimeExcel;
	}
//	public void setSendTimeExcel(String sendTimeExcel) {
//		this.sendTimeExcel = sendTimeExcel;
//	}
	public void setSendTimeExcel(String sendTimeExcel) {
		this.sendTimeExcel = sendTimeExcel;
		setNextTime();
	}
	public String getSendStateExcel() {
		return sendStateExcel;
	}
	public void setSendStateExcel(String sendStateExcel) {
		this.sendStateExcel = sendStateExcel;
	}
	public List<String> getMailListExcel() {
		return mailListExcel;
	}
	public void setMailListExcel(List<String> mailListExcel) {
		this.mailListExcel = mailListExcel;
	}
	public String getSendTypeNameExcel() {
		return sendTypeNameExcel;
	}
	public void setSendTypeNameExcel(String sendTypeNameExcel) {
		this.sendTypeNameExcel = sendTypeNameExcel;
	}
	public String getSendTimeNameExcel() {
		return sendTimeNameExcel;
	}
//	public void setSendTimeNameExcel(String sendTimeNameExcel) {
//		this.sendTimeNameExcel = sendTimeNameExcel;
//		setNextTime();
//	}
	public List<SendResult> getSendResultListExcel() {
		return sendResultListExcel;
	}
	public void setSendResultListExcel(List<SendResult> sendResultListExcel) {
		this.sendResultListExcel = sendResultListExcel;
	}
	public String getExcelMails() {
		excelMails ="";
		if(mailListExcel!=null){
			for(String m:mailListExcel){
				excelMails+=","+m;
			}
			if(!"".equals(excelMails)){
				excelMails=excelMails.substring(1);
			}
		}
		/*for(String m:mailListExcel){
			excelMails+=","+m;
		}
		if(!"".equals(excelMails)){
			excelMails=excelMails.substring(1);
		}*/
		return excelMails;
	}
	public void setExcelMails(String excelMails) {
		this.excelMails = excelMails;
		mailListExcel = new ArrayList<>();
		for(String mail:excelMails.split(",")){
			mailListExcel.add(mail);
		}
	}



	
	
	
	public Date getNextTime() {
		return nextTime;
	}
	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}
	


	private  SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private  SimpleDateFormat daySdf = new SimpleDateFormat("HH:mm");
	//设置下次执行时间，当执行后会重新设置
	public void setNextTime() {
		
		Calendar current =Calendar.getInstance();
		Calendar sendCl =Calendar.getInstance();
		try {
			sendTimeNameExcel = sendTimeExcel;
			if(SubscribeExcelType.once.getIndex().equals(sendTypeExcel)){
				sendCl.setTime(dateSdf.parse(sendTimeExcel));
				//发送时间是否已过去
				if(current.getTime().after(sendCl.getTime())){
					nextTime= null;
				}else{//发送时间在当前时间后面--才有机会发送
					nextTime= sendCl.getTime();
				}
			}else if(SubscribeExcelType.day.getIndex().equals(sendTypeExcel)){
				//发送时间在当前时间后面
				sendCl.setTime(daySdf.parse(sendTimeExcel));
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				sendCl.set(Calendar.DAY_OF_YEAR, current.get(Calendar.DAY_OF_YEAR));
				//发送时间在当前时间之前
				if(current.getTime().after(sendCl.getTime())){
					//加一天
					sendCl.add(Calendar.DATE,1);
				}
				nextTime= sendCl.getTime();
			}else if(SubscribeExcelType.week.getIndex().equals(sendTypeExcel)){
		
				//发送时间在当前时间后面
				String[] str = sendTimeExcel.split("_");
				sendCl.setTime(daySdf.parse(str[1]));
				String[] dayx = str[0].split(",");
				
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				sendCl.set(Calendar.WEEK_OF_YEAR, current.get(Calendar.WEEK_OF_YEAR));
				
				// 获取今天是星期几
				Date date = new Date();
				SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
				String da = dateFm.format(date);
				System.out.println("今天星期几"+da);
				if (da.equals("星期日")) {
					da = "1";
				} else if (da.equals("星期一")) {
					da = "2";
				} else if (da.equals("星期二")) {
					da = "3";
				} else if (da.equals("星期三")) {
					da = "4";
				} else if (da.equals("星期四")) {
					da = "5";
				} else if (da.equals("星期五")) {
					da = "6";
				} else if (da.equals("星期六")) {
					da = "7";
				}
				
				int countWeek = 0;
				int countWeek0 = 0;
				int countWeek1 = 0;
				int countWeek2 = 0;
				int countWeek3 = 0;
				int countWeek4 = 0;
				int countWeek5 = 0;	
				
				// 判断今天是否等于所选的发送星期
				for (int y = 0; y < dayx.length; y++) {
					
					// 选定的星期等于今天
					if (dayx[y].equals(da)) {
						
						sendCl.set(Calendar.DAY_OF_WEEK, Integer.parseInt(dayx[y]));
				
						
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("E HH:mm");
				sendTimeNameExcel = sdf.format(sendCl.getTime());
		
				//发送时间在当前时间之前 当前时间大于发送时间  已经发送完了的意思
				if(current.getTime().after(sendCl.getTime())){
					//加7天
					//sendCl.add(Calendar.DATE,7);
					for (int y = 0; y < dayx.length; y++) {
						
						// 选定的星期等于今天
						if (dayx[y].equals(da)) {
						
							switch (dayx.length) {
							case 1:
								// 选1天
								sendCl.add(Calendar.DATE, 7);
								break;
							case 2:
								// 选2天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek0 + 7 - countWeek1;
									sendCl.add(Calendar.DATE, countWeek);
								}
								break;
							case 3:
							
								// 选3天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek = countWeek2 - countWeek1;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 2) {
									// 下标为2
									countWeek0 = Integer.parseInt(dayx[0]);
								
									countWeek2 = Integer.parseInt(dayx[2]);
						
									countWeek = countWeek0 + 7 - countWeek2;
					
								}
								break;
							case 4:
								// 选4天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek = countWeek2 - countWeek1;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 2) {
									// 下标为2
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek = countWeek3 - countWeek2;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 3) {
									// 下标为3
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek = countWeek0 + 7 - countWeek3;
									sendCl.add(Calendar.DATE, countWeek);
								}
								break;
							case 5:
								// 选5天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek = countWeek2 - countWeek1;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 2) {
									// 下标为2
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek = countWeek3 - countWeek2;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 3) {
									// 下标为3
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek4 = Integer.parseInt(dayx[4]);
									countWeek = countWeek4 - countWeek3;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 4) {
									// 下标为4
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek4 = Integer.parseInt(dayx[4]);
									countWeek = countWeek0 + 7 - countWeek4;
									sendCl.add(Calendar.DATE, countWeek);
								}
								break;
							case 6:
								// 选6天
								if (y == 0) {
									// 下标为0
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek = countWeek1 - countWeek0;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 1) {
									// 下标为1
									countWeek1 = Integer.parseInt(dayx[1]);
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek = countWeek2 - countWeek1;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 2) {
									// 下标为2
									countWeek2 = Integer.parseInt(dayx[2]);
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek = countWeek3 - countWeek2;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 3) {
									// 下标为3
									countWeek3 = Integer.parseInt(dayx[3]);
									countWeek4 = Integer.parseInt(dayx[4]);
									countWeek = countWeek4 - countWeek3;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 4) {
									// 下标为4
									countWeek4 = Integer.parseInt(dayx[4]);
									countWeek5 = Integer.parseInt(dayx[5]);
									countWeek = countWeek5 - countWeek4;
									sendCl.add(Calendar.DATE, countWeek);
								} else if (y == 5) {
									// 下标为5
									countWeek0 = Integer.parseInt(dayx[0]);
									countWeek5 = Integer.parseInt(dayx[5]);
									countWeek = countWeek0 + 7 - countWeek5;
									sendCl.add(Calendar.DATE, countWeek);
								}
								break;
							case 7:
								// 选7天
								sendCl.add(Calendar.DATE, 1);
								break;
							}
						}
					}			
					
				}
			
				nextTime= sendCl.getTime();

			}else if(SubscribeExcelType.month.getIndex().equals(sendTypeExcel)){
				SimpleDateFormat monthSdf = new SimpleDateFormat("dd HH:mm");
				//发送时间在当前时间后面
				sendCl.setTime(monthSdf.parse(sendTimeExcel));
				sendCl.set(Calendar.YEAR, current.get(Calendar.YEAR));
				sendCl.set(Calendar.MONTH, current.get(Calendar.MONTH));
				//发送时间在当前时间之前
				if(current.getTime().after(sendCl.getTime())){
					//加1月
					sendCl.add(Calendar.MONTH,1);
				}
				nextTime= sendCl.getTime();
			}else if(SubscribeExcelType.year.getIndex().equals(sendTypeExcel)){
				SimpleDateFormat yearSdf = new SimpleDateFormat("MM-dd HH:mm");
				//发送时间在当前时间后面
				sendCl.setTime(yearSdf.parse(sendTimeExcel));
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
	public String getMailTitleExcel() {
		if(mailTitleExcel==null||"".equals(mailTitleExcel)){
			mailTitleExcel="无主题";
		}
		return mailTitleExcel;
	}
	public void setMailTitleExcel(String mailTitleExcel) {
		this.mailTitleExcel = mailTitleExcel;
	}
	public Date getTempSendTime() {
		return tempSendTime;
	}

	public void setTempSendTime(Date tempSendTime) {
		this.tempSendTime = tempSendTime;
	}
	
	public String getIsTodaySend() {
		return isTodaySend;
	}
	public void setIsTodaySend(String isTodaySend) {
		this.isTodaySend = isTodaySend;
	}
	public String getPtempSendTime() {
		return ptempSendTime;
	}
	public void setPtempSendTime(String ptempSendTime) {
		this.ptempSendTime = ptempSendTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String get_addDate() {
		return _addDate;
	}
	public void set_addDate(String _addDate) {
		this._addDate = _addDate;
	}
	public String get_selCond() {
		return _selCond;
	}
	public void set_selCond(String _selCond) {
		this._selCond = _selCond;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getSubTableId() {
		return subTableId;
	}
	public void setSubTableId(String subTableId) {
		this.subTableId = subTableId;
	}
	public String getTables() {
		return tables;
	}
	public void setTables(String tables) {
		this.tables = tables;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
