import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AppStateService } from './app-state.service';
import { Request, XHRBackend, RequestOptions, Response, Http, RequestOptionsArgs } from '@angular/http';
import { Observable, EMPTY, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class AuthenticatedHttpService extends Http {

    constructor(backend: XHRBackend, defaultOptions: RequestOptions, public router: Router, public appState: AppStateService) {
            super(backend, defaultOptions);
    }

    request(url: string | Request, options?: RequestOptionsArgs): Observable<Response> {
        return super.request(url, options).pipe(
            catchError(error => {
                if (error.status === 401 || error.status === 403) {
                    console.log('The authentication session expires or the user is not authorised. Force to redirect to login page.');
                    this.appState.userInfo = undefined;
                    this.router.navigate(['/login']);
                    return EMPTY;
                } else {
                    return throwError(error);
                }
            })
        );
    }
}
