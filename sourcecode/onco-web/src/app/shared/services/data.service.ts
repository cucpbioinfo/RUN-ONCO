import { Injectable } from '@angular/core';
import { Constants } from './constants';
import { MessageCode } from './message-code';
import { BaseService } from './base.service';
import { ApiService } from './api.service';
import { DateService } from './date.service';
import { UtilService } from './util.service';
import { HttpClient } from './http-client';
import { map } from 'rxjs/internal/operators/map';

@Injectable()
export class DataService extends BaseService {

    constructor(public constants: Constants,
        public msg: MessageCode,
        public api: ApiService,
        public dateService: DateService,
        public util: UtilService,
        public http: HttpClient
    ) {
        super(constants, api, dateService, util);
    }

    public filter(ctx: string, serviceName: string, data: any, isSkip = false): any {

        const dt = this.dateService.getCurrentDate();
        const refNo = this.util.getRefNo(dt);
        const txnDate = this.util.getTxnDate(dt);

        let serviceUri = serviceName;
        if (!this.util.isNullOrEmpty(ctx)) {
            serviceUri = `${ctx}/${serviceName}`;
        }

        const req: any = {
            header: {
                serviceName: serviceName,
                systemCode: this.constants.SYSTEM_CODE,
                channelId: this.constants.CHANNEL_ID,
                referenceNo: refNo,
                transactionDate: txnDate
            },
            data: data
        };

        return this.http.post(`${this.constants.APP_BASE_URL}/${serviceUri}`, req, isSkip).pipe(
            map((res: any) => {
                return res.json();
        }));
    }
}
