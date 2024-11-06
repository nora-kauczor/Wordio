export type Vocab = {
    id: string | null,
    word: string,
    translation: string,
    info: string,
    language: string,
    datesPerUser?: { [userId: string]: string[] }
    createdBy?: string;
}