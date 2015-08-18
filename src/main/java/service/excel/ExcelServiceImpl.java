package service.excel;

import model.Result;
import model.Status;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;
import service.ExcelService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by zzq on 15-5-22.
 */
@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public Result getExcel(String [] headArr,Map<String,String [] []> map,HttpServletResponse response){
        Result result = new Result();
        if(headArr.length>0){
            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            Set<String> set = map.keySet();
            int x = 0;
            for (String str : set) {
                // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
                HSSFSheet sheet = wb.createSheet();//"列表数据"
                wb.setSheetName(x,str); //sheet标题
                // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
                HSSFRow row = sheet.createRow((int) 0);
                // 第四步，创建单元格，并设置值表头 设置表头居中
                HSSFCellStyle style = wb.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                //表头
                HSSFCell cell = row.createCell(0);

                // 第五步，写入实体数据 实际应用中这些数据从数据库得到

                if(headArr.length>0){
                    for(int i=0;i<headArr.length;i++){
                        cell.setCellValue(headArr[i]);
                        cell.setCellStyle(style);
                        cell = row.createCell(i+1);
                    }
                }
                String [] [] arr =map.get(str);
            if (arr.length > 0) {
                for (int i = 0; i < arr.length; i++) {
                    row = sheet.createRow((int) i +1);
                    for(int j=0;j<arr[i].length;j++){
                        row.createCell(j).setCellValue(arr[i][j]);
                    }
                }
            }
                x++;
            }
            String filename=curTime();
            try {
                FileOutputStream fout = new FileOutputStream("/home/zzq/workspace/knPlatform/excel/"+filename+".xls");
                wb.write(fout);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename="+filename+".xls");
            OutputStream ouputStream = null;
            try {
                ouputStream = response.getOutputStream();
                wb.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.setStatus(Status.success);
        }else{
            result.setStatus(Status.noRecord);
        }
        return result;
    }
    public String curTime(){
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(new Date());//把当前时间赋给日历
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss"); //设置时间格式
        return sdf.format(calendar.getTime());
    }
}
