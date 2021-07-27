package com.sergent.wordSearch.WordSearchApplication.controller;


import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergent.wordSearch.WordSearchApplication.domain.WordSearchOutput;
import com.sergent.wordSearch.WordSearchApplication.service.WordSearchService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/wordSearch", produces= MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class WordSearchController {

    @Autowired
    WordSearchService wordSearchService;

    @ApiOperation(value = "Fetch files which contains the give words")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "words", value = "list of words with delimiter as ,",
                    required = true, dataType = "String", paramType = "query", defaultValue = "file")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = WordSearchOutput.class),
            @ApiResponse(code = 400, message = "Invalid or missing request data."),
            @ApiResponse(code = 500, message = "Internal Server Error")}) //, response = ErrorResponse.class

    @GetMapping(path="/multiWordSearch")
    public List<WordSearchOutput> searchWordsFromDirectoryOfFiles(@RequestParam String words)
            throws IOException {
        List<String> wordsList= Arrays.asList(words.split(","));
        log.info("wordsList::"+wordsList+" words::"+words);
        return wordSearchService.searchWordsFromADirectory(wordsList);
    }
}