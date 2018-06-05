Scenario: logowanie numer1

Given Mam logowanie1
When Zaloguje sie wpisujac login <log> i haslo <pas>
Then Otrzymam tytul strony <wynik>

Examples:
|log|pas|wynik|
|zgitlabselenium|gitlabselenium1|Projects · Dashboard · GitLab|
|zgitlabselenium2|gitlabselenium1|Projects · Dashboard · GitLab|