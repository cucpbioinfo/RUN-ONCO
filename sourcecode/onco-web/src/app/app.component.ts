import { Component, OnInit } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";
import { Constants } from "./shared/services/constants";
import { MessageCode } from "./shared/services/message-code";
import { AppStateService } from "./shared/services/app-state.service";
import { DataService } from "./shared/services/data.service";

@Component({
    selector: "app-root",
    templateUrl: "./app.component.html",
    styleUrls: ["./app.component.scss"]
})
export class AppComponent implements OnInit {

    constructor(
        private translate: TranslateService,
        private constants: Constants,
        private msg: MessageCode,
        private appState: AppStateService,
        private dataService: DataService
    ) {
        // this language will be used as a fallback when a translation isn't found in the current language
        this.translate.setDefaultLang('en');

        // the lang to use, if the lang isn't available, it will use the current loader to get them
        this.translate.use('en');
    }

    ngOnInit(): void {
        console.log('I:--START--:--OnLoad AppComponent--');
        this.init();
    }

    private init(): void {
        this.dataService.connect('', this.constants.SERVICE_NAME.GET_DYNAMIC_CONTENT_LIST, {})
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.appState.dynamicComponents = res.data.items;
                }
            });
    }
}
