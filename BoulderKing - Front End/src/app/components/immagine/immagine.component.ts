import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user.interface';
import { ImmaginiService } from 'src/app/services/immagini.service';
import { UserService } from 'src/app/services/user.service';
import { ToastComponent } from '../toast/toast.component';

@Component({
  selector: 'app-immagine',
  templateUrl: './immagine.component.html',
  styleUrls: ['./immagine.component.scss']
})
export class ImmagineComponent implements OnInit {
  
  user!: User;
  userData: any;
  userId!: string;
  @ViewChild('errorToast') errorToast!: ToastComponent;
  
  constructor(private userServ: UserService, private router: Router, private httpClient: HttpClient, private route: ActivatedRoute,private imgServ: ImmaginiService) { }
  uploadedImage!: File;
  id!: string;
  dbImage: any;
  postResponse: any;
  successResponse!: string;
  image: any;
  eventoId!: string ; 

  ngOnInit(): void {
    this.userData = this.userServ.getUserData();
    console.log(this.userData);
    this.userId = this.userData.sub;
    // Recupero le info di User
    this.userServ.getUser(this.userId).subscribe(
      user => {
        this.user = user;
        console.log(this.user); 
      },
      error => console.error('Errore durante il recupero dell\'utente:', error)
    );
  }

  public onImageUpload(event: Event) {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files.length > 0) {
      this.uploadedImage = target.files[0];
    }
  }
  

  imageUploadAction() {
    const imageFormData = new FormData();
    imageFormData.append('image', this.uploadedImage, this.uploadedImage.name);
    
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });

    let url = 'http://localhost:3001/upload/image';
  
    

    if (this.user.immagineProfilo){
      // PUT request to update existing image
      url += `/${this.user.immagineProfilo.id}`;
      this.httpClient.put(url, imageFormData, { headers, observe: 'response' })
      .subscribe((response) => {
        if (response.status === 200) {
          this.successResponse = 'Image updated successfully!';
        }
    },
    (error) => {
      this.successResponse = 'Image not updated due to some error!';
      this.errorToast.open();
      console.error('Errore durante l\'aggiornamento dell\'immagine:', error);
    });

    } else{
      url += `?userId=${this.userId}`;
      this.httpClient.post(url, imageFormData, { headers, observe: 'response' })
      .subscribe((response) => {
        if (response.status === 200) {
          this.postResponse = response;
          this.successResponse = this.postResponse.body.message;
        } 
      },
      (error) => {
        this.successResponse = 'Image not uploaded due to some error!';
        this.errorToast.open(); // Apri il toast di errore in caso di errore HTTP
        console.error('Errore durante il caricamento dell\'immagine:', error);
      });
    }
    
  }

}
