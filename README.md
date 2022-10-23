# Házi feladat specifikáció

Információk [itt](https://viauac00.github.io/laborok/hf)

## Mobil- és webes szoftverek
### [2022.10.23.]
### [Crypto Demo]
### [Petruska Bence] - ([JP5JDU])
### [bence.petruska@gmail.com]
### Laborvezető: [Telek Benjámin Márk]

## Bemutatás

Az alkalmazás egy kezdetleges egyszerű kripto számlát és tőzsdét valósít meg. Demo pénzzel vehetünk az általunk kiválasztott kriptovalutát és az általunk vásárolt kriptovalutákat el is adhatjuk. 
A portfoliónk profitját/veszteségét is tudjuk követni az alkalmazással.

## Főbb funkciók

Az alkalmazásunk kezdőképernyőjén jelenik meg a "watchlist", amin az általunk követett kriptovaluták ára van megjelenítve, frissítésre az árak frissülnek.
A watchlistbe felvehetünk, illetve törölhetünk is róla valutákat A cryptokról megjelenő adatokat a "coinmarketcap" nevezetű crypto ár követő oldal API-ján keresztül éri el az alkalmazás. 
A watchlist listában egy kriptovalutára rákattintva van lehetőségünk venni/eladni, megadva annak a mennyiségét is. Az összeg hozzáadás menüben van lehetőségünk további összeget hozzárendelni a fiókhoz. 
Profilunk nézetében követni tudjuk egy grafikon kimutatásunk a portfoliónk aktuális állását.
A profilunkat és a watchlist-et is perzisztens adattárolási módszerekkel tárolja. 
A profilunkon az összes eddigi feltöltött tőke mennyiségét és az általunk birtokolt kriptovalutákat és azok mennyiségét tárolja.
A watchlisten pedig az adott kripto TAG-jét és az aktuális árát tárolja.
## Választott technológiák:

- Perzisztens adattárolás
- fragmentek
- RecyclerView
- API hívással kapcsolatos technológiák (Retrofit, GSON, ...)


#Házi feladat dokumentáció (ha nincs, ez a fejezet törölhető)