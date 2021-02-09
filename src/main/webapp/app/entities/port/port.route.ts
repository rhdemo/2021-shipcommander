import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPort, Port } from 'app/shared/model/port.model';
import { PortService } from './port.service';
import { PortComponent } from './port.component';
import { PortDetailComponent } from './port-detail.component';
import { PortUpdateComponent } from './port-update.component';

@Injectable({ providedIn: 'root' })
export class PortResolve implements Resolve<IPort> {
  constructor(private service: PortService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPort> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((port: HttpResponse<Port>) => {
          if (port.body) {
            return of(port.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Port());
  }
}

export const portRoute: Routes = [
  {
    path: '',
    component: PortComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Ports',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PortDetailComponent,
    resolve: {
      port: PortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ports',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PortUpdateComponent,
    resolve: {
      port: PortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ports',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PortUpdateComponent,
    resolve: {
      port: PortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ports',
    },
    canActivate: [UserRouteAccessService],
  },
];
