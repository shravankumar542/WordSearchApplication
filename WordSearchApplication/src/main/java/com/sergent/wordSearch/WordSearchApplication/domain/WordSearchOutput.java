package com.sergent.wordSearch.WordSearchApplication.domain;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WordSearchOutput implements Serializable {
    private static final long serialVersionUID = -1L;
    private String word;
    private List<String> fileContainsWord;
}