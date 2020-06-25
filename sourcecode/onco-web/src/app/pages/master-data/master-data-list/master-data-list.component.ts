import { Component, OnInit } from "@angular/core";

@Component({
    selector: "app-master-data-list",
    templateUrl: "./master-data-list.component.html",
    styleUrls: ["./master-data-list.component.scss"]
})
export class MasterDataListComponent implements OnInit {

    public breadcrumbs: any;

    constructor() {}

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.breadcrumbs = [
            {
                name: 'List',
                url: '/master-data-list',
                active: true
            }
        ];
    }
}
