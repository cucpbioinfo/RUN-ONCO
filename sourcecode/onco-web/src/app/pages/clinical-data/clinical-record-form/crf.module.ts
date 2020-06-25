import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { CrfComponent } from './crf.component';
import { CrfStep1Component } from './crf-step1.component';
import { CrfStep2Component } from './crf-step2.component';
import { CrfRoutingModule } from './crf-routing.module';
import { CfrStepProcessModule } from "../../../shared/components/step-process/clinical-data/crf-step-process.module";
import { ModalModule } from 'ngx-bootstrap/modal';
import { ViewPatientInfoModule } from "../../../shared/components/patient/view-patient-info/view-patient-info.module";
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";
import { PatientAddModule } from "../../../shared/components/patient/patient-add/patient-add.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        TranslateModule.forChild(),
        CrfRoutingModule,
        CfrStepProcessModule,
        ModalModule,
        ViewPatientInfoModule,
        BreadcrumbModule,
        PatientAddModule
    ],
    declarations: [
        CrfComponent,
        CrfStep1Component,
        CrfStep2Component,
    ],
    exports: [
        CrfComponent,
        CrfStep1Component,
        CrfStep2Component,
    ]
})
export class CrfModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: CrfModule,
            providers: [

            ]
        };
    }
}
