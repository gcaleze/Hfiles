package utilities;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	private XSSFWorkbook ExcelWBook;
	private XSSFSheet ExcelWSheet;
	
	
public String[][] getData(String tableName, String path, String sheetName) {
		
		DataFormatter formatter = new DataFormatter();
		
		String position = "begin";
		XSSFCell[] cells = new XSSFCell[2];
		
		FileInputStream ExcelFile;
		try {
			ExcelFile = new FileInputStream(path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		for(Row row : ExcelWSheet) {
			for (Cell cell : row) {
				
				if(tableName.equals(formatter.formatCellValue(cell))) {
					
					if(position.equalsIgnoreCase("begin")) {
						cells[0] = (XSSFCell) cell;
						position = "end";
						
					} else {
						cells[1] = (XSSFCell) cell;
					}
				}
			}
		}
		XSSFCell startCell = cells[0];
		XSSFCell endCell = cells[1];
		
		int startRow = startCell.getRowIndex() + 1;
		int endRow = endCell.getRowIndex() - 1;
		int startCol = startCell.getColumnIndex() + 1;
		int endCol = endCell.getColumnIndex() - 1;
		
		int numRow = endRow - startRow + 1;
		int numCol = endCol - startCol + 1;
		
		String[][] testData = new String[numRow][numCol];
		
		for(int i = startRow; i < endRow + 1; i++) {
			
			for(int j = startCol; j < endCol + 1; j++) {
				
				Cell cell = ExcelWSheet.getRow(i).getCell(j);
				
				testData[i - startRow][j - startCol] = formatter.formatCellValue(cell);
			}
		}
		return testData;
	}

}
