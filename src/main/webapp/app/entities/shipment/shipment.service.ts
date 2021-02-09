import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShipment } from 'app/shared/model/shipment.model';

type EntityResponseType = HttpResponse<IShipment>;
type EntityArrayResponseType = HttpResponse<IShipment[]>;

@Injectable({ providedIn: 'root' })
export class ShipmentService {
  public resourceUrl = SERVER_API_URL + 'api/shipments';

  constructor(protected http: HttpClient) {}

  create(shipment: IShipment): Observable<EntityResponseType> {
    return this.http.post<IShipment>(this.resourceUrl, shipment, { observe: 'response' });
  }

  update(shipment: IShipment): Observable<EntityResponseType> {
    return this.http.put<IShipment>(this.resourceUrl, shipment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShipment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
