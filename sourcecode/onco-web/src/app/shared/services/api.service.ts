import { Injectable } from '@angular/core';
import { HttpClient } from './http-client';

@Injectable()
export class ApiService {
    constructor(private http: HttpClient) {}

    post(url: string, request: any, isSkip: boolean): Promise<any> {
        return this.http
            .post(url, request, isSkip)
            .toPromise()
            .then(res => this.extractData(res))
            .catch(this.handleError);
    }

    private extractData(res: any) {
        const body = res.json();
        return body || {};
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }
}
