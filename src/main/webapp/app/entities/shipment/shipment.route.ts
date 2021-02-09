import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IShipment, Shipment } from 'app/shared/model/shipment.model';
import { ShipmentService } from './shipment.service';
import { ShipmentComponent } from './shipment.component';
import { ShipmentDetailComponent } from './shipment-detail.component';
import { ShipmentUpdateComponent } from './shipment-update.component';

@Injectable({ providedIn: 'root' })
export class ShipmentResolve implements Resolve<IShipment> {
  constructor(private service: ShipmentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShipment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((shipment: HttpResponse<Shipment>) => {
          if (shipment.body) {
            return of(shipment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Shipment());
  }
}

export const shipmentRoute: Routes = [
  {
    path: '',
    component: ShipmentComponent,
    data: {
      authorities: [],
      pageTitle: 'Shipments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipmentDetailComponent,
    resolve: {
      shipment: ShipmentResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Shipments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipmentUpdateComponent,
    resolve: {
      shipment: ShipmentResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Shipments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipmentUpdateComponent,
    resolve: {
      shipment: ShipmentResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Shipments',
    },
    canActivate: [UserRouteAccessService],
  },
];
