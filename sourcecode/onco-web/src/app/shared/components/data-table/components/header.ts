import { Component, Inject, forwardRef } from '@angular/core';
import { DataTable } from './table';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'data-table-header',
  templateUrl: './header.html',
  // styleUrls: ['./header.css'],
  // tslint:disable-next-line:use-host-property-decorator
  host: {
    '(document:click)': '_closeSelector()'
  }
})
// tslint:disable-next-line:component-class-suffix
export class DataTableHeader {

    columnSelectorOpen = false;

    _closeSelector() {
        this.columnSelectorOpen = false;
    }

    constructor(@Inject(forwardRef(() => DataTable)) public dataTable: DataTable) {}
}
