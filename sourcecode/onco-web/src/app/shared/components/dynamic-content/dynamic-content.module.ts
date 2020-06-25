import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { TranslateModule } from "@ngx-translate/core";
import { StepWizardModule } from "../step-wizard/step-wizard.module";
import { OmicsAnalysisFormModule } from "../omics-analysis/omics-analysis-form/omics-analysis-form.module";
import { VariantAnalysisFormModule } from "../omics-analysis/variant-analysis-form/variant-analysis-form.module";
import { RnaSeqAnalysisFormModule } from "../omics-analysis/rna-seq-analysis-form/rna-seq-analysis-form.module";
import { SharedServicesModule } from "../../services/shared-services.module";
import { SharedDirectivesModule } from "../../directives/shared-directives.module";
import { SharedPipesModule } from "../../pipes/shared-pipes.module";
import { VariantComparisonModule } from "../omics-analysis/omics-visualization/variant-comparison/variant-comparison.module";
import { ClustergrammerModule } from "../omics-analysis/omics-visualization/clustergrammer/clustergrammer.module";
import { CytoscapeModule } from "../omics-analysis/omics-visualization/cytoscape/cytoscape.module";
import { IdeogramModule } from "../omics-analysis/omics-visualization/ideogram/ideogram.module";

// Omics analysis components
import { PatientClustergrammerComponent } from "./patient-clustergrammer/patient-clustergrammer.component";
import { PatientVariantComparisonComponent } from "./patient-variant-comparison/patient-variant-comparison.component";
import { TmbScoreComponent } from "./tmb-score/tmb-score.component";

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        TranslateModule.forChild(),
        StepWizardModule,
        OmicsAnalysisFormModule,
        VariantAnalysisFormModule,
        RnaSeqAnalysisFormModule,
        SharedServicesModule,
        SharedDirectivesModule,
        SharedPipesModule,
        VariantComparisonModule,
        ClustergrammerModule,
        CytoscapeModule,
        IdeogramModule
    ],
    declarations: [
        PatientClustergrammerComponent,
        PatientVariantComparisonComponent,
        TmbScoreComponent
    ],
    entryComponents: [
        PatientClustergrammerComponent,
        PatientVariantComparisonComponent,
        TmbScoreComponent
    ]
})
export class DynamicContentModule {
    static mapping = {
        PatientClustergrammerComponent,
        PatientVariantComparisonComponent,
        TmbScoreComponent
    };
}
