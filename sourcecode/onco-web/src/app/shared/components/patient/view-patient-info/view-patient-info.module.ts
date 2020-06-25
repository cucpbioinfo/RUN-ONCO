import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewPatientInfoComponent } from './view-patient-info.component';
import { TranslateModule } from '@ngx-translate/core';
import { SharedPipesModule } from "../../../pipes/shared-pipes.module";

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        SharedPipesModule
    ],
    declarations: [
        ViewPatientInfoComponent
    ],
    exports: [
        ViewPatientInfoComponent
    ]
})
export class ViewPatientInfoModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: ViewPatientInfoModule,
            providers: [

            ]
        };
    }
}
