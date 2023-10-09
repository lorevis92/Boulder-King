import { Component, OnInit } from '@angular/core';
import { Classifica } from 'src/app/models/classifica.interface';
import { ClassificaService } from 'src/app/services/classifica.service';

import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-classifica-evento',
  templateUrl: './classifica-evento.component.html',
  styleUrls: ['./classifica-evento.component.scss']
})
export class ClassificaEventoComponent implements OnInit {
  page = 0; // Imposta la pagina iniziale
  pageSize =10;
  idEvento!: string;
  classifica!: Classifica;
  constructor(private classificaServ: ClassificaService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idEvento = params['id'];
  });
    this.loadClassifica();
  }

  // CARICA LA CLASSIFICA CORRISPONDENTE AD UN DATO EVENTO
  loadClassifica(): void{
    this.classificaServ.getClassificaPerEvento(this.idEvento).subscribe((classifica: Classifica) =>{
      console.log(classifica);
      this.classifica = classifica;
    },
    (error) => {
      console.error("Error fetching users:", error);
    }
    );
  }
 
}

