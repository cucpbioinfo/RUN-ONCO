import { Component, OnInit } from "@angular/core";

@Component({
    selector: "app-master-data",
    template: `<router-outlet></router-outlet>`,
    styleUrls: ["./master-data.component.scss"]
})
export class MasterDataComponent implements OnInit {

    constructor() { }

    ngOnInit(): void {}
}
