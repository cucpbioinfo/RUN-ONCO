import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DynamicContentModalComponent } from "./dynamic-content-modal.component";
import { TranslateModule } from "@ngx-translate/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SharedServicesModule } from "../../../../services/shared-services.module";
import { SharedDirectivesModule } from "../../../../directives/shared-directives.module";
import { ModalModule } from 'ngx-bootstrap/modal';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        ReactiveFormsModule,
        FormsModule,
        SharedServicesModule,
        SharedDirectivesModule,
        ModalModule
    ],
    declarations: [
        DynamicContentModalComponent
    ],
    entryComponents: [
        DynamicContentModalComponent
    ]
})
export class DynamicContentModalModule {}
