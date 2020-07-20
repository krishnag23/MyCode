import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OktaAuthService } from '@okta/okta-angular';
import { Resolve} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class DataService implements Resolve<any> {

  constructor( private oktaService: OktaAuthService) { }

  resolve(): Observable<any>|Promise<any>|any {
    return this.oktaService.getUser();
  }

}
