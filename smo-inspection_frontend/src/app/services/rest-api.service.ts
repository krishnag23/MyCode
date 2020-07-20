import { Injectable } from '@angular/core';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RestApiService {

  constructor(
    private httpClient: HttpClient
  ) { }

  public getData() {
    return this.httpClient.get('/smo/v1/').pipe(catchError(this.handleError));
  }

  public getListOfReport(level: number) {
    return this.httpClient.get(`/smo/v1/getListOfReport/${level}`).pipe(catchError(this.handleError));
  }

  public saveReport(reportData: any) {
    return this.httpClient.post('/smo/v1/', reportData).pipe(catchError(this.handleError));
  }
  public getReportForEdit(id:number){
    return this.httpClient.get(`/smo/v1/getQuestionsDataForEdit/${id}`).pipe(catchError(this.handleError));
  }


  public updateReport(reportData: any) {
    return this.httpClient.put('/smo/v1/', reportData).pipe(catchError(this.handleError));
  }
  handleError(error: HttpErrorResponse) {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      // Client-side errors
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side errors
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  }
}
