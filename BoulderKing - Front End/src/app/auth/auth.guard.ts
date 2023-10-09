import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';  // Assicurati che il percorso sia corretto

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private authService: AuthService, private router: Router) {}
  
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const isLogged = this.authService.isLoggedIn();
    
    if (isLogged) {
      return true;
    } else {
      alert('Non hai il permesso per accedere a questa pagina, effetua il log in!')
      this.router.navigate(['/login']);
      return false;
    }
  }
}

