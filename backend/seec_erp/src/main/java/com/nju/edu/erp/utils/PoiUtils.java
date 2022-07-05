package com.nju.edu.erp.utils;

import com.nju.edu.erp.model.po.WarehousePO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseCountingVO;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.RichTextString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

public class PoiUtils {
//    ResponseEntity<byte[]>
    public static void exportJobLevelExcel(List<WarehousePO> warehousePOS, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();//创建一个Excel文件

        //创建Excel文档属性，必不可少。少了的话，getDocumentSummaryInformation()方法就会返回null
        workbook.createInformationProperties();
        DocumentSummaryInformation info = workbook.getDocumentSummaryInformation();
        info.setCompany("erp.");//设置公司信息
        info.setManager("kucun");//设置管理者
        info.setCategory("库存盘点");//设置文件名

        //设置文件中的日期格式
        HSSFCellStyle datecellStyle = workbook.createCellStyle();
        datecellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));//这个文件的日期格式和平时的不一样

        //创建表单
        HSSFSheet sheet = workbook.createSheet("库存盘点");
        HSSFRow r0 = sheet.createRow(0);//创建第一行
        HSSFCell c0 = r0.createCell(0);// 创建列
        HSSFCell c1 = r0.createCell(1);// 创建列
        HSSFCell c2 = r0.createCell(2);// 创建列
        HSSFCell c3 = r0.createCell(3);// 创建列
        HSSFCell c4 = r0.createCell(4);// 创建列
        HSSFCell c5 = r0.createCell(5);// 创建列

        c0.setCellValue("行号");
        c1.setCellValue("商品id");
        c2.setCellValue("数量");
        c3.setCellValue("批次");
        c4.setCellValue("价格");
        c5.setCellValue("生产日期");



        for (int i = 0; i < warehousePOS.size(); i++) {
            WarehousePO warehousePO=warehousePOS.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            HSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(i+1);
            HSSFCell cell1 = row.createCell(1);
            cell1.setCellValue(warehousePO.getPid());
            HSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(warehousePO.getQuantity());
            HSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(warehousePO.getBatchId());
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(warehousePO.getPurchasePrice().toString());
            HSSFCell cell5 = row.createCell(5);
            if(warehousePO.getProductionDate()==null)cell5.setCellValue("无");
            else cell5.setCellValue(warehousePO.getProductionDate());
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));//列宽自适应
        for (int i = 0; i <= warehousePOS.size(); i++) {
            sheet.autoSizeColumn(i);
        }
        OutputStream outputStream = null;
        try {
            String fileName = "订单汇总.xlsx";
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);

            outputStream.flush();

//            ExcelUtils.downloadFiles(filePath, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment",
//                new String("库存盘点.xls".getBytes("UTF-8"),"iso-8859-1"));
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//        workbook.write(baos);
//
//        ResponseEntity<byte[]> entity = new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.CREATED);
//
//        return entity;
    }
}
