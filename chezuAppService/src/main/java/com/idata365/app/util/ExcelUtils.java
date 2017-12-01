package com.idata365.app.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtils {
	public static  File saveToExcelGps(List<Map<String,Object>> gpsList){
		File file=null;
		try {
			WritableWorkbook wwb = null;
			
			// 创建可写入的Excel工作簿
			String fileName = "gps.xls";
			file=new File(fileName);
			//以fileName为文件名来创建一个Workbook
			wwb = Workbook.createWorkbook(file);

			// 创建工作表
			WritableSheet ws = wwb.createSheet("gps", 0);
			ws.setColumnView(0,20);
			ws.setColumnView(1,20);
			ws.setColumnView(2,20);
			ws.setColumnView(3,20);
			ws.setColumnView(4,20);
			
			Label a= new Label(0, 0, "lng");
			Label b= new Label(1, 0, "lat");
			Label c= new Label(2, 0, "t");
			Label d= new Label(3, 0, "s");
			Label e= new Label(4, 0, "course");
			
			ws.addCell(a);
			ws.addCell(b);
			ws.addCell(c);
			ws.addCell(d);
			ws.addCell(e);
			for (int i = 0; i < gpsList.size(); i++) {
			    Map<String,Object> map=gpsList.get(i);
			    Label c1= new Label(0, i+1,String.valueOf(map.get("y")));
			    Label c2= new Label(1, i+1,String.valueOf(map.get("x")));
			    Label c3= new Label(2, i+1,String.valueOf(map.get("t")));
			    Label c4= new Label(3, i+1,String.valueOf(map.get("s")));
			    Label c5= new Label(4, i+1,String.valueOf(map.get("c")));
			    ws.addCell(c1);
			    ws.addCell(c2);
			    ws.addCell(c3);
			    ws.addCell(c4);
			    ws.addCell(c5);
 
			}
     
			//写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			wwb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	public static  File saveToExcelAlarm(List<Map<String,Object>> alarmList){
		File file=null;
		try {
			WritableWorkbook wwb = null;
			
			// 创建可写入的Excel工作簿
			String fileName = "alarm.xls";
			file=new File(fileName);
			//以fileName为文件名来创建一个Workbook
			wwb = Workbook.createWorkbook(file);

			// 创建工作表
			WritableSheet ws = wwb.createSheet("alarm", 0);
 			ws.setColumnView(0,20);
 			ws.setColumnView(1,20);
 			ws.setColumnView(2,20);
 			ws.setColumnView(3,20);
 			ws.setColumnView(4,20);
 			ws.setColumnView(5,20);
 			ws.setColumnView(6,20);
 			//要插入到的Excel表格的行号，默认从0开始
 			Label a= new Label(0, 0, "startTime");
 			Label b= new Label(1, 0, "alarmValue");
 			Label c= new Label(2, 0, "endTime");
 			Label d= new Label(3, 0, "limitValue");
 			Label e= new Label(4, 0, "alarmType");
 			Label f= new Label(5, 0, "lng");
 			Label g= new Label(6, 0, "lat");
 			ws.addCell(a);
 			ws.addCell(b);
 			ws.addCell(c);
 			ws.addCell(d);
 			ws.addCell(e);
 			ws.addCell(f);
 			ws.addCell(g);
 			for (int i = 0; i < alarmList.size(); i++) {
 			    Map<String,Object> map=alarmList.get(i);
 			    Label c1= new Label(0, i+1,String.valueOf(map.get("startTime")));
 			    Label c2= new Label(1, i+1,String.valueOf(map.get("alarmValue")));
 			    Label c3= new Label(2, i+1,String.valueOf(map.get("endTime")));
 			    Label c4= new Label(3, i+1,String.valueOf(map.get("limitValue")));
 			    Label c5= new Label(4, i+1,String.valueOf(map.get("alarmType")));
 			    Label c6= new Label(5, i+1,String.valueOf(map.get("lng")));
 			    Label c7= new Label(6, i+1,String.valueOf(map.get("lat")));
 			    ws.addCell(c1);
 			    ws.addCell(c2);
 			    ws.addCell(c3);
 			    ws.addCell(c4);
 			    ws.addCell(c5);
 			    ws.addCell(c6);
 			    ws.addCell(c7);
 			  
 			}
      
 			//写进文档
 			wwb.write();
 			// 关闭Excel工作簿对象
 			wwb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	static WritableSheet createWs1(WritableWorkbook wwb,int sheet) throws RowsExceededException, WriteException{

		// 创建工作表
		WritableSheet ws = wwb.createSheet("sensor"+sheet, 0);
		ws.setColumnView(0,20);
		ws.setColumnView(1,20);
		ws.setColumnView(2,20);
		ws.setColumnView(3,20);
		ws.setColumnView(4,20);
		ws.setColumnView(5,20);
		ws.setColumnView(6,20);
		ws.setColumnView(7,20);
		ws.setColumnView(8,20);
		ws.setColumnView(9,20);
 
		ws.setColumnView(10,20);
		ws.setColumnView(11,20);
		ws.setColumnView(12,20);
		ws.setColumnView(13,20);
		ws.setColumnView(14,20);
		ws.setColumnView(15,20);
		
 
		
		//要插入到的Excel表格的行号，默认从0开始
		Label a= new Label(0, 0, "timestamp");
		Label b= new Label(1, 0, "gyroscopeZ");
		Label c= new Label(2, 0, "gyroscopeX");
		Label d= new Label(3, 0, "pitch");
		Label e= new Label(4,0, "accelerationY");
		Label f= new Label(5,0, "accelerationZ");
		Label g= new Label(6, 0, "roll");
		Label h= new Label(7,0, "accelerationX");
		Label j= new Label(8,0, "gyroscopeY");
		Label k= new Label(9,0, "yaw");
		
		Label l= new Label(10,0, "gravityX");
		Label m= new Label(11,0, "gravityY");
		Label n= new Label(12,0, "gravityZ");
		Label o= new Label(13,0, "magneticFieldX");
		Label p= new Label(14,0, "magneticFieldY");
		Label q= new Label(15,0, "magneticFieldZ");
		
		
		ws.addCell(a);
		ws.addCell(b);
		ws.addCell(c);
		ws.addCell(d);
		ws.addCell(e);
		ws.addCell(f);
		ws.addCell(g);
		ws.addCell(h);
		ws.addCell(j);
		ws.addCell(k);
		
		ws.addCell(l);
		ws.addCell(m);
		ws.addCell(n);
		ws.addCell(o);
		ws.addCell(p);
		ws.addCell(q);
		return ws;
	}
	
	/**
	 * 将详细的信息保存到Excel
	 * @param infos
	 * @return
	 */
	static int sheetRecord=65535;
	public static File saveToExcelSensor(List<Map<String,Object>> infos){
		File file=null;
		try {
			WritableWorkbook wwb = null;
			
			// 创建可写入的Excel工作簿
			String fileName = "infos.xls";
			file=new File(fileName);
			//以fileName为文件名来创建一个Workbook
			wwb = Workbook.createWorkbook(file);
	
			int j=0;
			int sheet=1;
			WritableSheet	ws=createWs1(wwb,sheet);
			for (int i = 0; i < infos.size(); i++) {
				
				if(j==sheetRecord){
					j=0;
					sheet++;
					ws=createWs1(wwb,sheet);
				}
				
			    Map<String,Object> map=infos.get(i);
			    Label c1= new Label(0, j+1,String.valueOf(map.get("t")));
			    Label c2= new Label(1, j+1,String.valueOf(map.get("tZ")));
			    Label c3= new Label(2, j+1,String.valueOf(map.get("tX")));
			    Label c4= new Label(3, j+1,String.valueOf(map.get("p")));
			    Label c5= new Label(4, j+1,String.valueOf(map.get("aY")));
			    Label c6= new Label(5, j+1,String.valueOf(map.get("aZ")));
			    Label c7= new Label(6, j+1,String.valueOf(map.get("r")));
			    Label c8= new Label(7, j+1,String.valueOf(map.get("aX")));
			    Label c9= new Label(8, j+1,String.valueOf(map.get("tY")));
			    Label c10= new Label(9, j+1,String.valueOf(map.get("y")));
			    
			    Label c11= new Label(10, j+1,String.valueOf(map.get("gX")));
			    Label c12= new Label(11, j+1,String.valueOf(map.get("gY")));
			    Label c13= new Label(12, j+1,String.valueOf(map.get("gZ")));
			    Label c14= new Label(13, j+1,String.valueOf(map.get("mX")));
			    Label c15= new Label(14, j+1,String.valueOf(map.get("mY")));
			    Label c16= new Label(15, j+1,String.valueOf(map.get("mZ")));
			    
			    ws.addCell(c1);
			    ws.addCell(c2);
			    ws.addCell(c3);
			    ws.addCell(c4);
			    ws.addCell(c5);
			    ws.addCell(c6);
			    ws.addCell(c7);
			    ws.addCell(c8);
			    ws.addCell(c9);
			    ws.addCell(c10);
			    ws.addCell(c11);
			    ws.addCell(c12);
			    ws.addCell(c13);
			    ws.addCell(c14);
			    ws.addCell(c15);
			    ws.addCell(c16);
			    j++;
			}
     
			//写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			wwb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
}
