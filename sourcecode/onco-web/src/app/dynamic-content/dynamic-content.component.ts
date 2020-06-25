import { Component, ComponentRef, Input, OnChanges, OnDestroy, ViewChild, ViewContainerRef } from "@angular/core";
import { DynamicContentService } from "./dynamic-content.service";

@Component({
    selector: "app-dynamic-content",
    template: `<ng-container #container></ng-container>`
})
export class DynamicContentComponent implements OnDestroy, OnChanges {

    @ViewChild("container", { read: ViewContainerRef })
    container: ViewContainerRef;

    @Input() componentName: string;
    @Input() params: any;

    private component: ComponentRef<{}>;

    constructor(private dynamicContentService: DynamicContentService) {}

    async ngOnChanges() {
        await this.renderComponent();
    }

    ngOnDestroy() {
        this.destroyComponent();
    }

    private async renderComponent() {
        this.destroyComponent();

        this.component = await this.dynamicContentService.GetComponent(this.componentName, this.params);
        // const instance: any = this.component.instance;
        // instance.params = this.params;
        this.container.insert(this.component.hostView);
    }

    private destroyComponent() {
        if (this.component) {
            this.component.destroy();
            this.component = null;
        }
    }
}
