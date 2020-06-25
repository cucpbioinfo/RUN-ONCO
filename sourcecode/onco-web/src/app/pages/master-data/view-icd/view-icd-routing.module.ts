import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ViewIcdComponent } from './view-icd.component';

const routes: Routes = [
    {
        path: '',
        component: ViewIcdComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ViewIcdRoutingModule {}
