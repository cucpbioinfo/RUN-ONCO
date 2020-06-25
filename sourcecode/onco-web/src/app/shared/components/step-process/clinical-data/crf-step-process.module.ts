import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { TranslateModule } from "@ngx-translate/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { DiagnosisListComponent } from "./diagnosis-list.component";
import { SharedServicesModule } from "../../../services/shared-services.module";
import { SharedDirectivesModule } from "../../../directives/shared-directives.module";
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { InputDiagnosisComponent } from "./input-diagnosis.component";
import { StepWizardModule } from "../../step-wizard/step-wizard.module";
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PathoListComponent } from "./patho-list.component";
import { InputCancerStageComponent } from "./input-cancer-stage.component";
import { InputSurvivalFollowupComponent } from "./input-survival-followup.component";
import { ClinicalCourseListComponent } from "./clinical-course-list.component";
import { SharedPipesModule } from "../../../pipes/shared-pipes.module";
import { SurvivalFollowupListComponent } from "../../step-process/clinical-data/survival-followup-list.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        ReactiveFormsModule,
        SharedServicesModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        BsDatepickerModule,
        StepWizardModule,
        TypeaheadModule,
        ModalModule,
        SharedPipesModule
    ],
    declarations: [
        DiagnosisListComponent,
        InputDiagnosisComponent,
        PathoListComponent,
        InputCancerStageComponent,
        InputSurvivalFollowupComponent,
        ClinicalCourseListComponent,
        SurvivalFollowupListComponent
    ],
    exports: [
        DiagnosisListComponent,
        InputDiagnosisComponent,
        PathoListComponent,
        InputCancerStageComponent,
        InputSurvivalFollowupComponent,
        ClinicalCourseListComponent,
        SurvivalFollowupListComponent
    ]
})
export class CfrStepProcessModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: CfrStepProcessModule,
            providers: [

            ]
        };
    }
}
