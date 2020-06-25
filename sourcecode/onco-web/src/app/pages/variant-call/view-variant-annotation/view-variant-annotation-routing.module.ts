import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ViewVariantAnnotationComponent } from './view-variant-annotation.component';

const routes: Routes = [
    {
        path: '',
        component: ViewVariantAnnotationComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ViewVariantAnnotationRoutingModule {}
