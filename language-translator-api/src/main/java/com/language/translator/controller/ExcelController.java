package com.language.translator.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.language.translator.model.LanguageTranslator;
import com.language.translator.service.ExcelOperation;

@Controller
public class ExcelController {
	
	@Autowired
	ExcelOperation excelOperation;
	
	@RequestMapping("home")
	public ResponseEntity<List<LanguageTranslator>> home() throws FileNotFoundException, IOException {
		System.out.println("hiiiii.....");
		
		List<LanguageTranslator>languaguelist=excelOperation.readForeignLanguageFr();
		excelOperation.writeTranslatedLanguage(languaguelist);
		
		ResponseEntity<LanguageTranslator>response=null;
		
		return response.ok(languaguelist);
	}



}
