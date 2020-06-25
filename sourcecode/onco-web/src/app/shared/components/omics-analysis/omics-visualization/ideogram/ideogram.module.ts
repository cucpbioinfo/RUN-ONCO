import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IdeogramComponent } from './ideogram.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild()
    ],
    declarations: [
        IdeogramComponent
    ],
    exports: [
        IdeogramComponent
    ]
})
export class IdeogramModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: IdeogramModule,
            providers: [

            ]
        };
    }
}

