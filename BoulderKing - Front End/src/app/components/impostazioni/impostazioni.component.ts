import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-impostazioni',
  templateUrl: './impostazioni.component.html',
  styleUrls: ['./impostazioni.component.scss']
})
export class ImpostazioniComponent implements OnInit {
  currentTheme: string = 'tema-default';

  constructor() { }

  ngOnInit(): void {
  }
}

