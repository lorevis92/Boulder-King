import { Component, OnInit, Input } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

// Dichiara l'oggetto 'google' come variabile globale
declare var google: any;
const PLACES_API_KEY = "AIzaSyCJ572aOuiFdh1FQcIsFTojM8xjKSh3cKg";
const PLACES_API_URL = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {
  map: any;
  @Input() coordinate!: { lat?: number, lng?: number }; // Aggiungi Input per le coordinate
  googleMapsLink = '';
  GOOGLE_MAPS_BASE_URL = 'https://www.google.com/maps/place/';
  
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.initMap();
  }

  initMap(): void {
    // Imposta le coordinate del punto di interesse (ad esempio, Roma)
    const location = { lat: this.coordinate?.lat , lng: this.coordinate?.lng };

    // Crea un oggetto mappa
    this.map = new google.maps.Map(document.getElementById('map'), {
      center: location, // Imposta il centro della mappa sul punto di interesse
      zoom: 15
    });

    // Crea un segnaposto (marker) per il punto di interesse
    const marker = new google.maps.Marker({
      position: location,
      map: this.map,
      title: 'Punto di Interesse' // Titolo del marker (facoltativo)
    });

    // Aggiungi un listener per l'evento di click sulla mappa
    this.map.addListener('click', () => {
      // Qui puoi definire il percorso del link da aprire quando la mappa viene cliccata
      this.googleMapsLink = `${this.GOOGLE_MAPS_BASE_URL}${location.lat},${location.lng}`;
  
      window.open(this.googleMapsLink, '_blank'); // Cambia questa URL con il tuo link desiderato
    });
  }

}
