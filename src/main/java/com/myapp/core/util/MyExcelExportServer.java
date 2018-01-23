package com.myapp.core.util;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.ExcelExportServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.*;

/**
 * @path：com.myapp.core.util
 * @description：
 * @author： ly
 * @date: 2018-01-19 16:25
 */
public class MyExcelExportServer extends ExcelExportServer {
    @Override
    protected int createHeaderAndTitle(ExportParams entity, Sheet sheet, Workbook workbook, List<ExcelExportEntity> excelParams) {
        int rows = 0;
        int fieldLength = this.getFieldLength(excelParams);
        if (entity.getTitle() != null) {
            rows += this.createHeaderRow(entity, sheet, workbook, fieldLength);
        }

        rows += this.createTitleRow(entity, sheet, workbook, rows, excelParams);
        sheet.createFreezePane(0, rows, 0, rows);
        return rows;
    }
    private int createTitleRow(ExportParams title, Sheet sheet, Workbook workbook, int index, List<ExcelExportEntity> excelParams) {
        Row row = sheet.createRow(index);
        int rows = this.getRowNums(excelParams);
        row.setHeight((short)450);
        Row listRow = null;
        if (rows == 2) {
            listRow = sheet.createRow(index + 1);
            listRow.setHeight((short)450);
        }

        int cellIndex = 0;
        int groupCellLength = 0;
        CellStyle titleStyle = this.getExcelExportStyler().getTitleStyle(title.getColor());
        int i = 0;

        for(int exportFieldTitleSize = excelParams.size(); i < exportFieldTitleSize; ++i) {
            ExcelExportEntity entity = (ExcelExportEntity)excelParams.get(i);
            if (i>0&&(StringUtils.isBlank(entity.getGroupName()) || !entity.getGroupName().equals(((ExcelExportEntity)excelParams.get(i - 1)).getGroupName()))) {
                if (groupCellLength > 1) {
                    sheet.addMergedRegion(new CellRangeAddress(index, index, cellIndex - groupCellLength, cellIndex - 1));
                }

                groupCellLength = 0;
            }

            if (StringUtils.isNotBlank(entity.getGroupName())) {
                this.createStringCell(row, cellIndex, entity.getGroupName(), titleStyle, entity);
                this.createStringCell(listRow, cellIndex, entity.getName(), titleStyle, entity);
                ++groupCellLength;
            } else if (StringUtils.isNotBlank(entity.getName())) {
                this.createStringCell(row, cellIndex, entity.getName(), titleStyle, entity);
            }

            if (entity.getList() == null) {
                if (rows == 2 && StringUtils.isBlank(entity.getGroupName())) {
                    this.createStringCell(listRow, cellIndex, "", titleStyle, entity);
                    sheet.addMergedRegion(new CellRangeAddress(index, index + 1, cellIndex, cellIndex));
                }
            } else {
                List<ExcelExportEntity> sTitel = entity.getList();
                if (StringUtils.isNotBlank(entity.getName())) {
                    sheet.addMergedRegion(new CellRangeAddress(index, index, cellIndex, cellIndex + sTitel.size() - 1));
                }

                int j = 0;

                for(int size = sTitel.size(); j < size; ++j) {
                    this.createStringCell(rows == 2 ? listRow : row, cellIndex, ((ExcelExportEntity)sTitel.get(j)).getName(), titleStyle, entity);
                    ++cellIndex;
                }

                --cellIndex;
            }

            ++cellIndex;
        }

        if (groupCellLength > 1) {
            sheet.addMergedRegion(new CellRangeAddress(index, index, cellIndex - groupCellLength, cellIndex - 1));
        }

        return rows;
    }
    @Override
    public void sortAllParams(List<ExcelExportEntity> excelParams) {
    	
    }
}
