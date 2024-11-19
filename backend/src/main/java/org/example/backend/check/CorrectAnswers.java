package org.example.backend.check;

import org.example.backend.vocab.Language;

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
        if (!wordHasSlash) {
            return null;
        }
        int indexOfSlash = word.indexOf(slash);
        return word.substring(0, indexOfSlash);
    }

    public static String getRightSideOfSlash(String word) {
        char slash = '/';
        boolean wordHasSlash = word.indexOf(slash) != -1;
        if (!wordHasSlash) {
            return null;
        }
        int indexOfSlash = word.indexOf(slash);
        return word.substring(indexOfSlash + 1);
    }

    public static String getLeftSideOfSlashWithEndingOfRightSide(String word) {
        char slash = '/';
        boolean wordHasSlash = word.indexOf(slash) != -1;
        if (!wordHasSlash) {
            return null;
        }
        int indexOfSlash = word.indexOf(slash);
        char minus = '-';
        boolean wordHasMinus = word.indexOf(minus) != -1;
        if (!wordHasMinus) {
            return null;
        }
        String ending = word.substring(indexOfSlash + 2);
        String leftSideWithoutLastLetter = word.substring(0, indexOfSlash - 1);
        return leftSideWithoutLastLetter + ending;
    }

    public static String getWordWithoutArticle(String word, Language language) {
        List<String> articles = List.of("el", "la", "los", "las", "un",
                "una", "unos", "unas");
        if (language.equals(Language.ITALIAN)) {
            articles = List.of("il", "lo", "la", "i", "gli", "le",
                    "un", "uno", "una", "un'");
        }
        if (language.equals(Language.FRENCH)) {
            articles = List.of("le", "la", "les", "un", "une", "des",
                    "l'");
        }
        boolean wordStartsWithArticlesLetters = articles.stream().anyMatch(word::contains);
        if (!wordStartsWithArticlesLetters) {
            return null;
        }
        String wordWithoutArticle = null;
        for (String article : articles) {
            boolean articleIsAtBeginning = word.indexOf(article) == 0;
            int articleLength = article.length();
            String substringBehindArticle = word.substring(articleLength, articleLength + 1);
            boolean spaceBehindArticle = substringBehindArticle.equals(" ");
            if (articleIsAtBeginning && spaceBehindArticle) {
                wordWithoutArticle = word.substring(articleLength + 1);
            }
        }
        return wordWithoutArticle;
    }

//    public static String getWordWithoutBrackets(String word) {
//    }

//    public String static getWordWithoutBracketsAndWithoutBracketsContent(String word) {
//    }


}
