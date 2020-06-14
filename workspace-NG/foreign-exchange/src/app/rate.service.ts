import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';
import { environment } from '../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class RateService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) {}

  private extractData(res: Response) {
    let body = res;
    return body || {};
  }

  getRatesList(): Observable<any> {
    return this.http
      .get(`${environment.baseURL}/rates`)
      .pipe(map(this.extractData));
  }

  getRate(currencyType): Observable<any> {
    return this.http
      .get(`${environment.baseURL}/rates/${currencyType}`)
      .pipe(map(this.extractData));
  }

  getRatePair(sourceType, targetType): Observable<any> {
    return this.http
      .get(`${environment.baseURL}/rates/${sourceType}/${targetType}`)
      .pipe(map(this.extractData));
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
