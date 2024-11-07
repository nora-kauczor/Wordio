export function getLeftSideOfSlash(word:string){
    if (!word.includes("/")) {
        return word
    }
    let indexOfSlash = 0;
    for (let i: number = 0; i < word.length; i++) {
        if (word.charAt(i) === "/") {
            indexOfSlash = i
        }
    }
    return word.slice(0, indexOfSlash)
}