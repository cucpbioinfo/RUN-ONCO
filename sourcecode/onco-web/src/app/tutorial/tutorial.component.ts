import { Component, OnInit } from "@angular/core";

@Component({
    selector: "app-tutorial",
    templateUrl: "./tutorial.component.html",
    styleUrls: ["./tutorial.component.scss"]
})
export class TutorialComponent implements OnInit {

    public tab: any;
    public prevTab: any;

    constructor() {

    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.tab = {
            data: [
                { value: 1, name: "Patient Management" },
                { value: 2, name: "Clinical Data Management" },
                { value: 3, name: "Biospecimen Management" },
                { value: 4, name: "Omics Data Management and Analysis" }
            ],
            step: 1,
            styleClass: "tablist"
        };
    }

    public goToNextTab(step: any) {
        this.prevTab = step.value;
        this.tab.step = step.value;
    }
}
