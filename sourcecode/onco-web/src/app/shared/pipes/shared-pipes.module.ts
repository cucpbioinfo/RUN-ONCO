import { CommonModule } from '@angular/common';
import { NgModule, ModuleWithProviders } from '@angular/core';
import { CurrencyFormatterPipe } from '../pipes/currency-formatter.pipe';
import { EscapeHtmlPipe } from '../pipes/keep-html.pipe';
import { SafePipe } from '../pipes/safe.pipe';
import { DateFormatterPipe } from '../pipes/date-formatter.pipe';

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        CurrencyFormatterPipe,
        EscapeHtmlPipe,
        SafePipe,
        DateFormatterPipe
    ],
    providers: [
        CurrencyFormatterPipe,
        EscapeHtmlPipe,
        SafePipe,
        DateFormatterPipe
    ],
    exports: [
        CurrencyFormatterPipe,
        EscapeHtmlPipe,
        SafePipe,
        DateFormatterPipe
    ]
})
export class SharedPipesModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: SharedPipesModule,
            providers: [
            ]
        };
    }
}
