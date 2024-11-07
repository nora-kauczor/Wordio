import {getWordWithoutBrackets} from "./getWordWithoutBrackets.ts";
import {
    getWordWithoutBracketsIncludingContent
} from "./getWordWithoutBracketsIncludingContent.ts";
import {getWordWithoutArticle} from "./getWordWithoutArticle.ts";
import {getLeftSideOfSlash} from "./getLeftSideOfSlash.ts";
import {getRightSideOfSlash} from "./getRightSideOfSlash.ts";

export function getRightAnswers(word: string, language: string): string[] {
    const wordWithoutBrackets: string = getWordWithoutBrackets(word)
    const wordWithoutBracketsIncludingContent = getWordWithoutBracketsIncludingContent(
        word)
    const wordWithoutArticle: string = getWordWithoutArticle(word, language)
    const wordWithoutArticleWithoutBrackets: string = getWordWithoutBrackets(
        word)
    const wordWithoutArticleWithoutBracketsIncludingContent: string = getWordWithoutBracketsIncludingContent(
        wordWithoutArticle)
    const leftSideOfSlash: string = getLeftSideOfSlash(word)
    const leftSideOfSlashWithoutArticle = getWordWithoutArticle(leftSideOfSlash,
        language)
    const rightSideOfSlash: string = getRightSideOfSlash(word)
    const rightSideOfSlashWithoutArticle = getWordWithoutArticle(
        rightSideOfSlash, language)
    return [wordWithoutBrackets, wordWithoutBracketsIncludingContent,
        wordWithoutArticle, wordWithoutArticleWithoutBrackets,
        wordWithoutArticleWithoutBracketsIncludingContent, leftSideOfSlash,
        leftSideOfSlashWithoutArticle, rightSideOfSlash,
        rightSideOfSlashWithoutArticle]
}