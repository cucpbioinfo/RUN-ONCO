import { Injectable } from '@angular/core';
import { HttpClient } from './http-client';
import { Constants } from '../../shared/services/constants';
import { map } from 'rxjs/internal/operators/map';
import { DateService } from './date.service';
import { UtilService } from './util.service';

@Injectable()
export class AuthService {

    constructor(private http: HttpClient,
        private constants: Constants,
        private dateService: DateService,
        private util: UtilService
    ) {
    }

    public login(username: string, passwd: string, isSkip = false): any {

        const dt = this.dateService.getCurrentDate();
        const refNo = this.util.getRefNo(dt);
        const txnDate = this.util.getTxnDate(dt);

        const req: any = {
            header: {
                serviceName: this.constants.SERVICE_NAME.LOGIN,
                systemCode: this.constants.SYSTEM_CODE,
                channelId: this.constants.CHANNEL_ID,
                referenceNo: refNo,
                transactionDate: txnDate
            },
            data: {
                username: username,
                password: passwd
            }
        };

        return this.http.post(`${this.constants.APP_BASE_URL}/${req.header.serviceName}`, req, isSkip).pipe(
            map((res: any) => {
                return res.json();
        }));
    }
}
