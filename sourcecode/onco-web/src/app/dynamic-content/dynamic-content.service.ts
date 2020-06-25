import { ComponentFactoryResolver, ComponentRef, Injectable, Injector, NgModuleFactoryLoader, Type } from "@angular/core";
import { DynamicContentErrorComponent } from "./dynamic-content-error.component";
import { AppStateService } from "../shared/services/app-state.service";

type ModuleWithDynamicComponents = Type<any> & {
    mapping: {};
};

@Injectable()
export class DynamicContentService {

    constructor(
        private componentFactoryResolver: ComponentFactoryResolver,
        private moduleLoader: NgModuleFactoryLoader,
        private injector: Injector,
        private appState: AppStateService
    ) {
    }

    async GetComponent(componentName: string, params: any): Promise<ComponentRef<any>> {
        const modulePath = this.getModulePathForComponent(componentName);

        if (!modulePath) {
            return this.getDynamicContentErrorComponent(`Unable to find component ${componentName} in module path`);
        }

        try {
            const moduleFactory = await this.moduleLoader.load(modulePath);
            const moduleReference = moduleFactory.create(this.injector);
            const componentResolver = moduleReference.componentFactoryResolver;
            const componentType = (moduleFactory.moduleType as ModuleWithDynamicComponents).mapping[componentName];
            const componentFactory = componentResolver.resolveComponentFactory(componentType);
            // return componentFactory.create(this.injector);
            const componentRef = componentFactory.create(this.injector);
            const instance: any = <any>componentRef.instance;
            instance.params = params;
            return componentRef;

        } catch (error) {
            console.error(error.message);
            return this.getDynamicContentErrorComponent(`Unable to load component: ${componentName}. Error Details: ${error.message}`);
        }
    }

    private getModulePathForComponent(componentName: string) {
        const registryItem = this.appState.dynamicComponents.find((x: any) => x.componentName === componentName);

        if (registryItem && registryItem.modulePath) {
            return `${registryItem.modulePath}#${registryItem.moduleName}`;
        }

        return null;
    }

    private getDynamicContentErrorComponent(errorMessage: string) {
        const factory = this.componentFactoryResolver.resolveComponentFactory(DynamicContentErrorComponent);
        const componentRef = factory.create(this.injector);
        const instance = <any>componentRef.instance;
        instance.errorMessage = errorMessage;
        return componentRef;
    }
}
