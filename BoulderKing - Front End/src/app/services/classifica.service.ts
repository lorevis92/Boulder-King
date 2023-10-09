import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs';
import { Classifica } from '../models/classifica.interface';
import { User } from '../models/user.interface';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ClassificaService {

  private urlclassifiche = 'http://localhost:3001/classifiche';

  constructor(private http: HttpClient) { }

  // TROVA TUTTE LE CLASSIFICHE
  getClassifiche(page:Number, order:string): Observable<Classifica[]> {
    const params = new HttpParams()
  
    .set('page', page.toString())
    .set('order', order)
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<any>(this.urlclassifiche, { params, headers })
      .pipe(map(response => response.content));
  }


  // TROVA CLASSIFICHE DATO L'id DELL'EVENTO
  getClassificaPerEvento(idEvento: string): Observable<Classifica> {
    const params = new HttpParams()
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
    return this.http.get<any>(this.urlclassifiche + '/evento/' + idEvento, { params, headers })
    .pipe(
      tap(response => console.log('Response:', response)), // Log della risposta
      map(response => response)
    );
  }

  // INVIA I DATI CON CUI RICAVARE LE CLASSIFICHE PERSONALIZZATE
  inviaDati(idEvento:String): Observable<any> {
      const newClassificaRequest = { idEvento};
    return this.http.post<any>('http://localhost:3001/', newClassificaRequest);
  }

  // CREA CLASSIFICA
  creaClassifica(): Observable<any> {
  return this.http.post<any>(this.urlclassifiche , null);
}

// AGGIORNA EVENTO CLASSIFICA
aggiornaClassifica(idClassifica: string, idEvento: string): Observable<any>{
  const evento = {"evento" : idEvento};
  return  this.http.put<any>(this.urlclassifiche + '/' + idClassifica, evento );
}

// AGGIORNO ATLETI CLASSIFICA
aggiornaPosizioniClassifica(idClassifica: string, posizione01: string, posizione02: string, posizione03: string, posizione04: string, posizione05: string, posizione06: string, posizione07: string, posizione08: string, posizione09: string, posizione10: string): Observable<any>{
  const podio = {
  "posizione01" : posizione01,
  "posizione02" : posizione02,
  "posizione03" : posizione03,
  "posizione04" : posizione04,
  "posizione05" : posizione05,
  "posizione06" : posizione06,
  "posizione07" : posizione07,
"posizione08" : posizione08,
"posizione09" : posizione09,
"posizione10" : posizione10,};
  return  this.http.put<any>(this.urlclassifiche + '/' + idClassifica, podio );
}


// TROVA L'EVENTO ASSOCIATO ALLA CLASSIFICA
trovaEvento(idClassifica: string){
  return   this.http.get<any> ("http://localhost:3001/eventi/classifica/" + idClassifica)
  .pipe(map(response => response));
}




// CARICA GLI ATLETI PER COSTRUIRE LA CLASSIFICA
searchAtleti(filters: any, page: number, pageSize: number, sortBy: string, sortOrder: string): Observable<User[]> {
  let params = new HttpParams()
    .set('page', page.toString())
    .set('size', pageSize.toString())
    .set('sortBy', sortBy)
    .set('sortOrder', sortOrder);

  // Definisci una funzione di utilità per impostare il parametro se è definito in filters
  const setParamIfDefined = (paramName: string, filterValue: string | null) => {
    if (filterValue) {
      params = params.set(paramName, filterValue);
    }
  };

  // Usa la funzione di utilità per impostare i parametri
    //setParamIfDefined('nomeEnte', filters.nomeEnte);
  setParamIfDefined('citta', filters.citta);
  setParamIfDefined('provincia', filters.provincia);
  setParamIfDefined('regione', filters.regione);
  setParamIfDefined('tipoEnte', filters.tipoEnte);
  setParamIfDefined('zonaItalia', filters.zonaItalia);

  const headers = new HttpHeaders({
    Authorization: `Bearer ${localStorage.getItem('token')}`
  });

  return this.http.get<any>(`${this.urlclassifiche}/search`, { params, headers })
    .pipe(map(response => response.content));
}  

}
