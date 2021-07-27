package com.sergent.wordSearch.WordSearchApplication.service;





import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sergent.wordSearch.WordSearchApplication.domain.WordSearchOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;

@Service
@Slf4j
public class WordSearchService {

    @Value("${directory.path}")
    String directoryPath;

    public List<WordSearchOutput> searchWordsFromADirectory(List<String> words)
    throws IOException{
        List<WordSearchOutput> wordSearchOutputs=new ArrayList<>();
        WordSearchOutput wordSearchOutput=null;
        for(String word:words){
            log.info("calling fetchFiles for word::"+word);
            wordSearchOutput=fetchFilesContainsWord(word);
            wordSearchOutputs.add(wordSearchOutput);
        }
        return wordSearchOutputs;
    }

    public WordSearchOutput fetchFilesContainsWord(String word) throws IOException {
        WordSearchOutput wordSearchOutput=new WordSearchOutput();
        wordSearchOutput.setWord(word);
        List<String> filepathContainsWord=new ArrayList<>();

        //C:\\Users\\m_406943\\Documents\\Personal Repo\\test-folder
        log.info("directory path:: {}",directoryPath);
        File mainDirectory = new File(directoryPath);
        List<File> fileArray=null;
        if(mainDirectory.exists() && mainDirectory.isDirectory()) {
            log.info("inside main directory check");
            //fileArray= mainDirectory.listFiles();
            fileArray=(List<File>)FileUtils.listFiles(mainDirectory,
            		new String[] {".txt","doc","xlsx","txt"}, true);
        }
        if(!fileArray.isEmpty()){
            //log.info("fileArray not null {}", Arrays.stream(fileArray).count());
        	log.info("fileArray size::"+fileArray.size());
            for(File file:fileArray){
                try {
                    log.info("file name:: {}",file.getPath());
                    if(checkForWordPresentInFile(word,file)){
                        filepathContainsWord.add(file.getPath());
                    }

                }catch(IOException e){
                    log.error("error while trying to process the file, so not gonna throw exception {}",e.getMessage());
                }
            }
        }
        wordSearchOutput.setFileContainsWord(filepathContainsWord);
        return wordSearchOutput;
    }
    public boolean checkForWordPresentInFile(String word,File file) throws IOException{
        String paddedInput = " " + word + " ";
        String paddedInputStart = word + " ";
        String paddedInputEnd = " " +word ;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.equals(word) ||
                    line.startsWith(paddedInputStart) ||
                    line.endsWith(paddedInputEnd) ||
                    (line.contains(paddedInput))) {
                log.debug("word present in file return true::"+word);
                return true;
            }
        }
        return false;
    }
}
