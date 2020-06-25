import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientAnalysisRoutingModule } from './patient-analysis-routing.module';
import { PatientAnalysisComponent } from './patient-analysis.component';
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from "@ngx-translate/core";
import { ModalModule } from 'ngx-bootstrap/modal';
import { ViewPatientInfoModule } from "../../../shared/components/patient/view-patient-info/view-patient-info.module";
import { StepWizardModule } from "../../../shared/components/step-wizard/step-wizard.module";
import { OmicsAnalysisFormModule } from "../../../shared/components/omics-analysis/omics-analysis-form/omics-analysis-form.module";
import { VariantAnalysisFormModule } from "../../../shared/components/omics-analysis/variant-analysis-form/variant-analysis-form.module";
import { RnaSeqAnalysisFormModule } from "../../../shared/components/omics-analysis/rna-seq-analysis-form/rna-seq-analysis-form.module";
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";
import { VariantComparisonModule } from "../../../shared/components/omics-analysis/omics-visualization/variant-comparison/variant-comparison.module";
import { ClustergrammerModule } from "../../../shared/components/omics-analysis/omics-visualization/clustergrammer/clustergrammer.module";
import { CytoscapeModule } from "../../../shared/components/omics-analysis/omics-visualization/cytoscape/cytoscape.module";
import { IdeogramModule } from "../../../shared/components/omics-analysis/omics-visualization/ideogram/ideogram.module";

@NgModule({
    imports: [
        CommonModule,
        PatientAnalysisRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        ModalModule,
        ViewPatientInfoModule,
        StepWizardModule,
        OmicsAnalysisFormModule,
        VariantAnalysisFormModule,
        RnaSeqAnalysisFormModule,
        BreadcrumbModule,
        VariantComparisonModule,
        ClustergrammerModule,
        CytoscapeModule,
        IdeogramModule
    ],
    declarations: [
        PatientAnalysisComponent,
    ],
    exports: [
        PatientAnalysisComponent,
    ]
})
export class PatientAnalysisModule { }
