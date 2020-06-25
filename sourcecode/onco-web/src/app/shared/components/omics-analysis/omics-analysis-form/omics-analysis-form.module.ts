import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedServicesModule } from '../../../services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../directives/shared-directives.module';
import { TranslateModule } from "@ngx-translate/core";
import { ModalModule } from 'ngx-bootstrap/modal';
import { OmicsAnalysisFormComponent } from "./omics-analysis-form.component";

@NgModule({
    imports: [
        CommonModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        ModalModule,
    ],
    declarations: [
        OmicsAnalysisFormComponent,
    ],
    exports: [
        OmicsAnalysisFormComponent
    ]
})
export class OmicsAnalysisFormModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: OmicsAnalysisFormModule,
            providers: [

            ]
        };
    }
}
