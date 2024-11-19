package org.example.backend.check;

import java.util.List;

public class CorrectAnswers {

//    public List<String> getCorrectAnswers(String word) {
//        /*
//        String leftSideOfSlash
//        String leftSideOfSlashWithoutArticle
//        String rightSideOfSlash
//        String rightSideOfSlashWithoutArticle
//        String wordWithoutBrackets =
//        String wordWithoutBracketsIncludingContent,
//        String wordWithoutArticle =
//        String wordWithoutArticleWithoutBrackets,
//        String wordWithoutArticleWithoutBracketsIncludingContent,
//
//       */
//    }

    public static String getLeftSideOfSlash(String word) {
        char slash = '/';
        boolean wordHasSlash = word.indexOf(slash) != -1;
        if (!wordHasSlash) {return word;}
        int indexOfSlash = word.indexOf(slash);
        return word.substring(0,indexOfSlash);
    }

    public static String getRightSideOfSlash(String word) {
        char slash = '/';
        boolean wordHasSlash = word.indexOf(slash) != -1;
        if (!wordHasSlash) {return word;}
        int indexOfSlash = word.indexOf(slash);
        return word.substring(indexOfSlash+1);
    }

    // getLeftSideWithEndingOfRightSide

//    public static String getWordWithoutArticle(String word) {
//         List<String> spanishArticles = List.of("el", "la", "los", "las", "un",
//                "una", "unos", "unas");
//        List<String> frenchArticles = List.of("le", "la", "les", "un", "une", "des",
//                "l'");
//        List<String> italianArticles = List.of("il", "lo", "la", "i", "gli", "le",
//                "un", "uno", "una", "un'");
//
//    }

//    public static String getWordWithoutBrackets(String word) {
//    }

//    public String static getWordWithoutBracketsAndWithoutBracketsContent(String word) {
//    }


}
