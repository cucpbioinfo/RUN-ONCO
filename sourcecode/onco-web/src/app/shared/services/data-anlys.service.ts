import { Injectable } from '@angular/core';
import { HttpClient } from './http-client';
import { Constants } from '../../shared/services/constants';
import { map } from 'rxjs/internal/operators/map';

@Injectable()
export class DataAnlysService {

    constructor(private http: HttpClient,
        private constants: Constants
    ) {
    }

    public connect(serviceName: string, req: any, isSkip = false): any {
        return this.http.post(`${this.constants.DATA_ANLYS_BASE_URL}/${serviceName}`, req, isSkip).pipe(
            map((res: any) => {
                return res.json();
        }));
    }
}
