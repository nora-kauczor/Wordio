import './ReviewPage.css'
import {useEffect, useState} from "react";
import CardContainer from "../../components/CardContainer/CardContainer.tsx";
import {Vocab} from "../../types/Vocab.ts";
import {useNavigate} from "react-router-dom";
import Confetti from 'react-confetti';
import useLocalStorageState from "use-local-storage-state";

type Props = {
    vocabsToReview: Vocab[]
    removeVocabFromVocabsToReview: (id: string | null) => void
    changeReviewDates: (id: string | null) => void
    language: string
}

export default function ReviewPage(props: Readonly<Props>) {

    const [currentIndex, setCurrentIndex] = useLocalStorageState("currentIndex",
        {defaultValue: 0});
    const [currentVocab, setCurrentVocab] = useLocalStorageState("currentVocab",
        {defaultValue: props.vocabsToReview[0]});
    const [userInput, setUserInput] = useState<string>("")
    const [showFireworks, setShowFireworks] = useState(false);
    const navigate = useNavigate()

    useEffect(() => {
        setCurrentVocab(props.vocabsToReview[currentIndex])
    }, [currentIndex]);

    function getInputWithoutExtraSpaces(userInput: string): string {
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

    function getWordWithoutArticle(word: string): string {
        const spanishArticles: string[] = ["el", "la", "los", "las", "un",
            "una", "unos", "unas"];
        const frenchArticles: string[] = ["le", "la", "les", "un", "une", "des",
            "l'"];
        const italianArticles: string[] = ["il", "lo", "la", "i", "gli", "le",
            "un", "uno", "una", "un'"];
        let articles: string[] = [];
        if (props.language === "Spanish") {
            articles = spanishArticles
        } else {
            if (props.language === "Italian") {
                articles = italianArticles
            } else if (props.language === "French") {
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

    // TODO wenn zwei wörte rund mit slash getrennt: splitte es auf und prüfe
    // jeweils beide TODO wenn buchstabe in klammer: prüfe auf

    function getWordWithoutBrackets(word: string) {
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


    function getWordWithoutBracketsIncludingContent(word: string) {

    }

    function checkAnswer() {
// get all right solutions
        const wordWithoutBrackets: string = getWordWithoutBrackets(
            currentVocab.word)
        const wordWithoutArticle: string = getWordWithoutArticle(
            currentVocab.word)
        const wordWithoutArticleWithoutBrackets: string = getWordWithoutArticle(
            wordWithoutBrackets)
        const rightAnswers: string[] = [wordWithoutBrackets, wordWithoutArticle,
            wordWithoutArticleWithoutBrackets]


        const inputWithoutExtraSpaces: string = getInputWithoutExtraSpaces(
            userInput)
        if (inputWithoutExtraSpaces.toLowerCase() ===
            currentVocab.word.toLowerCase() ||
            inputWithoutExtraSpaces.toLowerCase() ===
            wordWithoutArticle.toLowerCase()) {
            setShowFireworks(true);
            setTimeout(() => {
                setShowFireworks(false);
            }, 2500);
            getNextVocab()
        } else {
            props.changeReviewDates(currentVocab.id)
            // TODO debug navigation
            navigate(`/display/:${currentVocab.id}`)
            // TODO how to get back to review page after that
        }
        props.removeVocabFromVocabsToReview(currentVocab.id)
    }

    function getNextVocab(): void {
        setCurrentIndex(currentIndex + 1)
    }

    return (<div id={"review-page"} role={"main"}>
        {showFireworks && <Confetti/>}
        {currentVocab && <CardContainer displayedVocab={currentVocab}/>}
        <label htmlFor={"review-input"} className={"visually-hidden"}>Your
            answer</label>
        <input id={"review-input"}
               onChange={element => setUserInput(element.target.value)}
               aria-label={"Enter your answer"}
               placeholder={"Type your answer here"}/>
        <button onClick={checkAnswer} aria-label={"Submit your answer"}>submit
        </button>
    </div>)
}