export   function getInputWithoutExtraSpaces(userInput: string): string {
    const trimmedInput: string = userInput.trim()
    const chars: string[] = trimmedInput.split('');
    const charsWithoutExtraSpaces: string[] = chars;
    let z: number = 0;
    while (z < charsWithoutExtraSpaces.length) {
        if (charsWithoutExtraSpaces[z] === " " &&
            charsWithoutExtraSpaces[z + 1] === " ") {
            charsWithoutExtraSpaces.splice(z + 1, 1)
        } else {
            z++
        }
    }
    return charsWithoutExtraSpaces.reduce((a, b) => a + b, "")
}