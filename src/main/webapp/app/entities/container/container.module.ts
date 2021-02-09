import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShipcommanderSharedModule } from 'app/shared/shared.module';
import { ContainerComponent } from './container.component';
import { ContainerDetailComponent } from './container-detail.component';
import { ContainerUpdateComponent } from './container-update.component';
import { ContainerDeleteDialogComponent } from './container-delete-dialog.component';
import { containerRoute } from './container.route';

@NgModule({
  imports: [ShipcommanderSharedModule, RouterModule.forChild(containerRoute)],
  declarations: [ContainerComponent, ContainerDetailComponent, ContainerUpdateComponent, ContainerDeleteDialogComponent],
  entryComponents: [ContainerDeleteDialogComponent],
})
export class ShipcommanderContainerModule {}
