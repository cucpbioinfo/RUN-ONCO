import { Directive, ElementRef, OnInit, DoCheck, Input, KeyValueDiffers, OnChanges, OnDestroy } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ValidationService } from '../../shared/services/validation.service';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import { Subscription } from 'rxjs';

@Directive({
    // tslint:disable-next-line:directive-selector
    selector: '[formControlValidator]',
    providers: [ValidationService]
})
export class FormValidatorDirective implements OnInit, OnDestroy, DoCheck, OnChanges {

    constructor(private _elementRef: ElementRef,
        private differs: KeyValueDiffers,
        private translate: TranslateService) {
    }

    @Input() control: FormControl;
    // tslint:disable-next-line:no-input-rename
    @Input('submitted') isSubmitted: boolean;
    // tslint:disable-next-line:no-input-rename
    @Input('option') messageOption: any;

    differ: any;
    nativeElement: any;
    private langSubscription: Subscription;

    ngOnChanges(changed: any) {
        if (changed && changed.isSubmitted && changed.isSubmitted.currentValue) {
            this.validation();
        }
    }

    ngDoCheck() {
        if (this.differ) {
            const changes = this.differ.diff(this.control);
            if (changes) {
                this.validation();
            }
        }
    }

    ngOnInit() {
        this.nativeElement = this._elementRef.nativeElement;
        this.differ = this.differs.find(this.control).create();

        this.langSubscription = this.translate.onLangChange
            .subscribe((event: LangChangeEvent) => {
                this.validation();
            });
    }

    ngOnDestroy() {
        this.langSubscription.unsubscribe();
    }

    validation() {
        // console.log('validation -> ', this.control.errors, this.control.dirty, this.control.touched);
        if (this.control.errors && (this.control.dirty || this.control.touched || this.isSubmitted)) {
            this.addClass('has-error');
            this.addErrorMessage();
        } else {
            this.removeClass('has-error');
            this.removeErrorMessage();
        }
    }

    addClass(className) {
        if (this.nativeElement) {
            this.nativeElement.classList ? this.nativeElement.classList.add(className) : this.nativeElement.className += ' ' + className;
        }
    }

    removeClass(className) {
        if (this.nativeElement) {
            // tslint:disable-next-line:max-line-length
            this.nativeElement.classList ? this.nativeElement.classList.remove(className) : this.nativeElement.className += this.nativeElement.className.replace(new RegExp('(?:^|\\s)' + className + '(?:\\s|$)'), ' ');
        }
    }

    addErrorMessage() {
        if (this.nativeElement) {
            const divErrorBlock = this.nativeElement.querySelector('.help-block.with-errors');
            if (divErrorBlock) {
                divErrorBlock.innerText = this.errorMessage;
                divErrorBlock.style.display = 'block';
            }
        }
    }

    removeErrorMessage() {
        if (this.nativeElement) {
            const divErrorBlock = this.nativeElement.querySelector('.help-block.with-errors');
            if (divErrorBlock) {
                divErrorBlock.innerText = '';
                divErrorBlock.style.display = 'none';
            }
        }
    }

    resetValidator() {
        this.removeClass('has-error');
        this.removeErrorMessage();
    }

    get errorMessage() {
        for (const propertyName in this.control.errors) {
            if (this.control.errors.hasOwnProperty(propertyName)) {
                // tslint:disable-next-line:max-line-length
                return this.translate.instant(ValidationService.getValidatorErrorMessage(propertyName, this.control.errors[propertyName]), this.messageOption || {});
            }
        }

        return null;
    }
}
