import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShip } from 'app/shared/model/ship.model';

type EntityResponseType = HttpResponse<IShip>;
type EntityArrayResponseType = HttpResponse<IShip[]>;

@Injectable({ providedIn: 'root' })
export class ShipService {
  public resourceUrl = SERVER_API_URL + 'api/ships';

  constructor(protected http: HttpClient) {}

  create(ship: IShip): Observable<EntityResponseType> {
    return this.http.post<IShip>(this.resourceUrl, ship, { observe: 'response' });
  }

  update(ship: IShip): Observable<EntityResponseType> {
    return this.http.put<IShip>(this.resourceUrl, ship, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShip>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShip[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
