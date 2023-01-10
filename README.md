# U_Pass
Acesta e codul sursa.

Inca nu a fost impachetat intrun executabil, deci trebuie rulat manual.

Pentru inceput este nevoie de libraria JavaFX -> https://gluonhq.com/products/javafx/

La rulare este nevoie de niste argumente speciale pentru JavaVM -> 
java --module-path "[filepath to javafx-sdk/lib]" --add-modules javafx.controls,javafx.fxml -jar ProiectPi.jar

![image](https://user-images.githubusercontent.com/92794489/207394305-8b6929b1-81ae-4ec1-bf24-1b1daca01e6a.png)

Asa arata fereastra principala la deschiderea aplicatie, evident inca nu e gata :)

De aici se poate selecta optiunea de conectare la un server MySql (care inca nu e implementata), sau optiunea de a crea/deschide un fisier (partea asta e implementata, dar partea de citie/scriere inca nu e, pentru ca vreau sa si criptez continutul fisierului)

![image](https://user-images.githubusercontent.com/92794489/207395996-cfb3b93d-0fc2-4b89-8703-5db84f4f6f2e.png)

Dupa prima selectare, utilizatorul trebuie sa se logheze (pot fi mai multi utilizatori care folosesc acelasi fiser/server MySql). [Aici ma gandeam si la varianta de a cere doar o parola, nu si un username]

![image](https://user-images.githubusercontent.com/92794489/207396862-4c99c46b-d48c-466a-9291-676f137cd304.png)

Asta este partea principala. Aici se vor vedea conturile, si in partea de jos, se va vedea si parola (nu sunt 100% decis cum vreau sa fie partea asta).

Tabelul din cate se poate vedea functioneaza, se pot scrie/citi date.


In mare parte, cam asa doresc sa arate aplicatia. Evident vreau sa mai fac mici ajustari pentru a creea o interfata si mai buna.

In partea de backend, trebuie sa mai implementez conectarea la un server MySql, citirea/scrierea in fisiere si sa le leg cu tabelul unde vor fi afisate datele. Va trebui sa si creez butoane pentru gestionarea datelor (adaugare, stergere, modificare...) si sa implementez si functia de search.

La inceput am spus ca voi creea aplicatia cu Java Swing, dar mam decis sa folosesc Java Fx pentru a crea o interfata mai buna, in rest sa cam pastrat aceeasi idee ca si in prima prezentare.
