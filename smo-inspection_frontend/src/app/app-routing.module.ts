import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './auth/login/login.component';
import { OktaCallbackComponent, OktaAuthGuard } from '@okta/okta-angular';
import { ProtectedComponent } from './auth/protected/protected.component';
import { HomeComponent } from './home/home.component';
import { CalendarComponent } from './calendar/calendar.component'
import { SafetyquestionsComponent } from './safetyquestions/safetyquestions.component';

import { DataService } from './services/data.service';
import { EditQuestionComponent } from './edit-question/edit-question.component';


export function onAuthRequired({ oktaAuth, router }) {
  router.navigate(['/login']);
}

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'home',
    component: HomeComponent,
    resolve: {
      userClaims: DataService
    }
  },
  {
    path: 'calendar',
    component: CalendarComponent,
    resolve: {
      userClaims: DataService
    }
  },
  {
    path: 'edit',
    component: EditQuestionComponent,
    resolve: {
      userClaims: DataService
    }
  },
  {
    path: 'questions',
    component: SafetyquestionsComponent,
    resolve: {
      userClaims: DataService
    }
  },
  {
    path: 'implicit/callback',
    component: OktaCallbackComponent
  },
  {
    path: 'protected',
    component: ProtectedComponent,
    data: { onAuthRequired }
  }
];

// Require authentication on every route
routes.forEach(route => {
 route.canActivate = route.canActivate || [];
 route.canActivate.push(OktaAuthGuard);
});

@NgModule({
  imports: [RouterModule.forRoot(routes, { initialNavigation: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
