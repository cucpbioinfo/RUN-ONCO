import { Injectable } from '@angular/core';
import { Constants } from './constants';
import { MessageCode } from './message-code';
import { ApiService } from './api.service';
import { DateService } from './date.service';
import { UtilService } from './util.service';
import { HttpClient } from './http-client';
import { map } from 'rxjs/internal/operators/map';

@Injectable()
export class UploadService {

    constructor(public constants: Constants,
        public msg: MessageCode,
        public api: ApiService,
        public dateService: DateService,
        public util: UtilService,
        public http: HttpClient
    ) {
    }

    public uploadFile(ctx: string, serviceName: string, formData: FormData, isSkip = false) {
        const dt = this.dateService.getCurrentDate();
        const refNo = this.util.getRefNo(dt);
        const txnDate = this.util.getTxnDate(dt);

        let serviceUri = serviceName;
        if (!this.util.isNullOrEmpty(ctx)) {
            serviceUri = `${ctx}/${serviceName}`;
        }

        const header = {
            serviceName: serviceName,
            systemCode: this.constants.SYSTEM_CODE,
            channelId: this.constants.CHANNEL_ID,
            referenceNo: refNo,
            transactionDate: txnDate
        };

        formData.append('responseHeader', JSON.stringify(header));
        return this.http.upload(`${this.constants.APP_BASE_URL}/${serviceUri}`, formData, isSkip).pipe(
            map((res: any) => {
                return JSON.parse(res._body);
        }));
    }
}
