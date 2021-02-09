import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISensorReadings } from 'app/shared/model/sensor-readings.model';

type EntityResponseType = HttpResponse<ISensorReadings>;
type EntityArrayResponseType = HttpResponse<ISensorReadings[]>;

@Injectable({ providedIn: 'root' })
export class SensorReadingsService {
  public resourceUrl = SERVER_API_URL + 'api/sensor-readings';

  constructor(protected http: HttpClient) {}

  create(sensorReadings: ISensorReadings): Observable<EntityResponseType> {
    return this.http.post<ISensorReadings>(this.resourceUrl, sensorReadings, { observe: 'response' });
  }

  update(sensorReadings: ISensorReadings): Observable<EntityResponseType> {
    return this.http.put<ISensorReadings>(this.resourceUrl, sensorReadings, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISensorReadings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISensorReadings[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
