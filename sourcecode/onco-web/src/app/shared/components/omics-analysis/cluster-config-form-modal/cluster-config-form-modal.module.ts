import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { SharedServicesModule } from '../../../services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../directives/shared-directives.module';
import { TranslateModule } from "@ngx-translate/core";
import { ModalModule } from 'ngx-bootstrap/modal';
import { ClusterConfigFormModalComponent } from "./cluster-config-form-modal.component";

@NgModule({
    imports: [
        CommonModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        ModalModule,
    ],
    declarations: [
        ClusterConfigFormModalComponent
    ],
    entryComponents: [
        ClusterConfigFormModalComponent
    ]
})
export class ClusterConfigFormModalModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: ClusterConfigFormModalModule,
            providers: [

            ]
        };
    }
}
