import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Boulder King';

  constructor(private router: Router, private httpClient: HttpClient) {}

  ngOnInit(): void {
  }

  uploadedImage!: File;
  dbImage: any;
  postResponse: any;
  successResponse!: string;
  image: any;

  

  shouldShowNavbar(): boolean {
    const currentRoute = this.router.url;
    return currentRoute !== '/login' && currentRoute !== '/register'&& currentRoute !== '/navbar' && currentRoute !== '/registerEnte';
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
  
    const userId = null;
    const eventoId = '04004f51-0285-4be3-80c2-f605e27aea88';
  
    let url = 'http://localhost:3001/upload/image';
  
    if (userId) {
      url += `?userId=${userId}`;
    }

    if (eventoId) {
      url += `?eventoId=${eventoId}`;
    }
  
    this.httpClient.post(url, imageFormData, { headers, observe: 'response' })
      .subscribe((response) => {
        if (response.status === 200) {
          this.postResponse = response;
          this.successResponse = this.postResponse.body.message;
        } else {
          this.successResponse = 'Image not uploaded due to some error!';
        }
      });
  }
 
}

