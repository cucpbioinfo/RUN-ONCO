import { NgModule, ModuleWithProviders } from '@angular/core';
import { CurrencyFormatterDirective } from './currency-formatter.directive';
import { NumberFormatterDirective } from './number-formatter.directive';
import { SharedPipesModule } from '../pipes/shared-pipes.module';
import { ClickOutsideDirective } from './click-outside.directive';
import { FormValidatorDirective } from './form-validator.directive';
import { FileDropDirective } from './file-drop.directive';

@NgModule({
    imports: [
        SharedPipesModule
    ],
    declarations: [
        CurrencyFormatterDirective,
        NumberFormatterDirective,
        ClickOutsideDirective,
        FormValidatorDirective,
        FileDropDirective
    ],
    providers: [
        CurrencyFormatterDirective,
        NumberFormatterDirective,
        ClickOutsideDirective,
        FormValidatorDirective,
        FileDropDirective
    ],
    exports: [
        CurrencyFormatterDirective,
        NumberFormatterDirective,
        ClickOutsideDirective,
        FormValidatorDirective,
        FileDropDirective
    ]
})
export class SharedDirectivesModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SharedDirectivesModule,
            providers: [
            ]
        };
    }
}
