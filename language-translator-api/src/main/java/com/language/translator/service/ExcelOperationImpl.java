package com.language.translator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.language.translator.model.LanguageTranslator;


@Service
public class ExcelOperationImpl implements ExcelOperation {

	@Value("${excel.view.path}")
	private String excelPathdetail;

	@Autowired
	TranslationService translationService;

	public List<LanguageTranslator> readForeignLanguageFr() throws IOException {

		String excelpath = excelPathdetail;
		String[] excelData = null;
		LanguageTranslator languageTranslator = null;
		List<LanguageTranslator> languagelist = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(excelpath));
		Workbook workbook = new XSSFWorkbook(inputStream);

		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			if (nextRow.getRowNum() == 0) {
				continue;
			}
			while (cellIterator.hasNext()) {
				excelData = new String[4];
				for (int i = 0; i < 12; i++) {
					try {
						Cell cell = cellIterator.next();
						switch (cell.getCellType()) {

						case Cell.CELL_TYPE_STRING:
							String sValue = cell.getStringCellValue();
							String encoded = URLEncoder.encode(sValue, "UTF-8");
							excelData[i] = encoded;
							System.out.print(cell.getStringCellValue());
							break;
						}
					} catch (NoSuchElementException exception) {
						excelData[i] = "";
					}
				}
				if (excelData.length > 0) {
					languageTranslator = new LanguageTranslator();
					languageTranslator.setSource(excelData[0]);
					String identifiedLanguage = translationService.identifyLanguage(excelData[0]);
					languageTranslator.setLocale(identifiedLanguage);
					languageTranslator.setTarget(translationService.translateLanguage(excelData[0]));

				}
			}
		}

		return languagelist;
	}

	public void writeTranslatedLanguage(List<LanguageTranslator> languageTranslatorData) {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java Books");

		if (languageTranslatorData != null) {
			for (LanguageTranslator language : languageTranslatorData) {
				int rowCount = 1;
				Row row = sheet.createRow(++rowCount);
				writeBook(language, row);
			}

			try {
				FileOutputStream outputStream = new FileOutputStream(excelPathdetail);
				workbook.write(outputStream);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void writeBook(LanguageTranslator language, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(language.getSource());

		cell = row.createCell(2);
		cell.setCellValue(language.getTarget());

		cell = row.createCell(3);
		cell.setCellValue(language.getLocale());
	}

}
