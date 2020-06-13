import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  ratesAllendpoint = 'http://localhost:4000/rates/ratesall';
  rateEndpoint = 'http://localhost:4000/rates/rate/';
  ratePairEndpoint = 'http://localhost:4000/rates/rate/pair/';
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
    return this.http.get(this.ratesAllendpoint).pipe(map(this.extractData));
  }

  getRate(currencyType): Observable<any> {
    return this.http
      .get(this.rateEndpoint + currencyType)
      .pipe(map(this.extractData));
  }

  getRatePair(sourceType, targetType): Observable<any> {
    return this.http
      .get(this.ratePairEndpoint + sourceType + '/' + targetType)
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
