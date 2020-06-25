import { Injectable } from '@angular/core';

import * as $ from 'jquery';

@Injectable()
export class PreloadService {
    private countConnection = 0;

    constructor() {
        console.log('I:--START--:--Initial Preload--');
    }

    show() {
        this.countConnection += 1;
        console.log(`O:--SUCCESS--:--Show Loading--:countConnection/${this.countConnection}`);
        $('#dvLoading').show();
    }

    hide() {
        this.countConnection -= 1;
        console.log(`O:--SUCCESS--:--Show Hide--:countConnection/${this.countConnection}`);

        if (this.countConnection === 0) {
            $('#dvLoading').hide();
        }
    }
}
