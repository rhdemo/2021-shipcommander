import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContainer } from 'app/shared/model/container.model';

type EntityResponseType = HttpResponse<IContainer>;
type EntityArrayResponseType = HttpResponse<IContainer[]>;

@Injectable({ providedIn: 'root' })
export class ContainerService {
  public resourceUrl = SERVER_API_URL + 'api/containers';

  constructor(protected http: HttpClient) {}

  create(container: IContainer): Observable<EntityResponseType> {
    return this.http.post<IContainer>(this.resourceUrl, container, { observe: 'response' });
  }

  update(container: IContainer): Observable<EntityResponseType> {
    return this.http.put<IContainer>(this.resourceUrl, container, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContainer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContainer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
