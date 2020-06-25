import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ViewIcdOComponent } from './view-icd-o.component';

const routes: Routes = [
    {
        path: '',
        component: ViewIcdOComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ViewIcdORoutingModule {}
