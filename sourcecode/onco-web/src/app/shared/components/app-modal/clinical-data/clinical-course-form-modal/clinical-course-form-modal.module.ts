import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { TranslateModule } from "@ngx-translate/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SharedServicesModule } from "../../../../services/shared-services.module";
import { SharedDirectivesModule } from "../../../../directives/shared-directives.module";
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { ClinicalCourseFormModalComponent } from "./clinical-course-form-modal.component";
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';

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
        ModalModule,
        TypeaheadModule
    ],
    declarations: [
        ClinicalCourseFormModalComponent
    ],
    entryComponents: [
        ClinicalCourseFormModalComponent
    ]
})
export class ClinicalCourseFormModalModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: ClinicalCourseFormModalModule,
            providers: [

            ]
        };
    }
}
