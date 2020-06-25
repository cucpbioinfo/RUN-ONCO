import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TranslateModule } from "@ngx-translate/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SharedServicesModule } from "../../../../services/shared-services.module";
import { SharedDirectivesModule } from "../../../../directives/shared-directives.module";
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { SurvivalFollowupFormModalComponent } from "./survival-followup-form-modal.component";
import { ModalModule } from 'ngx-bootstrap/modal';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';

@NgModule({
    imports: [
        CommonModule,
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
        SurvivalFollowupFormModalComponent
    ],
    entryComponents: [
        SurvivalFollowupFormModalComponent
    ]
})
export class SurvivalFollowupFormModalModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SurvivalFollowupFormModalModule,
            providers: [

            ]
        };
    }
}
