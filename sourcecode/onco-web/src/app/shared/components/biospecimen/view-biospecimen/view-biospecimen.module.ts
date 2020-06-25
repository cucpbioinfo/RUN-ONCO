import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewBiospecimenComponent } from './view-biospecimen.component';
import { TranslateModule } from '@ngx-translate/core';
import { SharedPipesModule } from "../../../pipes/shared-pipes.module";
import { PathoViewModalModule } from "../../../components/app-modal/clinical-data/patho-view-modal/patho-view-modal.module";

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        SharedPipesModule,
        PathoViewModalModule
    ],
    declarations: [
        ViewBiospecimenComponent
    ],
    exports: [
        ViewBiospecimenComponent
    ]
})
export class ViewBiospecimenModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: ViewBiospecimenModule,
            providers: [

            ]
        };
    }
}
