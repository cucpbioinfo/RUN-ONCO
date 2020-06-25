import { ModuleWithProviders, NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TranslateModule } from '@ngx-translate/core';
import { ViewImageDialogComponent } from "./view-image-dialog.component";
import { ModalModule } from 'ngx-bootstrap/modal';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        ModalModule
    ],
    declarations: [
        ViewImageDialogComponent
    ],
    entryComponents: [
        ViewImageDialogComponent
    ]
})
export class ViewImageDialogModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: ViewImageDialogComponent,
            providers: [

            ]
        };
    }
}
