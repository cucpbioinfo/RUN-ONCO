import { Component, OnInit, Input } from "@angular/core";
import { Router } from "@angular/router";

@Component({
    selector: "app-breadcrumb",
    templateUrl: "./breadcrumb.component.html",
    styleUrls: ["./breadcrumb.component.scss"]
})
export class BreadcrumbComponent implements OnInit {

    @Input() public items: any;

    constructor(private router: Router) {
    }

    ngOnInit(): void {

    }

    public trackByFn(index, item) {
        return index;
    }

    public onNavigate(item: any): void {
        if (!item.active) {
            this.router.navigate([item.url]);
        }
    }
}
