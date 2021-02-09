import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShipcommanderSharedModule } from 'app/shared/shared.module';
import { SensorReadingsComponent } from './sensor-readings.component';
import { SensorReadingsDetailComponent } from './sensor-readings-detail.component';
import { SensorReadingsUpdateComponent } from './sensor-readings-update.component';
import { SensorReadingsDeleteDialogComponent } from './sensor-readings-delete-dialog.component';
import { sensorReadingsRoute } from './sensor-readings.route';

@NgModule({
  imports: [ShipcommanderSharedModule, RouterModule.forChild(sensorReadingsRoute)],
  declarations: [
    SensorReadingsComponent,
    SensorReadingsDetailComponent,
    SensorReadingsUpdateComponent,
    SensorReadingsDeleteDialogComponent,
  ],
  entryComponents: [SensorReadingsDeleteDialogComponent],
})
export class ShipcommanderSensorReadingsModule {}
