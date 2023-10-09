import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  
  page = 0; // Imposta la pagina iniziale
  pageSize =10;
  users: User[] = [];

  constructor(private userServ: UserService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

loadUsers(): void{
  this.userServ.getUsers(this.page, 'email').subscribe((users: User[]) =>{
    console.log(this.users);
    this.users = users;
  },
  (error) => {
    console.error("Error fetching users:", error);
  }
  );
}

}
