# android_horvath_lindeisz
Our android development
# Projekt neve
FinancialCoinApp

## Rövid leírás
Egy API-ról adatokat vesz le, feldolgozza és megtekinthetővé teszi

## Funkciók
- . . .  <!-- Funkció 1 -->
- . . .  <!-- Funkció 2 -->
- . . .  <!-- Funkció 3 -->
- . . .  <!-- Funkció 4 (ha van) -->
- . . .  <!-- Funkció 5 (ha van) -->

## Telepítés / Futtatás

Mindenki a main branchba dolgozik

echo "# android_horvath_lindeisz" >> README.md  
git init  
git add README.md  
git commit -m "first commit"  
git branch -M main
git remote add origin https://github.com/Horvath-qss8b4/android_horvath_lindeisz.git  
git push -u origin main


## Finnhub – NEWS API JSON struktúra

```text
FinnhubNewsResponse = [
    {
        category: string
        datetime: number
        headline: string
        id: number
        image: string
        related: string
        source: string
        summary: string
        url: string
    }
]

## CoinMarketCap – NEWS API JSON struktúra

```text
CoinMarketCapNewsResponse {
    status: {
        timestamp: string
        error_code: number
        error_message: string | null
        elapsed: number
        credit_count: number
    }
    data: [
        {
            id: number
            title: string
            description: string
            url: string
            source: string
            published_at: string
            thumbnail: string
            related_coins: [
                {
                    id: number
                    name: string
                    symbol: string
                    slug: string
                }
            ]
        }
    ]
}


