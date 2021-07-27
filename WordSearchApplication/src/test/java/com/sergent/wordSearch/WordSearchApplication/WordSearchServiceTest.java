package com.sergent.wordSearch.WordSearchApplication;


import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.test.context.ActiveProfiles;

import com.sergent.wordSearch.WordSearchApplication.domain.WordSearchOutput;
import com.sergent.wordSearch.WordSearchApplication.service.WordSearchService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class WordSearchServiceTest {

    @InjectMocks
    WordSearchService wordSearchService;

    @Test
    public void checkForWordPresentInFileSucesssAndFailure(){

        String content = "Hello World !!";
        try {
            Files.write(Paths.get("src/test/resources/test-folder/test-folder.txt"), content.getBytes());
            File file = new File("src/test/resources/test-folder/test-folder.txt");
            log.info("file exists::"+file.exists()+" "+file.isFile());
            Assert.assertTrue(wordSearchService.checkForWordPresentInFile("Hello",file));
            Assert.assertFalse(wordSearchService.checkForWordPresentInFile("ello",file));
            Assert.assertTrue(wordSearchService.checkForWordPresentInFile("Hello World",file));
            Assert.assertFalse(wordSearchService.checkForWordPresentInFile("hello world",file));
            Assert.assertTrue(wordSearchService.checkForWordPresentInFile(content,file));
        }catch(IOException e){
            log.error(e.getMessage());
        }
    }
    @Test
    public void fetchFilesContainsWordSucessAndFailure(){
        try {
            WordSearchOutput wordSearchOutput = wordSearchService.fetchFilesContainsWord("Hello");
            Assert.assertNotNull(wordSearchOutput);
        }catch(IOException e){
            log.error(e.getMessage());
        }
    }
    @Test
    public void searchWordsFromADirectorySucess(){
        try{
            List<WordSearchOutput> wordSearchOutputs=
                    wordSearchService.searchWordsFromADirectory(Arrays.asList("Hello","ello","Hello World","!!"));
            Assert.assertNotNull(wordSearchOutputs);
            Assert.assertEquals(3,wordSearchOutputs.size());
        }catch(IOException e){
            log.error(e.getMessage());
        }
    }
}