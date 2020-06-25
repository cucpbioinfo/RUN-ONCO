import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MasterDataListComponent } from './master-data-list.component';

const routes: Routes = [
    {
        path: '',
        component: MasterDataListComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MasterDataListRoutingModule {}
