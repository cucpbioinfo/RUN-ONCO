import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewRnaSeqRoutingModule } from './view-rna-seq-routing.module';
import { ViewRnaSeqComponent } from './view-rna-seq.component';
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from "@ngx-translate/core";
import { RnaSeqStepProcessModule } from "../../../shared/components/step-process/rna-seq/rna-seq-step-process.module";
import { DataTableModule } from "../../../shared/components/data-table";
import { ViewPatientInfoModule } from "../../../shared/components/patient/view-patient-info/view-patient-info.module";
import { ViewBiospecimenModule } from "../../../shared/components/biospecimen/view-biospecimen/view-biospecimen.module";
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";

@NgModule({
    imports: [
        CommonModule,
        ViewRnaSeqRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        RnaSeqStepProcessModule,
        DataTableModule,
        ViewPatientInfoModule,
        ViewBiospecimenModule,
        BreadcrumbModule
    ],
    declarations: [
        ViewRnaSeqComponent
    ]
})
export class ViewRnaSeqModule { }
