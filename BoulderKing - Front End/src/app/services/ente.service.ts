import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.interface';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EnteService {
  private urlenti = 'http://localhost:3001/enti';

  constructor(private http: HttpClient) { }


  // CARICA LA PELESTRA DATO IL SUO ID
  getEnte(id : string){
    return this.http.get<User>(`${this.urlenti}/${id}`);
  }

  // CARICA LA PELESTRA DATO IL NOME
  getEntiByNome(nomeEnte: string, page: number, size: number): Observable<User[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
  
    return this.http.get<any>(`${this.urlenti}/nome/${nomeEnte}`, { params, headers })
      .pipe(map(response => response.content));
  }
  

  // Carica Enti tramite filtri
  searchEnti(filters: any, page: number, size: number, sortBy: string): Observable<User[]> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy);
  
    // Definisci una funzione di utilità per impostare il parametro se è definito in filters
    const setParamIfDefined = (paramName: string, filterValue: string | null) => {
      if (filterValue) {
        params = params.set(paramName, filterValue);
      }
    };
  
    // Usa la funzione di utilità per impostare i parametri
    setParamIfDefined('nomeEnte', filters.nomeEnte);
    setParamIfDefined('citta', filters.citta);
    setParamIfDefined('provincia', filters.provincia);
    setParamIfDefined('regione', filters.regione);
    setParamIfDefined('tipoEnte', filters.tipoEnte);
    setParamIfDefined('zonaItalia', filters.zonaItalia);
  
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });
  
    return this.http.get<any>(`${this.urlenti}/search`, { params, headers })
      .pipe(map(response => response.content));
  }  
}
