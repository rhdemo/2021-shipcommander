import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.ShipcommanderCompanyModule),
      },
      {
        path: 'container',
        loadChildren: () => import('./container/container.module').then(m => m.ShipcommanderContainerModule),
      },
      {
        path: 'port',
        loadChildren: () => import('./port/port.module').then(m => m.ShipcommanderPortModule),
      },
      {
        path: 'sensor-readings',
        loadChildren: () => import('./sensor-readings/sensor-readings.module').then(m => m.ShipcommanderSensorReadingsModule),
      },
      {
        path: 'ship',
        loadChildren: () => import('./ship/ship.module').then(m => m.ShipcommanderShipModule),
      },
      {
        path: 'shipment',
        loadChildren: () => import('./shipment/shipment.module').then(m => m.ShipcommanderShipmentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class ShipcommanderEntityModule {}
