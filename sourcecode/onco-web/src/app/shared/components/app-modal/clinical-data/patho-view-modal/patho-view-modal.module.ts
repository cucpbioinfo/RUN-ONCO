import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from "@ngx-translate/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { PathoViewModalComponent } from './patho-view-modal.component';
import { SharedServicesModule } from "../../../../services/shared-services.module";
import { ModalModule } from 'ngx-bootstrap/modal';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        SharedServicesModule,
        TranslateModule.forChild(),
        ModalModule
    ],
    declarations: [
        PathoViewModalComponent
    ],
    entryComponents: [
        PathoViewModalComponent
    ]
})
export class PathoViewModalModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: PathoViewModalModule,
            providers: [

            ]
        };
    }
}
