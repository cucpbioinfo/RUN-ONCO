import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { TranslateModule } from "@ngx-translate/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SharedServicesModule } from "../../../services/shared-services.module";
import { SharedDirectivesModule } from "../../../directives/shared-directives.module";
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { StepWizardModule } from "../../step-wizard/step-wizard.module";
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { ModalModule } from 'ngx-bootstrap/modal';
import { SharedPipesModule } from "../../../pipes/shared-pipes.module";
import { VariantCallListComponent } from "./variant-call-list.component";
import { VariantAnnotationListComponent } from "./variant-annotation-list.component";
import { DataTableModule } from "../../../../shared/components/data-table";
import { PathoViewModalModule } from "../../../../shared/components/app-modal/clinical-data/patho-view-modal/patho-view-modal.module";

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
        SharedPipesModule,
        DataTableModule,
        PathoViewModalModule
    ],
    declarations: [
        VariantCallListComponent,
        VariantAnnotationListComponent
    ],
    exports: [
        VariantCallListComponent,
        VariantAnnotationListComponent
    ]
})
export class VcStepProcessModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: VcStepProcessModule,
            providers: [

            ]
        };
    }
}
