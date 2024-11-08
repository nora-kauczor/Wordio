export function getWordWithoutArticle(word: string, language:string): string {
    const spanishArticles: string[] = ["el", "la", "los", "las", "un",
        "una", "unos", "unas"];
    const frenchArticles: string[] = ["le", "la", "les", "un", "une", "des",
        "l'"];
    const italianArticles: string[] = ["il", "lo", "la", "i", "gli", "le",
        "un", "uno", "una", "un'"];
    let articles: string[] = [];
    if (language === "Spanish") {
        articles = spanishArticles
    } else {
        if (language === "Italian") {
            articles = italianArticles
        } else if (language === "French") {
            articles = frenchArticles
        } else {
            return word
        }
    }
    for (let i: number = 0; i < 4; i++) {
        if (articles.includes(word.substring(0, i)) &&
            word.charAt(i + 1) === " ") {
            return word.slice(i + 2, word.length)
        }
    }
    return word;
}