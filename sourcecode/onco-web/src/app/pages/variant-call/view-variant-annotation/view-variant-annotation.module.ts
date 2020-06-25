import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewVariantAnnotationRoutingModule } from './view-variant-annotation-routing.module';
import { ViewVariantAnnotationComponent } from './view-variant-annotation.component';
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from "@ngx-translate/core";
import { VcStepProcessModule } from "../../../shared/components/step-process/variant-call/vc-step-process.module";
import { DataTableModule } from "../../../shared/components/data-table";
import { ViewPatientInfoModule } from "../../../shared/components/patient/view-patient-info/view-patient-info.module";
import { ViewBiospecimenModule } from "../../../shared/components/biospecimen/view-biospecimen/view-biospecimen.module";

@NgModule({
    imports: [
        CommonModule,
        ViewVariantAnnotationRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        VcStepProcessModule,
        DataTableModule,
        ViewPatientInfoModule,
        ViewBiospecimenModule
    ],
    declarations: [
        ViewVariantAnnotationComponent
    ]
})
export class ViewVariantAnnotationModule { }
