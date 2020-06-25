import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedServicesModule } from '../../../services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../directives/shared-directives.module';
import { TranslateModule } from "@ngx-translate/core";
import { ModalModule } from 'ngx-bootstrap/modal';
import { VariantAnalysisFormComponent } from "./variant-analysis-form.component";

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
        VariantAnalysisFormComponent,
    ],
    exports: [
        VariantAnalysisFormComponent
    ]
})
export class VariantAnalysisFormModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: VariantAnalysisFormModule,
            providers: [

            ]
        };
    }
}
