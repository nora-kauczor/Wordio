export type Vocab = {
    _id: string | null,
    word: string,
    translation: string,
    info: string,
    language: string,
    datesPerUser?: { [userName: string]: string[] }
    createdBy?: string;
}