import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';

@Component({
    selector: 'app-step-wizard',
    templateUrl: './step-wizard.component.html',
    styleUrls: ['./step-wizard.component.scss']
})
export class StepWizardComponent {

    @Input() data: any = [];
    @Input() step: number;
    @Input() styleClass: string;
    @Input() isShow = true;
    @Output() selectChanged = new EventEmitter();

    constructor(private cdf: ChangeDetectorRef) {

    }

    onTabChanged(step: any): void {
        this.selectChanged.emit(step);
        this.cdf.detectChanges();
    }

    public trackByFn(index, item) {
        return index;
    }
}
