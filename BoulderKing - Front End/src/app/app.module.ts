import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule, Route } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtModule } from '@auth0/angular-jwt';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './auth/login/login.component';

import { RegisterComponent } from './auth/register/register.component';
import { TokenInterceptor } from './auth/token.interceptor';
import { AtletaComponent } from './components/atleta/atleta.component';
import { EventoComponent } from './components/evento/evento.component';
import { ClassificaComponent } from './components/classifica/classifica.component';
import { ClassificaPrincipaleComponent } from './components/classifica/classifica-principale/classifica-principale.component';
import { EnteComponent } from './components/ente/ente.component';
import { EnteSpecificoComponent } from './components/ente/ente-specifico/ente-specifico.component';
import { EventoSpecificoComponent } from './components/evento/evento-specifico/evento-specifico.component';
import { AtletaSpecificoComponent } from './components/atleta/atleta-specifico/atleta-specifico.component';
import { NuovoEventoComponent } from './components/evento/nuovo-evento/nuovo-evento.component';
import { ClassificaEventoComponent } from './components/classifica/classifica-evento/classifica-evento.component';
import { FiltriComponent } from './components/classifica/filtri/filtri.component';
import { AggiornaClassificaComponent } from './components/classifica/aggiorna-classifica/aggiorna-classifica.component';
import { ImmagineComponent } from './components/immagine/immagine.component';
import { UserComponent } from './components/user/user.component';
import { AuthGuard } from './auth/auth.guard';
import { ToastComponent } from './components/toast/toast.component';
import { ImpostazioniComponent } from './components/impostazioni/impostazioni.component';
import { MapComponent } from './components/map/map.component';
import { FooterComponent } from './components/footer/footer.component';
import { RegisterEnteComponent } from './auth/register-ente/register-ente.component';
import { ModificaInfoEnteComponent } from './components/ente/modifica-info-ente/modifica-info-ente.component';
import { ModificaInfoAtletaComponent } from './components/atleta/modifica-info-atleta/modifica-info-atleta.component';
import { ModificaInfoEventoComponent } from './components/evento/modifica-info-evento/modifica-info-evento.component';

const rotte: Route[] = [
  { path: '',
  component: HomeComponent,
  //canActivate: [AuthGuard]
  },  
  {
    path: 'navbar',
    component: NavbarComponent,
   // canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
    
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'registerEnte',
    component: RegisterEnteComponent
  },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'impostazioni',
    component: ImpostazioniComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'evento',
    component: EventoComponent,
    //canActivate: [AuthGuard]
  },
  {
    path: 'nuovoEvento',
    component: NuovoEventoComponent,
    //canActivate: [AuthGuard]
  },
  {
    path: 'ente',
    component: EnteComponent,
    //canActivate: [AuthGuard]
  },
  {
    path: 'ente/:id',
    component: EnteSpecificoComponent,
   //canActivate: [AuthGuard]
},
{
  path: 'evento/:id',
  component: EventoSpecificoComponent,
 //canActivate: [AuthGuard]
},
  {
    path: 'atleta',
    component: AtletaComponent,
    //canActivate: [AuthGuard]
  },
  
{
  path: 'atleta/:id',
  component: AtletaSpecificoComponent,
// canActivate: [AuthGuard]
},
  {
    path: 'classifica',
    component: ClassificaComponent,
  //  canActivate: [AuthGuard]
  },
  {
    path: 'classificaPrincipale',
    component: ClassificaPrincipaleComponent,
   // canActivate: [AuthGuard]
  },
  {
    path: 'classificaFiltri',
    component: FiltriComponent,
    // canActivate: [AuthGuard]
  },
  {
    path: 'classifica-evento/:id',
    component: ClassificaEventoComponent,
   // canActivate: [AuthGuard]
  },
  {
    path: 'classifica-aggiungi-partecipanti/:id',
    component: AggiornaClassificaComponent,
   // canActivate: [AuthGuard]
  }
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    AtletaComponent,
    EventoComponent,
    ClassificaComponent,
    ClassificaPrincipaleComponent,
    EnteComponent,
    EnteSpecificoComponent,
    EventoSpecificoComponent,
    AtletaSpecificoComponent,
    NuovoEventoComponent,
    ClassificaEventoComponent,
    FiltriComponent,
    AggiornaClassificaComponent,
    ImmagineComponent,
    UserComponent,
    ToastComponent,
    MapComponent,
    FooterComponent,
    RegisterEnteComponent,
    ModificaInfoEnteComponent,
    ModificaInfoAtletaComponent,
    ModificaInfoEventoComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(rotte),
    JwtModule.forRoot({
      config: {
        tokenGetter: () => localStorage.getItem('token'), // Modifica 'access_token' con 'token'
        allowedDomains: ['localhost:3001'], // Rimuovi 'http://' e '/auth/login'
        disallowedRoutes: []
      }
    })
  ],
  providers: [
  
  ],
  bootstrap: [AppComponent]
})
export class AppModule { 
}
