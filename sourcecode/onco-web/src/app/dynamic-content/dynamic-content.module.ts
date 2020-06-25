import { CommonModule } from "@angular/common";
import { NgModule, NgModuleFactoryLoader, SystemJsNgModuleLoader } from "@angular/core";
import { DynamicContentErrorComponent } from "./dynamic-content-error.component";
import { DynamicContentComponent } from "./dynamic-content.component";
import { DynamicContentService } from "./dynamic-content.service";

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        DynamicContentComponent,
        DynamicContentErrorComponent
    ],
    exports: [
        DynamicContentComponent
    ],
    providers: [
        {
            provide: NgModuleFactoryLoader,
            useClass: SystemJsNgModuleLoader
        },
        DynamicContentService
    ],
    entryComponents: [
        DynamicContentErrorComponent
    ]
})
export class DynamicContentModule {}
