import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientFormRoutingModule } from "./patient-form-routing.module";
import { PatientFormComponent } from "./patient-form.component";
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from '@ngx-translate/core';
import { DataTableModule } from '../../../shared/components/data-table';
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { CfrStepProcessModule } from "../../../shared/components/step-process/clinical-data/crf-step-process.module";
import { StepWizardModule } from "../../../shared/components/step-wizard/step-wizard.module";
import { VcStepProcessModule } from "../../../shared/components/step-process/variant-call/vc-step-process.module";
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";
import { RnaSeqStepProcessModule } from "../../../shared/components/step-process/rna-seq/rna-seq-step-process.module";

@NgModule({
    imports: [
        CommonModule,
        PatientFormRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule,
        DataTableModule,
        BsDatepickerModule,
        ModalModule,
        TypeaheadModule,
        CfrStepProcessModule,
        StepWizardModule,
        VcStepProcessModule,
        BreadcrumbModule,
        RnaSeqStepProcessModule
    ],
    declarations: [
        PatientFormComponent
    ],
    exports: [
        PatientFormComponent
    ]
})
export class PatientFormModule { }
