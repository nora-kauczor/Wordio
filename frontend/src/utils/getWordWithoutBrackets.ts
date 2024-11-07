export function getWordWithoutBrackets(word: string) {
    if (!word.includes("(")) {
        return word
    }
    let wordWithoutOpeningTag: string = word;
    for (let i: number = 0; i < word.length; i++) {
        if (word.charAt(i) === "(") {
            wordWithoutOpeningTag =
                word.slice(0, i) + word.slice(i + 1, word.length)
        }
    }
    for (let i: number = 0; i < word.length; i++) {
        if (wordWithoutOpeningTag.charAt(i) === ")") {
            let lastPart: string = "";
            if (wordWithoutOpeningTag.charAt(
                wordWithoutOpeningTag.length - 1) === ")") {
                console.log("condition met")
                lastPart = ""
            } else {
                lastPart =
                    wordWithoutOpeningTag.slice(i + 1, word.length - 1)
            }
            return wordWithoutOpeningTag.slice(0, i) + lastPart
        }
    }
}
