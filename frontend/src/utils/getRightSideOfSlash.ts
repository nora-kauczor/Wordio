export function getRightSideOfSlash(word:string){
    if (!word.includes("/")) {
        return word
    }
    let indexOfSlash = 0;
    for (let i: number = 0; i < word.length; i++) {
        if (word.charAt(i) === "/") {
            indexOfSlash = i
        }
    }
    return word.slice(indexOfSlash+1, word.length)
}