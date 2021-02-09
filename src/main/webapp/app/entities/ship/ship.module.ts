import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShipcommanderSharedModule } from 'app/shared/shared.module';
import { ShipComponent } from './ship.component';
import { ShipDetailComponent } from './ship-detail.component';
import { ShipUpdateComponent } from './ship-update.component';
import { ShipDeleteDialogComponent } from './ship-delete-dialog.component';
import { shipRoute } from './ship.route';

@NgModule({
  imports: [ShipcommanderSharedModule, RouterModule.forChild(shipRoute)],
  declarations: [ShipComponent, ShipDetailComponent, ShipUpdateComponent, ShipDeleteDialogComponent],
  entryComponents: [ShipDeleteDialogComponent],
})
export class ShipcommanderShipModule {}
