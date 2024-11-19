package org.example.backend.check;

import org.example.backend.vocab.Language;

import java.util.List;


public class CorrectAnswers {

    public static List<String> getCorrectAnswers(String word, Language language) {
        String leftSideOfSlash = getLeftSideOfSlash(word);
        String leftSideOfSlashWithoutArticle = getWordWithoutArticle(leftSideOfSlash, language);
        String rightSideOfSlash = getRightSideOfSlash(word);
        String rightSideOfSlashWithoutArticle = getWordWithoutArticle(word, language);
        String leftSideOfSlashWithEndingOfRightSide = getLeftSideOfSlashWithEndingOfRightSide(word);
        String wordWithoutArticle = getWordWithoutArticle(word, language);
        // without brackets but with brackets' content
        String wordWithoutBrackets = getWordWithoutBrackets(word);
        String wordWithoutArticleWithoutBrackets = getWordWithoutArticle(wordWithoutBrackets, language);
        String wordWithoutBracketsRunTwice = getWordWithoutBrackets(wordWithoutBrackets);
        // without brackets and without brackets' content
        String wordWithoutBracketsAndWithoutContent = getWordWithoutBracketsAndWithoutBracketsContent(word);
        String wordWithoutArticleWithoutBracketsAndWithoutContent = getWordWithoutArticle(wordWithoutBracketsAndWithoutContent, language);
        String wordWithoutBracketsAndWithoutContentRunTwice = getWordWithoutBracketsAndWithoutBracketsContent(wordWithoutBracketsAndWithoutContent);
        return List.of(
                word,
                leftSideOfSlash,
                leftSideOfSlashWithoutArticle,
                rightSideOfSlash,
                rightSideOfSlashWithoutArticle,
                leftSideOfSlashWithEndingOfRightSide,
                //
                wordWithoutArticle,
                wordWithoutBrackets,
                wordWithoutArticleWithoutBrackets,
                wordWithoutBracketsRunTwice,
                wordWithoutBracketsAndWithoutContent,
                wordWithoutArticleWithoutBracketsAndWithoutContent,
                wordWithoutBracketsAndWithoutContentRunTwice
        );
    }

    public static String getLeftSideOfSlash(String word) {
        char slash = '/';
        boolean wordHasSlash = word.indexOf(slash) != -1;
        if (!wordHasSlash) {
            return word;
        }
        int indexOfSlash = word.indexOf(slash);
        return word.substring(0, indexOfSlash);
    }

    public static String getRightSideOfSlash(String word) {
        char slash = '/';
        boolean wordHasSlash = word.indexOf(slash) != -1;
        if (!wordHasSlash) {
            return word;
        }
        int indexOfSlash = word.indexOf(slash);
        return word.substring(indexOfSlash + 1);
    }

    public static String getLeftSideOfSlashWithEndingOfRightSide(String word) {
        char slash = '/';
        boolean wordHasSlash = word.indexOf(slash) != -1;
        if (!wordHasSlash) {
            return word;
        }
        int indexOfSlash = word.indexOf(slash);
        char minus = '-';
        boolean wordHasMinus = word.indexOf(minus) != -1;
        if (!wordHasMinus) {
            return word;
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
            return word;
        }
        String wordWithoutArticle = word;
        for (String article : articles) {
            boolean articleIsAtBeginning = word.indexOf(article) == 0;
            int articleLength = article.length();
            String substringBehindArticle = word.substring(articleLength, articleLength + 1);
            boolean spaceBehindArticle = substringBehindArticle.equals(" ");
            if (articleIsAtBeginning && spaceBehindArticle) {
                wordWithoutArticle = word.substring(articleLength + 1);
            }
            boolean bracketBehindArticle = substringBehindArticle.equals("(");
            if (articleIsAtBeginning && bracketBehindArticle) {
                wordWithoutArticle = word.substring(articleLength + 4);
            }
        }
        return wordWithoutArticle;
    }

    public static String getWordWithoutBrackets(String word) {
        char openingBracket = '(';
        char closingBracket = ')';
        boolean wordHasBrackets = word.indexOf(openingBracket) != -1;
        if (!wordHasBrackets) {
            return word;
        }
        int indexOfOpeningBracket = word.indexOf(openingBracket);
        int indexOfClosingBracket = word.indexOf(closingBracket);
        String substringBeforeBrackets = word.substring(0, indexOfOpeningBracket);
        String substringBetweenBrackets = word.substring(indexOfOpeningBracket + 1, indexOfClosingBracket);
        String subStringAfterBrackets = word.substring(indexOfClosingBracket + 1);
        return substringBeforeBrackets + substringBetweenBrackets + subStringAfterBrackets;
    }

    public static String getWordWithoutBracketsAndWithoutBracketsContent(String word) {
        char openingBracket = '(';
        char closingBracket = ')';
        boolean wordHasBrackets = word.indexOf(openingBracket) != -1;
        if (!wordHasBrackets) {
            return word;
        }
        int indexOfOpeningBracket = word.indexOf(openingBracket);
        int indexOfClosingBracket = word.indexOf(closingBracket);
        String substringBeforeBrackets = word.substring(0, indexOfOpeningBracket);
        String subStringAfterBrackets = word.substring(indexOfClosingBracket + 1);
        return substringBeforeBrackets + subStringAfterBrackets;
    }


}
