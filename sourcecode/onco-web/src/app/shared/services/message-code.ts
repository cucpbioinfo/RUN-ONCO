import { Injectable } from '@angular/core';

@Injectable()
export class MessageCode {

    constructor() { }

    SUCCESS = { code: 'ONC-SCC000', desc: 'Service Success.' };
    ERROR_DATA_NOT_FOUND = { code: 'ONC-ERR003', desc: 'Data not found.' };
    ERROR_DUPLICATED = { code: 'ONC-ERR006', desc: 'Duplicate data exist.' };
}
