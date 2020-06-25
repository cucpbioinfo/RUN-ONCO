import { Component, OnInit } from "@angular/core";
import * as env from '../../../../../environments/environment.prod';

@Component({
    selector: "app-footer",
    templateUrl: "./footer.component.html",
    styleUrls: ["./footer.component.scss"]
})
export class FooterComponent implements OnInit {

    // public version = pack.version;
    public buildVersion = env.environment.version;

    constructor() {

    }

    ngOnInit(): void {

    }
}
