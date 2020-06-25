import { Pipe, PipeTransform } from '@angular/core';

const PADDING = '000000';

@Pipe({ name: 'formatCurrency' })
export class CurrencyFormatterPipe implements PipeTransform {

    private DECIMAL_SEPARATOR: string;
    private THOUSANDS_SEPARATOR: string;

    constructor() {
        this.DECIMAL_SEPARATOR = '.';
        this.THOUSANDS_SEPARATOR = ',';
    }

    public transform(value: number | string, comma: boolean = true, fractionSize: number = 2): string {
        let [integer, fraction = ''] = (value || '').toString().replace(/[^0-9\.]+/g, '').toString().split(this.DECIMAL_SEPARATOR);

        integer = Number(integer).toString();
        fraction = fractionSize > 0 ? this.DECIMAL_SEPARATOR + (fraction + PADDING).substring(0, fractionSize) : '';

        if (integer === '') {
            integer = '0';
        } else {
            if (comma) {
                integer = integer.replace(/\B(?=(\d{3})+(?!\d))/g, this.THOUSANDS_SEPARATOR);
            } else {
                integer = integer.replace(/\B(?=(\d{3})+(?!\d))/g, '');
            }
        }

        const num = integer + fraction;
        return num;
    }

    public parse(value: string, fractionSize: number = 2): string {
        let [integer, fraction = ''] = (value || '').split(this.DECIMAL_SEPARATOR);

        integer = integer.replace(new RegExp(this.THOUSANDS_SEPARATOR, 'g'), '');

        fraction = parseInt(fraction, 10) > 0 && fractionSize > 0 ?
        this.DECIMAL_SEPARATOR + (fraction + PADDING).substring(0, fractionSize) : '';

        const num = integer + fraction;
        return num === '0' ? '' : num;
    }
}
