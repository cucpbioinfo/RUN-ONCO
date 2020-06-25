import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TranslateModule } from "@ngx-translate/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SharedServicesModule } from "../../../../services/shared-services.module";
import { SharedDirectivesModule } from "../../../../directives/shared-directives.module";
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { UserFormModalComponent } from "./user-form-modal.component";
import { ModalModule } from "ngx-bootstrap/modal";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        SharedServicesModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        BsDatepickerModule,
        ModalModule
    ],
    declarations: [
        UserFormModalComponent
    ],
    entryComponents: [
        UserFormModalComponent
    ]
})
export class UserFormModalModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: UserFormModalComponent,
            providers: [

            ]
        };
    }
}
