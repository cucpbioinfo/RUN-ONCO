import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { AlertDialogComponent } from "./alert-dialog.component";
import { ModalModule } from 'ngx-bootstrap/modal';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        ModalModule
    ],
    declarations: [
        AlertDialogComponent
    ],
    entryComponents: [
        AlertDialogComponent
    ]
})
export class AlertDialogModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: AlertDialogModule,
            providers: [

            ]
        };
    }
}
