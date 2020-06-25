import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IndivPatientAnalysisRoutingModule } from './indiv-patient-analysis-routing.module';
import { IndivPatientAnalysisComponent } from './indiv-patient-analysis.component';
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from "@ngx-translate/core";
import { ModalModule } from 'ngx-bootstrap/modal';
import { ViewPatientInfoModule } from "../../../shared/components/patient/view-patient-info/view-patient-info.module";
import { StepWizardModule } from "../../../shared/components/step-wizard/step-wizard.module";
import { OmicsAnalysisFormModule } from "../../../shared/components/omics-analysis/omics-analysis-form/omics-analysis-form.module";
import { DynamicContentModule } from '../../../dynamic-content/dynamic-content.module';
import { VariantAnalysisFormModule } from "../../../shared/components/omics-analysis/variant-analysis-form/variant-analysis-form.module";
import { RnaSeqAnalysisFormModule } from "../../../shared/components/omics-analysis/rna-seq-analysis-form/rna-seq-analysis-form.module";
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";

@NgModule({
    imports: [
        CommonModule,
        IndivPatientAnalysisRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        ModalModule,
        ViewPatientInfoModule,
        StepWizardModule,
        OmicsAnalysisFormModule,
        DynamicContentModule,
        VariantAnalysisFormModule,
        RnaSeqAnalysisFormModule,
        TypeaheadModule,
        BreadcrumbModule
    ],
    declarations: [
        IndivPatientAnalysisComponent,
    ],
    exports: [
        IndivPatientAnalysisComponent,
    ]
})
export class IndivPatientAnalysisModule { }
