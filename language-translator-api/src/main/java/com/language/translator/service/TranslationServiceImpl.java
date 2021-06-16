package com.language.translator.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.language.translator.repository.TranslationRepository;


public class TranslationServiceImpl implements TranslationService{
	
	@Autowired
	TranslationRepository translationRepository;

	@Override
	public String identifyLanguage(String language) {
		return translationRepository.identifyLanguage(language);
	}

	@Override
	public String translateLanguage(String languageScript) {
		return translationRepository.translateForeignLanguage(languageScript, this.identifyLanguage(languageScript));
	}

}
