import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { ConfirmDialogComponent } from "./confirm-dialog.component";
import { ModalModule } from 'ngx-bootstrap/modal';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        ModalModule
    ],
    declarations: [
        ConfirmDialogComponent
    ],
    entryComponents: [
        ConfirmDialogComponent
    ]
})
export class ConfirmDialogModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: ConfirmDialogModule,
            providers: [

            ]
        };
    }
}
