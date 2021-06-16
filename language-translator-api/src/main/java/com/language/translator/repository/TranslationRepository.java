package com.language.translator.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.ibm.cloud.sdk.core.http.HttpConfigOptions;
import com.ibm.cloud.sdk.core.http.ServiceCall;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.IdentifiableLanguages;
import com.ibm.watson.language_translator.v3.model.IdentifiedLanguages;
import com.ibm.watson.language_translator.v3.model.IdentifyOptions;
import com.ibm.watson.language_translator.v3.model.Languages;
import com.ibm.watson.language_translator.v3.model.ListIdentifiableLanguagesOptions;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;

@Repository
public class TranslationRepository {
	
	@Value("${watson.api.key}")
	private String apiKey;
	
	@Value("${watson.api.url}")
	private String url;
	
	@Autowired
	IamAuthenticator iamAuthenticator;
	
	// Identify the given Language
	public String identifyLanguage(String flanguage) {
		
		LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", iamAuthenticator);
		languageTranslator.setServiceUrl(url);
		
		IdentifyOptions identifyOptions = new IdentifyOptions.Builder()
				  .text(flanguage)
				  .build();

				IdentifiedLanguages languages = languageTranslator.identify(identifyOptions)
				  .execute().getResult();
		
				
		return languages.getLanguages().get(0).getLanguage();
	}
	
	
	// Translate the given Language to English
	public String translateForeignLanguage(String flanguage, String translationFrom) {
		
		LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", iamAuthenticator);
		languageTranslator.setServiceUrl(url);
		
		TranslateOptions translateOptions = new TranslateOptions.Builder()
				  .addText(flanguage)
				  .modelId(translationFrom+"en")// Translation From source Language where en represents English
				  .build();

				TranslationResult result = languageTranslator.translate(translateOptions)
				  .execute().getResult();
		
		return result.getTranslations().get(0).getTranslation();
	}

}
