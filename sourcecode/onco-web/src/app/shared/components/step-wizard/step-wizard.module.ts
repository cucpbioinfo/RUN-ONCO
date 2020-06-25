import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StepWizardComponent } from './step-wizard.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule
    ],
    declarations: [
        StepWizardComponent
    ],
    exports: [
        StepWizardComponent
    ]
})
export class StepWizardModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: StepWizardModule,
            providers: [

            ]
        };
    }
}
