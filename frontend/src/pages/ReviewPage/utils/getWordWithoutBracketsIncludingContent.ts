export function getWordWithoutBracketsIncludingContent(word: string):string{
    if (!word.includes("(")) {
        return word
    }
    let indexOfOpeningTag:number = 0;
    let indexOfClosingTag:number = 0;
    for (let i: number = 0; i < word.length; i++) {
        if (word.charAt(i) === "(") {
            indexOfOpeningTag = i
        }
    }
    for (let i: number = 0; i < word.length; i++) {
        if (word.charAt(i) === ")") {
            indexOfClosingTag = i
        }
    }
    return word.slice(0, indexOfOpeningTag) + word.slice(indexOfClosingTag+1, word.length)
}