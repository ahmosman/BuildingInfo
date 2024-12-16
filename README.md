## Struktura danych

### Lokacja
Lokacja to budynek, poziom, lub pomieszczenie.

### Budynek
Budynek może składać się z poziomów, a te z pomieszczeń.

### Atrybuty lokalizacji
- `id` – unikalny identyfikator
- `name` – opcjonalna nazwa lokalizacji

### Pomieszczenie
Pomieszczenie dodatkowo jest charakteryzowane przez:
- `area` – powierzchnia w m^2
- `cube` – kubatura pomieszczenia w m^3
- `heating` – poziom zużycia energii ogrzewania (float)
- `light` – łączna moc oświetlenia