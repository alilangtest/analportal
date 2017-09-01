package byit.tableausubscribe.tab.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import byit.aladdin.dataIndex.util.ByitConfig;
import byit.aladdin.dataIndex.util.PageUtil;
import byit.tableausubscribe.tab.bean.ExcelColumnSubscribe;
import byit.tableausubscribe.tab.bean.ExcelSubscribe;
import byit.tableausubscribe.tab.db.DB;
import byit.tableausubscribe.tab.db.DbSql;
import byit.utils.Tools;

@Service
@Transactional
public class ExcelOper {
	private static final Logger logger = Logger.getLogger(ExcelOper.class);
	//导入数据
		public static Map<String,String> exportData(String filePath,ExcelSubscribe excelSubscribe, Integer pageSize) 
				throws Exception{
			Map<String,String> retMap=new HashMap<String, String>();
			Connection conn = null;
			if (conn == null) {
				try {
					conn =DB.getDmConn();//  DBConnection.getConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			Boolean isCreateExel=false;
			long dealExcelStartTime=0l;//开始写入excel的时间
			long confDataStartTime=0l;
			String excelFilePath="";
			Vector<ExcelSubscribe> listExcelSubscribe = new Vector<ExcelSubscribe>();
			listExcelSubscribe.add(excelSubscribe);
			if(listExcelSubscribe != null && listExcelSubscribe.size()>0){
				//拆分tableId,tableName,取出每一个表的英文名和中文名
				String tabId = excelSubscribe.getTableId();
				String tabName = excelSubscribe.getTableName();
				String[] tabIdList = tabId.split(",");//表名
				String[] tabNameList = tabName.split(",");//表中文名
				String isCheckedString = "";
				String addDateString = "";
				
				//日期
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String fileDate = sdf.format(now);

				// 导出到Excel
				//XSSFWorkbook wbExcel = new XSSFWorkbook();
				//SXSSFWorkbook wbExcel = new SXSSFWorkbook(100);
				Workbook wbExcel = new SXSSFWorkbook(100);
				File excel = new File(filePath+excelSubscribe.getMailTitleExcel()+"_"+fileDate+".xlsx");
				if(excel.exists()){
					excel.delete();
				}
				
				PushExcelColumnDao excelColumnDao=new PushExcelColumnDao();
				List<ExcelColumnSubscribe> excelColSubscribeList=excelColumnDao.getExcelSubscribeById(tabId);
				String selCond="";
				for (int i = 0; i < excelColSubscribeList.size(); i++) {
					//String checkedColumnId=excelColSubscribeList.get(i).getCheckedColumnId();
					String isChecked=excelColSubscribeList.get(i).getIsChecked();
					isCheckedString = isCheckedString+"~"+isChecked;
					String addDate=excelColSubscribeList.get(i).getAddDate();
					selCond=excelColSubscribeList.get(i).getSelCond();
					if(Tools.isNotEmpty(addDate)){
						addDateString = addDateString+"~"+addDate;
					}
				}
				if(Tools.isNotEmpty(addDateString)){
					addDateString = addDateString.substring(1,addDateString.length());
				}
				
				isCheckedString = isCheckedString.substring(1,isCheckedString.length());
				
				OutputStream ouputStream = new FileOutputStream(filePath+excelSubscribe.getMailTitleExcel()+"_"+fileDate+".xlsx");
				excelFilePath=filePath+excelSubscribe.getMailTitleExcel()+"_"+fileDate+".xlsx";
				
				
				String[] isCheckedArray =  isCheckedString.split("~");
				String[] addDateArray = addDateString.split("~");
				
				
				//表名的循环获取生成excel
				for(int i=0;i<tabIdList.length;i++){
					logger.info("开始导出表：" + tabIdList[i]);
					//记录每个导出表的当前行数
					Integer countRow = 0;
					List<Map<String, String>> excelList = new ArrayList<Map<String, String>>();//excel表
					Map<String, String> excelMap = new HashMap<String, String>();//excel表
					List<List<String>>excelDatas = new ArrayList<List<String>>();//excel表
					List<List<String>>excelTypes = new ArrayList<List<String>>();//excel表
					List<String>titleRow = new ArrayList<String>();//表头
					List<String>columnRow = new ArrayList<String>();//字段
					List<String>dataType = new ArrayList<String>();//字段
					
					
					//表头
					titleRow.add(tabNameList[i]);
					//excelDatas.add(titleRow);
					excelTypes.add(titleRow);
					//表中文名：tableCnName；表英文名：tableEnName
					//表字段中文名：columnCnName；表字段英文名：columnEnName
					String excelSql="SELECT A.DATA_TYPE as columnDataType,A.TABLE_NAME as tableEnName," +
							"B.COMMENTS as  tableCnName, " +
							"A.COLUMN_NAME as columnEnName," +
							"C.COMMENTS as columnCnName " +
							"FROM USER_TAB_COLUMNS A " +
							"INNER JOIN USER_TAB_COMMENTS B ON A.TABLE_NAME = B.TABLE_NAME " +
							"INNER JOIN USER_COL_COMMENTS C ON A.TABLE_NAME = C.TABLE_NAME AND A.COLUMN_NAME = C.COLUMN_NAME " +
							"WHERE A.TABLE_NAME = '"+tabIdList[i]+"'"+
							" ORDER BY  A.COLUMN_ID ";
					excelList = DbSql.findNativeSQL(conn, excelSql, null);
					//表中文字段
					for(int y=0;y<excelList.size();y++){
						excelMap = /*(Map)*/ excelList.get(y);
						//所选字段的匹配
							String[] isCheckedColumn = isCheckedArray[i].split(",");
							for(int z=0;z<isCheckedColumn.length;z++){
								if(isCheckedColumn[z].equals(String.valueOf(excelMap.get("columnenname")))){
									//System.out.println("-------tabIdList.length:"+tabIdList.length+"------------tabIdList[i]"+tabIdList[i]+"----------isCheckedArray[x]："+isCheckedArray[i]+"----------isCheckedColumn[z]："+isCheckedColumn[z]+"----------字段相等的--------"+String.valueOf(excelMap.get("columnenname")));
									if(excelMap.get("columncnname") != null){
										//中文字段不为null
										columnRow.add(String.valueOf(excelMap.get("columncnname")));
									}else{
										//当中文字段为null时用英文字段填写
										columnRow.add(String.valueOf(excelMap.get("columnenname")));
									}
								}
							}
						
						dataType.add(String.valueOf(excelMap.get("columndatatype")));
					}
					excelDatas.add(columnRow);
					excelTypes.add(dataType);
					
					SXSSFSheet sheet = (SXSSFSheet) wbExcel.createSheet(tabNameList[i]);
					//循环中第一个样式
					CellStyle stringCellStyle_title = wbExcel.createCellStyle();
					stringCellStyle_title.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);		
					stringCellStyle_title.setAlignment(XSSFCellStyle.ALIGN_CENTER);
					Font font_title = wbExcel.createFont();
					font_title.setColor(HSSFColor.ROYAL_BLUE.index);
					stringCellStyle_title.setFont(font_title);
					//循环中第二个样式
					CellStyle stringCellStyle_tag = wbExcel.createCellStyle();
					Font font_tag = wbExcel.createFont();
					font_tag.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗
					font_tag.setFontName("黑体");//字体						
					stringCellStyle_tag.setFont(font_tag);
					stringCellStyle_tag.setFillForegroundColor(HSSFColor.ORANGE.index);// 设置背景色
					stringCellStyle_tag.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
					//循环中第三个样式
					CellStyle stringCellStyle_info = wbExcel.createCellStyle();									
					DataFormat format_info = wbExcel.createDataFormat();
					stringCellStyle_info.setFillForegroundColor(HSSFColor.ROSE.index);// 设置背景色
					stringCellStyle_info.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
					Font font_info = wbExcel.createFont();
					stringCellStyle_info.setFont(font_info);
					
					String sql = "select count(*) from " + tabIdList[i];
					int rowCount = DbSql.findNativeSQLRows(conn, sql);
					PageUtil pageUtil = new PageUtil();
					pageUtil.setPageSize(pageSize);
					pageUtil.setTotleNumber(rowCount);
					//生成标题列
					SXSSFRow dataRowExcel = (SXSSFRow) sheet.createRow(0);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, excelDatas.get(0).size()-1));
					SXSSFCell dataCell_title = (SXSSFCell) dataRowExcel.createCell(0);
					dataCell_title.setCellStyle(stringCellStyle_title);
					dataCell_title.setCellValue(new XSSFRichTextString(tabNameList[i]));
					
					SXSSFRow dataRowExcel2 = (SXSSFRow) sheet.createRow(1);
					//生成每列列名称
					for (int j = 0; j < excelDatas.get(0).size(); j++) {
						SXSSFCell dataCell_tag = (SXSSFCell) dataRowExcel2.createCell(j);
						dataCell_tag.setCellStyle(stringCellStyle_tag);
						dataCell_tag.setCellType(XSSFCell.CELL_TYPE_STRING);
						dataCell_tag.setCellValue(new XSSFRichTextString(excelDatas.get(0).get(j)));
					}
					countRow = 1;
					for (int a = 0; a < pageUtil.getTotlePage(); a++) {
						//当前页数
						pageUtil.setCurrentPageNumber(a+1);
						List<Map<String, String>> columnlList = new ArrayList<Map<String, String>>();//字段
						List<List<String>> Datas = new ArrayList<List<String>>();
						//取字段的信息
						//数据信息
						String date_dt = "";
						String sqlColumn="select ";
						for(int z=0;z<excelList.size();z++){
							excelMap = excelList.get(z);
							//获取统计日期的字段
							//所选字段的匹配
							String[] isCheckedColumn = isCheckedArray[i].split(",");
							for(int w=0;w<isCheckedColumn.length;w++){
								if(isCheckedColumn[w].equals(String.valueOf(excelMap.get("columnenname")))){
									if(Tools.isNotEmpty(addDateArray[i])){
										//配置了筛选日期 强制第一个字段是 记录日期
										if(z==0){
											date_dt = String.valueOf(excelMap.get("columnenname")).toLowerCase();
										}
									}
									sqlColumn = sqlColumn+String.valueOf(excelMap.get("columnenname")).toLowerCase()+",";//英文字段
								}
							}
						}
						//截取掉最后的“,”
						sqlColumn += "rownum row_num";
						if(addDateArray[i]!=null && !"null".equals(addDateArray[i])&& !"".equals(addDateArray[i])){
							//有筛选日期
							sqlColumn=sqlColumn+" from "+tabIdList[i]+" a where to_char(a."+date_dt+",'yyyy-mm-dd' ) >= to_char(sysdate-"+addDateArray[i]+",'yyyy-mm-dd') ";
							if(Tools.isNotEmpty(selCond)){
								sqlColumn=sqlColumn+" and "+selCond;
							}
							sqlColumn=sqlColumn+ " order by a."+date_dt+"  desc";
						}else{
							//无筛选日期
							sqlColumn=sqlColumn+" from "+tabIdList[i]+" a ";
							if(Tools.isNotEmpty(selCond)){
								sqlColumn=sqlColumn+"where 1=1 and "+selCond;
							}
						}
						logger.info("分页查询数据开始：start:" + (pageUtil.getStratNumber() + 1) + ", end:" + pageUtil.getEndNumber());
						String sqls = "select * from (" + sqlColumn + ") b where b.row_num between "+(pageUtil.getStratNumber() + 1)+" and " + pageUtil.getEndNumber();
						logger.info("查询数据条数的sql为：" + sqls);
						//查询出写入excel的数据总量
						columnlList = DbSql.findNativeSQL(conn, sqls, null);
						logger.info("数据库表中有"+columnlList.size()+"条数据");
						//如果数据量大于600000,不生成excel，不发送邮件
						if(columnlList.size()>ByitConfig.EXP_EXCEL_MAXSIZE){
							logger.info("数据量大于"+ByitConfig.EXP_EXCEL_MAXSIZE+"，不生成excel，直接发邮件告诉使用者");
							retMap.put("info", "抱歉！"+tabIdList[i]+"表的数据量大于"+ByitConfig.EXP_EXCEL_MAXSIZE+"条，请筛选查询条件，否则无法发送！");
						}else if(columnlList.size()==0){
							logger.info("数据量为0。数据未就位，不生成excel，应该给使用者发送提示邮件");
							retMap.put("sizeZero", columnlList.size()+"");
						}else{//否则生成excel，并发送邮件
							retMap.put("path", excelFilePath);
							logger.info("数据量小于"+ByitConfig.EXP_EXCEL_MAXSIZE+"，准备生成excel");
							isCreateExel=true;
							confDataStartTime = System.currentTimeMillis();//获取数据整理开始时间
							logger.info("开始查询并整理数据。当前时间"+Tools.getCurrFormatTimeGen());
							for(int x=0;x<columnlList.size();x++){
								columnRow = new ArrayList<String>();
								for(int y=0;y<excelList.size();y++){
									Map<String, String> columnMap = excelList.get(y);
									//所选字段的匹配
									String[] isCheckedColumn = isCheckedArray[i].split(",");
									for(int z=0;z<isCheckedColumn.length;z++){
										if(isCheckedColumn[z].equals(String.valueOf(columnMap.get("columnenname")))){
											//获取字段名
											String getColumn = String.valueOf(columnMap.get("columnenname")).toLowerCase();
											Map<String, String> dataMap =  columnlList.get(x);
											//根据字段名获取值
											columnRow.add((String.valueOf(dataMap.get(getColumn))==null||String.valueOf(dataMap.get(getColumn)).equals("null"))?"": String.valueOf(dataMap.get(getColumn)).toString());
										}
									}
								}
								Datas.add(columnRow);
							}
							long dataConfTime = System.currentTimeMillis();//获取数据整理结束时间
							logger.info("查询并整理数据完成,耗时："+(dataConfTime-confDataStartTime)+"ms");
							
							dealExcelStartTime = System.currentTimeMillis();//获取结束时间
							logger.info("写入临时的excel。当前时间"+Tools.getCurrFormatTimeGen()+",写入excel的计算时间："+dealExcelStartTime);
							for (int q = 0; q < Datas.size(); q++) {
								countRow ++;
								logger.info("目前是多少行--------：" + countRow);
								//System.out.println("目前是多少行--------：" + countRow);
								SXSSFRow data1RowExcel = (SXSSFRow) sheet.createRow(countRow);
								List<String> data = Datas.get(q);
								List<String> type = excelTypes.get(1);	
								for (short j = 0; j < data.size(); j++) {
										SXSSFCell dataCell_info = (SXSSFCell) data1RowExcel.createCell(j);
										
										if("NUMBER".equals(type.get(j))){
											stringCellStyle_info.setDataFormat(format_info.getFormat("0.00"));
										}else {
											dataCell_info.setCellType(XSSFCell.CELL_TYPE_STRING);
										}
										dataCell_info.setCellValue(new XSSFRichTextString(data.get(j)));
								}
							}
							
						}
						columnlList = null;
					}
					excelList = null;
					excelMap = null;
					excelDatas = null;
					excelTypes = null;
					titleRow = null;
					columnRow = null;
					dataType = null;
				}
				if(isCreateExel){
					logger.info("向磁盘中excel准备写入。临时写入excel到现在花费时长："+(System.currentTimeMillis()-dealExcelStartTime));
					wbExcel.write(ouputStream);
					logger.info("写入excel完成,写入excle耗时："+(System.currentTimeMillis()-dealExcelStartTime)+
								"整理数据并写入excel耗时："+(System.currentTimeMillis()-confDataStartTime));
					logger.info("导出测试表信息成功！");
					ouputStream.flush();
					ouputStream.close();
					wbExcel = null;
					logger.info("关闭流！");
				}
				
				
			}
			return retMap;
		}
}
