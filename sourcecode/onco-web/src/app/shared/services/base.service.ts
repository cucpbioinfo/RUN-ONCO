import { Constants } from './constants';
import { ErrorMessageBean } from '../bean/error-message-bean';
import { ApiService } from './api.service';
import { DateService } from './date.service';
import { UtilService } from './util.service';

export class BaseService {

    constructor(
        public constants: Constants,
        public api: ApiService,
        public dateService: DateService,
        public util: UtilService
    ) {
    }

    public connect(ctx: string, serviceName: string, data: any, isSkip = false) {

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

        const p = new Promise(resolve => {
            console.log(`I:--START--:--${serviceName}--`);

            this.api.post(`${this.constants.APP_BASE_URL}/${serviceUri}`, req, isSkip).then(res => {
                console.log('---> res : ', res);

                if (res.error) {
                    const errorMessage = new ErrorMessageBean(res.error.code, res.error.message);
                    console.log(`O:--FAIL--:--${serviceName}--:errorCode/${errorMessage.result}:errorDesc/${errorMessage.message}`);
                    resolve(errorMessage);
                } else {
                    resolve(res);
                    console.log(`O:--SUCCESS--:--${serviceName}--`);
                }
            });
        }).catch((ex: any) => {
            console.debug(ex);
        });

        return p;
    }
}
