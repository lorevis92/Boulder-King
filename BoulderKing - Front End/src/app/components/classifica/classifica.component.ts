import { Component, OnInit } from '@angular/core';
import { Classifica } from 'src/app/models/classifica.interface';
import { ClassificaService } from 'src/app/services/classifica.service';

@Component({
  selector: 'app-classifica',
  templateUrl: './classifica.component.html',
  styleUrls: ['./classifica.component.scss']
})
export class ClassificaComponent implements OnInit {

  page = 0; // Imposta la pagina iniziale
  pageSize =10;
  classifiche: Classifica[] = [];

  constructor(private classificaServ: ClassificaService) { }

  ngOnInit(): void {
    //this.loadClassifiche();
  }

  loadClassifiche(): void{
    this.classificaServ.getClassifiche(this.page, 'id').subscribe((classifiche: Classifica[]) =>{
      console.log(this.classifiche);
      this.classifiche = classifiche;
    },
    (error) => {
      console.error("Error fetching users:", error);
    }
    );
  }

}
