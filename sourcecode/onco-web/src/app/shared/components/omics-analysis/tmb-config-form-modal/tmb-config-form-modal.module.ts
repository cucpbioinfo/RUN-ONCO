import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TmbConfigFormModalComponent } from "./tmb-config-form-modal.component";
import { SharedDirectivesModule } from '../../../directives/shared-directives.module';
import { TranslateModule } from "@ngx-translate/core";
import { ModalModule } from 'ngx-bootstrap/modal';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedServicesModule } from '../../../services/shared-services.module';

@NgModule({
    imports: [
        CommonModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        ModalModule
    ],
    declarations: [
        TmbConfigFormModalComponent
    ],
    entryComponents: [
        TmbConfigFormModalComponent
    ]
})
export class TmbConfigFormModalModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: TmbConfigFormModalComponent,
            providers: [

            ]
        };
    }
}
