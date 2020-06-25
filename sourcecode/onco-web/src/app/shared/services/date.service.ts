import { Injectable } from '@angular/core';
import { Constants } from '../../shared/services/constants';

import * as moment from 'moment';

@Injectable()
export class DateService {

    constructor(public constants: Constants) { }

    public toString(obj: any): string {
        const date = moment(`${obj.year}-${obj.month}-${obj.day}`, this.constants.FORMAT_SHORT_DATE).toDate();
        return this.formatDate(date, this.constants.FORMAT_SHORT_DATE);
    }

    public parseDate(s: string) {
        const obj = this.parseDateString(s);
        const date = moment(`${obj.year}-${obj.month}-${obj.day}`, this.constants.FORMAT_SHORT_DATE).toDate();
        return date;
    }

    public formatDate(date: Date, datePattern: string, defaultLang: string = this.constants.KNOWN_CULTURE_ENLISH_US) {
        moment.locale(defaultLang);
        return moment(date).format(datePattern);
    }

    public getCurrentDate(defaultLang: string = this.constants.KNOWN_CULTURE_ENLISH_US) {
        moment.locale(defaultLang);
        const now = moment().toDate();
        return now;
    }

    private parseDateString(s: string): any {
        const reggie = /(\d{4})-(\d{2})-(\d{2})/;
        const dateArray = reggie.exec(s);

        if (dateArray == null) {
            return null;
        }

        const dateObject = {
            year: +dateArray[1],
            month: +dateArray[2],
            day: +dateArray[3]
        };

        return dateObject;
    }
}
