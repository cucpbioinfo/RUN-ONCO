import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CytoscapeComponent } from './cytoscape.component';
import { TranslateModule } from '@ngx-translate/core';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SharedServicesModule } from "../../../../services/shared-services.module";
import { SharedDirectivesModule } from "../../../../directives/shared-directives.module";
import { DataTableModule } from '../../../../components/data-table';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        FormsModule,
        ReactiveFormsModule,
        SharedServicesModule,
        SharedDirectivesModule,
        DataTableModule
    ],
    declarations: [
        CytoscapeComponent
    ],
    exports: [
        CytoscapeComponent
    ]
})
export class CytoscapeModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: CytoscapeModule,
            providers: [

            ]
        };
    }
}
