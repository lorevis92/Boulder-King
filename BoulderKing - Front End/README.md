# BoulderKing

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.2.11.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.

Boulder King - README

Descrizione

Boulder King è una piattaforma per la gestione di eventi di arrampicata e bouldering. Questo progetto è stato sviluppato come parte del capstone project e utilizza Angular 14 per il front end e Spring Boot per il back end, con un database PostgreSQL (PgAdmin4).

Sono state create due repository diverse, una per il front end ed una per il back end, in questa repository si trova il front end. Il back end è disponibile a questo link: (https://github.com/lorevis92/Boulder-King/tree/Version-1)

Prerequisiti Node.js: Installare Node.js da https://nodejs.org/ per eseguire l'app Angular. Java Development Kit (JDK): Installare JDK per eseguire l'app Spring Boot. PostgreSQL: Installare PostgreSQL e PgAdmin4 per gestire il database. È possibile scaricare PostgreSQL da https://www.postgresql.org/download/. Eclipse (o un IDE a scelta) per l'ambiente di sviluppo Spring Boot.

Setup del Progetto Clonare il repository da GitHub. git clone https://github.com/lorevis92/boulder-king.git per il back end git clone https://github.com/lorevis92/boulder-king-front-end.git per il front end Backend: Aprire il progetto Spring Boot in Eclipse o nell'IDE preferito e avviare l'applicazione BoulderKingApplication.java. Database: Creare un database PostgreSQL chiamato "boulder_king" e configurare le credenziali nel file application.properties dell'app Spring Boot. Configurazione: Verificare e aggiustare le configurazioni nel file application.properties.

Avvio dell'Applicazione Frontend: eseguire l'app Angular. ng serve per avviare l'applicazione Backend: Avviare l'applicazione Spring Boot dall'IDE. Accedere all'applicazione dal browser all'indirizzo http://localhost:4200.

Documentazione API La documentazione API è disponibile all'indirizzo https://documenter.getpostman.com/view/28856704/2s9YJaXiL4

API Esterne Per sviluppare il progetto ho incorporato l'utilizzo delle API Google in due fasi: 1) Raccolta dei dati sui punti di arrampicata in Italia 2) Visualizzazione della posizione di ogni punto di arrampicata nella pagina specifica

Funzionalità di Boulder King Boulder King è un'applicazione che permette di visionare al pubblico tutte i punti di arrampicata presenti in italia, gli eventi che sono stati organizzati e le statistiche di tutti gli utenti iscritti. Boulder King è stato progettato con un design immersivo e specializzato che utlizza colori volutamente accesi e passionali. Il linguaggio e la scelta dei nomi delle pagine è stato intenzionale al fine di suggerire una sana competitività che possa far appassionare i partecipanti. Interessante è il sistema automatico di gestione delle classifiche: Vi sono classifiche specifiche per ogni zona d'Italia in maniera tale che ogni zona possa avere il proprio King Ogni volta che viene aggiornata una classifica il punteggio di ogni concorrente aumenta in base alla sua posizione ed anche alla sua partecipazione. Si è fatto si che anche solo partcipando ad un evento si ottengano dei punti in maniera da valorizzare l'impegno e la passione che gli i partecipanti hanno per questo sport.

Future evoluzioni del progetto Vi è la volonta mia come sviluppatore ed ideatore della piattoforma ed alcuni collaboratori appassionati di arrampicata di far crescere BOULDER KING Il dominio www.boulderking.it è in nostro possesso una volta puvvlicata l'applicazione farla conoscere ad altri appassionati del settore per vedere se può avere un feedback del mercato Propensione a sviluppare una seizione GRUPPI che i insoli utenti possono creare per vedere chi è il Boulder King all'interno della cerchia di amici/compagni di arrampicata

BOULDER KING: Capstone Project Full-Stack Web Developer di fine corso Epicode di Lorenzo Gabriele Visconti

Contatti: Mail lorenzo.visconti@hotmail.it LinkedIn https://www.linkedin.com/in/lorenzovisconti
