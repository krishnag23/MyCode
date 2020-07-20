import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { OktaAuthModule } from '@okta/okta-angular';

import { MainBodyComponent } from './main-body/main-body.component';
import { HomeComponent } from './home/home.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { environment } from '../environments/environment';
import { CalendarComponent } from './calendar/calendar.component';
import { SafetylevelComponent } from './safetylevel/safetylevel.component';
import { QuestionmainComponent } from './questionmain/questionmain.component';
import { HeaderquestionComponent } from './headerquestion/headerquestion.component';
import { NooptionsComponent } from './nooptions/nooptions.component';
import { SafetyquestionsComponent } from './safetyquestions/safetyquestions.component';
import {MatListModule,} from '@angular/material/list';
import {MatSelectModule} from '@angular/material/select';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import {MatRadioModule} from '@angular/material/radio';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import { EditQuestionComponent } from './edit-question/edit-question.component';

import { NgxUiLoaderModule, NgxUiLoaderConfig } from 'ngx-ui-loader';
import { ToastrModule } from 'ngx-toastr';

const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  bgsColor: 'red',
  // bgsOpacity: 0.5,
  // bgsPosition: POSITION.bottomLeft,
  // bgsSize: 60,
  // bgsType: SPINNER.chasingDots,
  // blur: 5,
  // delay: 0,
  fastFadeOut: true,
  // fgsColor: 'red',
  // fgsPosition: POSITION.centerCenter,
  // fgsSize: 60,
  // fgsType: SPINNER.chasingDots,
  // gap: 24,
  // logoPosition: POSITION.centerCenter,
  // logoSize: 120,
  // logoUrl: 'assets/angular.png',
  // overlayBorderRadius: '0',
  // overlayColor: 'rgba(40, 40, 40, 0.8)',
  // pbColor: 'red',
  // pbDirection: PB_DIRECTION.leftToRight,
  pbThickness: 0,
  hasProgressBar: false
  // text: 'Welcome to ngx-ui-loader',
  // textColor: '#FFFFFF',
  // textPosition: POSITION.centerCenter,
  // maxTime: -1,
  // minTime: 500
};

@NgModule({
  declarations: [
    AppComponent,
    MainBodyComponent,
    HomeComponent,
    CalendarComponent,
    SafetylevelComponent,
    HeaderquestionComponent,
    QuestionmainComponent,
    NooptionsComponent,
    SafetyquestionsComponent,
    EditQuestionComponent
  ],
  imports: [

    OktaAuthModule.initAuth(environment.oktaConfig),
    MatListModule,
    MatSelectModule,
    MatDatepickerModule,MatNativeDateModule, MatInputModule,
    MatRadioModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig),
    ToastrModule.forRoot()
  ],
  providers: [
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
