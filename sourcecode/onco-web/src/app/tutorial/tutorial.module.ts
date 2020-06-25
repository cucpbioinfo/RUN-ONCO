import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TutorialComponent } from "./tutorial.component";
import { TutorialRoutingModule } from "./tutorial-routing.module";
import { LoginFormModule } from "../shared/components/login-form/login-form.module";
import { LayoutModule } from "../shared/components/layout/layout.module";
import { StepWizardModule } from "../shared/components/step-wizard/step-wizard.module";
import { ViewPatientManagementComponent } from './view-patient-management/view-patient-management.component';
import { ViewImageDialogModule } from '../shared/components/modal-dialog/view-image-dialog/view-image-dialog.module';
import { ViewClinicalDataManagementComponent } from './view-clinical-data-management/view-clinical-data-management.component';
import { ViewBiospecimenManagementComponent } from './view-biospecimen-management/view-biospecimen-management.component';
import { ViewOmicsDataManagementComponent } from './view-omics-data-management/view-omics-data-management.component';

@NgModule({
    imports: [
        CommonModule,
        TutorialRoutingModule,
        LoginFormModule,
        LayoutModule,
        StepWizardModule,
        ViewImageDialogModule
    ],
    declarations: [
        TutorialComponent,
        ViewPatientManagementComponent,
        ViewClinicalDataManagementComponent,
        ViewBiospecimenManagementComponent,
        ViewOmicsDataManagementComponent
    ]
})
export class TutorialModule {}
