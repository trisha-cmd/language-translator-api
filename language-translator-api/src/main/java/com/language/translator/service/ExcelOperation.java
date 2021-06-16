package com.language.translator.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.language.translator.model.LanguageTranslator;


public interface ExcelOperation {
	
	public List<LanguageTranslator> readForeignLanguageFr() throws FileNotFoundException, IOException;
	
	public void writeTranslatedLanguage(List<LanguageTranslator> list);
	

}
