export type ReviewDay =
    {
        reviewDayId: string,
        date: string,
        userId: string,
        vocabIds: { [vocabId: string]: boolean }
    }