import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISensorReadings, SensorReadings } from 'app/shared/model/sensor-readings.model';
import { SensorReadingsService } from './sensor-readings.service';
import { SensorReadingsComponent } from './sensor-readings.component';
import { SensorReadingsDetailComponent } from './sensor-readings-detail.component';
import { SensorReadingsUpdateComponent } from './sensor-readings-update.component';

@Injectable({ providedIn: 'root' })
export class SensorReadingsResolve implements Resolve<ISensorReadings> {
  constructor(private service: SensorReadingsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISensorReadings> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sensorReadings: HttpResponse<SensorReadings>) => {
          if (sensorReadings.body) {
            return of(sensorReadings.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SensorReadings());
  }
}

export const sensorReadingsRoute: Routes = [
  {
    path: '',
    component: SensorReadingsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SensorReadings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SensorReadingsDetailComponent,
    resolve: {
      sensorReadings: SensorReadingsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SensorReadings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SensorReadingsUpdateComponent,
    resolve: {
      sensorReadings: SensorReadingsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SensorReadings',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SensorReadingsUpdateComponent,
    resolve: {
      sensorReadings: SensorReadingsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SensorReadings',
    },
    canActivate: [UserRouteAccessService],
  },
];
