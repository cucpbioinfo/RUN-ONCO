import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TranslateModule } from "@ngx-translate/core";
import { BreadcrumbComponent } from "./breadcrumb.component";

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild()
    ],
    declarations: [
        BreadcrumbComponent
    ],
    exports: [
        BreadcrumbComponent
    ]
})
export class BreadcrumbModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: BreadcrumbModule,
            providers: [

            ]
        };
    }
}
