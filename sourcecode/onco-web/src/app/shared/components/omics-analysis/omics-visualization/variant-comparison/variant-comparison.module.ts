import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { TranslateModule } from "@ngx-translate/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SharedServicesModule } from "../../../../services/shared-services.module";
import { SharedDirectivesModule } from "../../../../directives/shared-directives.module";
import { ModalModule } from 'ngx-bootstrap/modal';
import { SharedPipesModule } from "../../../../pipes/shared-pipes.module";
import { VariantComparisonComponent } from "./variant-comparison.component";
import { DataTableModule } from "../../../data-table";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        ReactiveFormsModule,
        SharedServicesModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        ModalModule,
        SharedPipesModule,
        DataTableModule
    ],
    declarations: [
        VariantComparisonComponent
    ],
    exports: [
        VariantComparisonComponent
    ]
})
export class VariantComparisonModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: VariantComparisonModule,
            providers: [

            ]
        };
    }
}
