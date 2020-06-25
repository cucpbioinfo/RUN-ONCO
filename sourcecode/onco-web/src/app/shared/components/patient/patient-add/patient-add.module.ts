import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { PatientAddComponent } from "./patient-add.component";
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedServicesModule } from '../../../../shared/services/shared-services.module';
import { SharedDirectivesModule } from '../../../../shared/directives/shared-directives.module';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
    imports: [
        CommonModule,
        TypeaheadModule,
        FormsModule,
        ReactiveFormsModule,
        SharedServicesModule,
        SharedDirectivesModule,
        TranslateModule.forChild()
    ],
    declarations: [
        PatientAddComponent
    ],
    exports: [
        PatientAddComponent
    ]
})
export class PatientAddModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: PatientAddModule,
            providers: [

            ]
        };
    }
}
