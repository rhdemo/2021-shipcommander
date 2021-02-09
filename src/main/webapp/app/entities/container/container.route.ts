import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContainer, Container } from 'app/shared/model/container.model';
import { ContainerService } from './container.service';
import { ContainerComponent } from './container.component';
import { ContainerDetailComponent } from './container-detail.component';
import { ContainerUpdateComponent } from './container-update.component';

@Injectable({ providedIn: 'root' })
export class ContainerResolve implements Resolve<IContainer> {
  constructor(private service: ContainerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContainer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((container: HttpResponse<Container>) => {
          if (container.body) {
            return of(container.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Container());
  }
}

export const containerRoute: Routes = [
  {
    path: '',
    component: ContainerComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Containers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContainerDetailComponent,
    resolve: {
      container: ContainerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Containers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContainerUpdateComponent,
    resolve: {
      container: ContainerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Containers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContainerUpdateComponent,
    resolve: {
      container: ContainerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Containers',
    },
    canActivate: [UserRouteAccessService],
  },
];
