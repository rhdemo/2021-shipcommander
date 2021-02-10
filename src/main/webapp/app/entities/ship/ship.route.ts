import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IShip, Ship } from 'app/shared/model/ship.model';
import { ShipService } from './ship.service';
import { ShipComponent } from './ship.component';
import { ShipDetailComponent } from './ship-detail.component';
import { ShipUpdateComponent } from './ship-update.component';

@Injectable({ providedIn: 'root' })
export class ShipResolve implements Resolve<IShip> {
  constructor(private service: ShipService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShip> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ship: HttpResponse<Ship>) => {
          if (ship.body) {
            return of(ship.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ship());
  }
}

export const shipRoute: Routes = [
  {
    path: '',
    component: ShipComponent,
    data: {
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'Ships',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipDetailComponent,
    resolve: {
      ship: ShipResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Ships',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipUpdateComponent,
    resolve: {
      ship: ShipResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Ships',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipUpdateComponent,
    resolve: {
      ship: ShipResolve,
    },
    data: {
      authorities: [],
      pageTitle: 'Ships',
    },
    canActivate: [UserRouteAccessService],
  },
];
