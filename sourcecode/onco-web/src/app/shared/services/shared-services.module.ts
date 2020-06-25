import { NgModule, ModuleWithProviders } from "@angular/core";
import { HttpModule } from "@angular/http";
import { ApiService } from "../services/api.service";
import { AppStateService } from "../services/app-state.service";
import { AuthService } from "../services/auth.service";
import { Constants } from "../services/constants";
import { DateService } from '../services/date.service';
import { HttpClient } from '../services/http-client';
import { MessageCode } from '../services/message-code';
import { UtilService } from '../services/util.service';
import { ValidationService } from '../services/validation.service';
import { PreloadService } from '../services/preload.service';
import { ModalService } from '../services/modal.service';
import { DataService } from "../services/data.service";
import { TranslateModule } from "@ngx-translate/core";
import { DataAnlysService } from "../services/data-anlys.service";
import { DownloadService } from "../services/download.service";
import { UploadService } from "../services/upload.service";

@NgModule({
    imports: [
        HttpModule,
        TranslateModule.forChild()
    ],
    providers: [
    ],
    exports: [
    ]
})
export class SharedServicesModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SharedServicesModule,
            providers: [
                ApiService,
                AppStateService,
                AuthService,
                Constants,
                DateService,
                HttpClient,
                MessageCode,
                UtilService,
                ValidationService,
                PreloadService,
                ModalService,
                DataService,
                DataAnlysService,
                DownloadService,
                UploadService
            ]
        };
    }
}
